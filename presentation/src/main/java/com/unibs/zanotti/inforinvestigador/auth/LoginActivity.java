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
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.shobhitpuri.custombuttons.GoogleSignInButton;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.domain.IUserRepository;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;
import com.unibs.zanotti.inforinvestigador.navigation.MainNavigationActivity;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;
import com.unibs.zanotti.inforinvestigador.utils.Injection;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_GOOGLE_SIGN_IN = 9001;
    public static final int PROGRESS_BAR_FADEIN_DURATION = 300;
    private static final String TAG = String.valueOf(LoginActivity.class);

    // View fields
    @BindView(R.id.login_input_email)
    EditText emailEditText;
    @BindView(R.id.login_input_password)
    EditText passwordEditText;
    @BindView(R.id.login_credentials_signin_button)
    Button loginButton;
    @BindView(R.id.link_password_forgot)
    TextView passwordForgotLink;
    @BindView(R.id.login_google_signin_button)
    GoogleSignInButton googleSignInButton;
    @BindView(R.id.login_link_signup)
    TextView signupLink;
    @BindView(R.id.link_resend_verification_email)
    TextView resendVerificationEmailLink;
    @BindView(R.id.undetermined_progress_bar)
    @Nullable
    View progressBar;

    // Authentication
    private GoogleSignInOptions mGso;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    // Services
    private IUserRepository userService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initializeAuth();
        setupButtonListeners();

        // FIXME: use DI
        userService = Injection.provideUserRepository();

        // FirebaseUtils.populatePapersCollection();
    }

    private void setupButtonListeners() {
        loginButton.setOnClickListener(e -> {
            ActivityUtils.dismissKeyboard(this);
            firebaseAuthWithEmailPassword(emailEditText.getText().toString(), passwordEditText.getText().toString());
        });

        signupLink.setOnClickListener(e -> {
            Intent intent = new Intent(this, RegistrationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        passwordForgotLink.setOnClickListener(e -> {
        });

        googleSignInButton.setOnClickListener(e -> googleSignIn());

        resendVerificationEmailLink.setOnClickListener(e -> resendVerificationEmail());

        passwordForgotLink.setOnClickListener(e -> {
            sendPasswordResetLink();
            passwordEditText.setError(null);
        });
    }

    private void initializeAuth() {
        mAuth = FirebaseAuth.getInstance();
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        mGso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.inforinvestigador_web_client_token_auth))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by mGso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGso);
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    /**
     * Update the user interface according to the firebase user provided as argument: if the user is null
     * (e.g. the login failed) then do nothing, otherwise, if the user is not null (e.g. the login was
     * successful, check if the user if email-verified and in case open the main application activity, otherwise
     * show a message for informing the user that email-verification is needed
     *
     * @param user
     */
    private void updateUI(FirebaseUser user) {
        // Hide progress bar
        ActivityUtils.animateViewWithFade(progressBar, View.GONE, 0f, PROGRESS_BAR_FADEIN_DURATION);

        if (user != null) {
            if (user.isEmailVerified()) {
                Log.d(TAG, String.format("%s logged into Inforinvestigador", user.getEmail()));
                Intent intent = new Intent(this, MainNavigationActivity.class);
                startActivity(intent);
                finish();
            } else {
                manageEmailNotVerified();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Reload for getting the update user from Firebase
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload()
                    .addOnSuccessListener(aVoid -> updateUI(mAuth.getCurrentUser()))
                    .addOnFailureListener(aVoid -> updateUI(mAuth.getCurrentUser()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
                authenticationFailed(googleSignInButton, getString(R.string.google_login_error_message));
            }
        }
    }


    private void firebaseAuthWithEmailPassword(String email, String password) {
        ActivityUtils.animateViewWithFade(progressBar, View.VISIBLE, 1f, PROGRESS_BAR_FADEIN_DURATION);

        emailEditText.setError(
                StringUtils.isBlank(email) ?
                        getResources().getString(R.string.msg_error_email_empty) :
                        null
        );
        passwordEditText.setError(
                StringUtils.isBlank(password) ?
                        getResources().getString(R.string.msg_error_password_empty) :
                        null
        );

        if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(email)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmailPasswordCredentials: success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmailPasswordCredentials: failure", task.getException());
                            passwordEditText.setError(getResources().getString(R.string.authentication_failed_wrong_credentials_message));
                            passwordForgotLink.setVisibility(View.VISIBLE);
                            updateUI(null);
                        }
                    });
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        // Show progress bar
        ActivityUtils.animateViewWithFade(progressBar, View.VISIBLE, 1, PROGRESS_BAR_FADEIN_DURATION);

        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Update UI with the signed-in user's information
                        Log.d(TAG, "signInWithGoogleCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        authenticationFailed(googleSignInButton, this.getString(R.string.authentication_failed_generic_message));
                        Log.w(TAG, "signInWithGoogleCredential:failure", task.getException());
                        updateUI(null);
                    }
                });
    }


    /**
     * Manage the event that occurs when the user tries to log in with the right credentials but the
     * used email address has not been verified yet
     */
    private void manageEmailNotVerified() {
        Log.d(TAG, "attemp to login with un-verified email");
        emailEditText.setError("This email has not been verified yet");
        resendVerificationEmailLink.setVisibility(View.VISIBLE);
    }


    /**
     * Resend the verification email to the current logged in (and not verified) user. If there is no user logged in
     * then do nothing.
     */
    private void resendVerificationEmail() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, String.format("verification email sent to [%s]", currentUser.getEmail()));
                            // Show snackbar. After it shows and dismisses navigate to login activity
                            Snackbar.make(resendVerificationEmailLink, String.format(
                                    getString(R.string.registration_verification_email_sent_message),
                                    currentUser.getEmail()),
                                    Snackbar.LENGTH_LONG)
                                    .show();
                        } else {
                            Log.d(TAG, String.format("error sending verification email to [%s]\n%s",
                                    currentUser.getEmail(),
                                    Objects.requireNonNull(task.getException()).toString()));
                            Snackbar.make(resendVerificationEmailLink, getString(R.string.send_verification_email_generic_error), Snackbar.LENGTH_LONG).show();
                        }
                    });
        }
    }


    /**
     * Send to the email address currently typed into {@link LoginActivity#emailEditText} a link for restoring
     * the password of the account of the user associated to that email (if exists). If the typed email address is
     * not valid then do nothing. After successfully sending the email with the link for resetting the password, inform
     * the user with a snackbar.
     */
    private void sendPasswordResetLink() {
        String emailAddress = emailEditText.getText().toString();
        if (StringUtils.isNotBlank(emailAddress)) {
            mAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(task ->
                    Snackbar.make(
                            passwordForgotLink,
                            String.format(getString(R.string.login_message_reset_pass_email_sent), emailAddress),
                            Snackbar.LENGTH_LONG
                    ).show()
            );
        }
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
