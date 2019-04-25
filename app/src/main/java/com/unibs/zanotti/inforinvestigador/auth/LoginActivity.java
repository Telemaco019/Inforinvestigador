package com.unibs.zanotti.inforinvestigador.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.*;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.navigation.MainNavigationActivity;
import com.unibs.zanotti.inforinvestigador.utils.StringUtils;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_GOOGLE_SIGN_IN = 9001;
    private static final String TAG = String.valueOf(LoginActivity.class);

    // View fields
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView passwordForgotLink;
    private SignInButton googleSignInButton;
    private LoginButton facebookSignInButton;

    // Authentication
    private GoogleSignInOptions mGso;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        mGso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.inforinvestigador_web_client_token_auth))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by mGso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGso);
        // Facebook callback manager
        callbackManager = CallbackManager.Factory.create();

        emailEditText = findViewById(R.id.login_input_email);
        passwordEditText = findViewById(R.id.login_input_password);
        loginButton = findViewById(R.id.btn_login);
        passwordForgotLink = findViewById(R.id.link_password_forgot);
        googleSignInButton = findViewById(R.id.login_google_signin_button);
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD);
        facebookSignInButton = findViewById(R.id.login_facebook_sign_inbutton);
        facebookSignInButton.setReadPermissions("email", "public_profile");

        loginButton.setOnClickListener(e ->
                firebaseAuthWithCredentials(emailEditText.getText().toString(), passwordEditText.getText().toString())
        );
        passwordForgotLink.setOnClickListener(e -> {
        });
        googleSignInButton.setOnClickListener(e -> googleSignIn());
        facebookSignInButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                authenticationFailed(facebookSignInButton, getString(R.string.facebook_login_error_message));
            }
        });
    }


    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, MainNavigationActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            // startActivity(new Intent(getApplicationContext(), MainNavigationActivity.class));
            // finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account);
                }
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                authenticationFailed(facebookSignInButton, getString(R.string.google_login_error_message));
            }
        }
    }


    private void firebaseAuthWithCredentials(String email, String password) {
        if (StringUtils.isBlank(email)) {
            emailEditText.setError(getResources().getString(R.string.msg_error_email_empty));
        }

        if (StringUtils.isBlank(password)) {
            passwordEditText.setError(getResources().getString(R.string.msg_error_password_empty));
        }

        if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(email)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail: success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail: failure", task.getException());
                            passwordEditText.setError(getResources().getString(R.string.msg_login_failed));
                            passwordForgotLink.setVisibility(View.VISIBLE);
                            authenticationFailed(loginButton, this.getString(R.string.authentication_failed_message));
                            updateUI(null);
                        }
                    });
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithGoogleCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithGoogleCredential:failure", task.getException());
                        authenticationFailed(googleSignInButton, this.getString(R.string.authentication_failed_message));
                        updateUI(null);
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithFacebookCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithFacebookCredential:failure", task.getException());
                        authenticationFailed(facebookSignInButton, this.getString(R.string.authentication_failed_message));
                        updateUI(null);
                    }
                });
    }

    /**
     * Show a snackbar for informing the user that the authentication process failed
     *
     * @param contextView
     */
    private void authenticationFailed(View contextView, String message) {
        Snackbar.make(contextView, message, Snackbar.LENGTH_LONG).show();
    }
}
