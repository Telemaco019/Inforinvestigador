package com.unibs.zanotti.inforinvestigador.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
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

    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.login_input_email);
        passwordEditText = findViewById(R.id.login_input_password);
        loginButton = findViewById(R.id.btn_login);
        passwordForgotLink = findViewById(R.id.link_password_forgot);

        loginButton.setOnClickListener(e -> performLogin());
        passwordForgotLink.setOnClickListener(e -> {;});

    /*    emailEditText = findViewById(R.id.girisEmail);
        passwordEditText = findViewById(R.id.girisParola);
        loginButton = findViewById(R.id.girisButton);
        registerButton = findViewById(R.id.uyeOlButton);
        signInButton = findViewById(R.id.sign_in_button);
        newPassButton = findViewById(R.id.yeniSifreButton);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(LoginActivity.this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setOnClickListener(v -> signIn());
        loginButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String parola = passwordEditText.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(getApplicationContext(), "Please fill in the required fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(parola)) {
                Toast.makeText(getApplicationContext(), "Please fill in the required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (parola.length() < 6) {
                Toast.makeText(getApplicationContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, parola)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), MainNavigationActivity.class));
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "E-mail or passwordEditText is wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
        });




        if (mAuth.getCurrentUser() != null) {
            //startActivity(new Intent(getApplicationContext(), MainNavigationActivity.class));
        }*/

    }

    private void performLogin() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(StringUtils.isBlank(email)) {
            emailEditText.setError("Insert the email");
        }

        if(StringUtils.isBlank(password)) {
            passwordEditText.setError("Insert the password");
        }

        if(StringUtils.isNotBlank(password) && StringUtils.isNotBlank(email)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail: success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail: failure", task.getException());
                                passwordEditText.setError("Wrong username or password");
                                passwordForgotLink.setVisibility(View.VISIBLE);
                                updateUI(null);
                            }
                        }
                    });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is already logged in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MainNavigationActivity.class);
         //   startActivity(intent);
        }
    }

    private void signIn() {
        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signIntent, RC_SIGN_IN);
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

    private void authWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
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
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
