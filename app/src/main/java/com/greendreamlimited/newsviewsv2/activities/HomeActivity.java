package com.greendreamlimited.newsviewsv2.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.greendreamlimited.newsviewsv2.R;
import com.greendreamlimited.newsviewsv2.adapters.MenuAdapter;
import com.greendreamlimited.newsviewsv2.fragments.AboutFragment;
import com.greendreamlimited.newsviewsv2.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.Arrays;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;


public class HomeActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener {
    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;
    public ArrayList<String> mTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));

        mViewHolder = new ViewHolder();
        handleToolbar();
        handleMenu();
        handleDrawer();

        goToFragment(new HomeFragment(), false);
        mMenuAdapter.setViewSelected(0, true);
        setTitle(mTitles.get(0));
    }

    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(R.id.main_frame, fragment).commit();
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(
                this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();
    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(mTitles);

        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
    }

    @Override
    public void onFooterClicked() {
        Toast.makeText(this, "onFooterClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHeaderClicked() {
        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        setTitle(mTitles.get(position));
        mMenuAdapter.setViewSelected(position, true);
        switch (position) {
            case 0:
                goToFragment(new HomeFragment(), false);
                break;
            case 1:
                goToFragment(new AboutFragment(), false);
                break;
            case 2:
                exitFromApp();
                break;
                default:
                    Toast.makeText(this, String.valueOf(position) , Toast.LENGTH_SHORT).show();
                    break;
        }
        mViewHolder.mDuoDrawerLayout.closeDrawer();
    }

    private void exitFromApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = findViewById(R.id.toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        android.view.MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Latest News.....");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2) {
                    //onLoadingSwipeRefresh(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // onLoadingSwipeRefresh(newText);
                return false;
            }
        });
        searchMenuItem.getIcon().setVisible(false, false);
        return true;
    }
}
