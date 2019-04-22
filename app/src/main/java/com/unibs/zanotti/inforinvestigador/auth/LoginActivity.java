package com.unibs.zanotti.inforinvestigador.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.navigation.MainNavigationActivity;
import com.unibs.zanotti.inforinvestigador.utils.StringUtils;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = String.valueOf(LoginActivity.class);


    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView passwordForgotLink;
    private SignInButton googleSignInButton;

    private GoogleSignInOptions mGso;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        mGso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by mGso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGso);

        emailEditText = findViewById(R.id.login_input_email);
        passwordEditText = findViewById(R.id.login_input_password);
        loginButton = findViewById(R.id.btn_login);
        passwordForgotLink = findViewById(R.id.link_password_forgot);

        loginButton.setOnClickListener(e ->
                authWithCredentials(emailEditText.getText().toString(), passwordEditText.getText().toString())
        );
        passwordForgotLink.setOnClickListener(e -> {
            ;
        });


    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, MainNavigationActivity.class);
            //   startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            //startActivity(new Intent(getApplicationContext(), MainNavigationActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                authWithGoogle(account);
            }
        }
    }


    private void authWithCredentials(String email, String password) {
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
                            updateUI(null);
                        }
                    });
        }
    }

    private void authWithGoogle(GoogleSignInAccount account) {
   /*     AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), MainNavigationActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Auth Error", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
