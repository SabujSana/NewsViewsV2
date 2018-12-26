package com.greendreamlimited.newsviewsv2.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
import android.view.Menu;

import com.greendreamlimited.newsviewsv2.R;
import com.greendreamlimited.newsviewsv2.fragments.AboutFragment;
import com.greendreamlimited.newsviewsv2.fragments.HomeFragment;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    SNavigationDrawer sNavigationDrawer;
    int color1 = 0;
    Class fragmentClass;
    public static Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        sNavigationDrawer = findViewById(R.id.navigationDrawer);
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(new MenuItem("Home", R.drawable.ic_home));
        menuItemList.add(new MenuItem("About", R.drawable.ic_about));
        menuItemList.add(new MenuItem("Exit", R.drawable.ic_exit));
        sNavigationDrawer.setMenuItemList(menuItemList);

        fragmentClass = HomeFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in,
                    android.R.animator.fade_out)
                    .replace(R.id.frameLayout, fragment)
                    .commit();
        }

        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                switch (position) {
                    case 0:
                        color1 = R.color.home;
                        fragmentClass = HomeFragment.class;
                        break;
                    case 1:
                        color1 = R.color.about;
                        fragmentClass = AboutFragment.class;
                        break;
                    case 2:
                        exitFromApp();
                        break;
                }
            }
        });

        sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {
            @Override
            public void onDrawerOpening() {

            }

            @Override
            public void onDrawerClosing() {
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().setCustomAnimations(
                            android.R.animator.fade_in, android.R.animator.fade_out
                    ).replace(R.id.frameLayout, fragment).commit();
                }
            }

            @Override
            public void onDrawerOpened() {

            }

            @Override
            public void onDrawerClosed() {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });


    }

    private void exitFromApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        System.exit(0);
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
