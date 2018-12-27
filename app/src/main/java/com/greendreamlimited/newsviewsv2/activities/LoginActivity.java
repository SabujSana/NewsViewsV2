package com.greendreamlimited.newsviewsv2.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.greendreamlimited.newsviewsv2.R;

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_READ_CONTACTS = 0;
    private static final int SIGNIN_REQUEST_CODE = 1;
    private LoginButton btnFBlogin;
    private CallbackManager callbackManager;
    private SignInButton btnGoogleLogin;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        initialization();
        fbLogin();
        googleSignin();
    }

    private void googleSignin() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        account = GoogleSignIn.getLastSignedInAccount(this);
        btnGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signinIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signinIntent, SIGNIN_REQUEST_CODE);
            }
        });
    }

    private void fbLogin() {
        callbackManager = CallbackManager.Factory.create();
        btnFBlogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login Cancel", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void initialization() {
        btnFBlogin = findViewById(R.id.btn_fb_login);
        btnGoogleLogin = findViewById(R.id.btn_google_login);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==SIGNIN_REQUEST_CODE){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSigninResult(task);
            Toast.makeText(getApplicationContext(), "Sign In Successfully", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }
    }

    private void handleSigninResult(Task<GoogleSignInAccount> completedTask) {
        try {
             account=completedTask.getResult(ApiException.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
