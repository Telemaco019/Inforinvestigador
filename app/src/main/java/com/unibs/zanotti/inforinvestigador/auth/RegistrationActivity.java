package com.unibs.zanotti.inforinvestigador.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;
import com.unibs.zanotti.inforinvestigador.utils.StringUtils;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = String.valueOf(RegistrationActivity.class);

    private FirebaseAuth mAuth;

    private EditText inputTxtName;
    private EditText inputTxtSurname;
    private EditText inputTxtEmail;
    private EditText inputTxtPassword;
    private View undeterminedOverlayProgressBar;
    private Button btnSignup;
    private TextView loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();

        // This activity is supposed to be viewd only by non-authenticated users
        if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().isEmailVerified()) {
            finish();
            return;
        }

        initializeView();

        btnSignup.setOnClickListener(v -> signup());
        // Finish the registration and go back to the login activity
        loginLink.setOnClickListener(v -> finish());
    }

    private void initializeView() {
        inputTxtEmail = findViewById(R.id.registration_input_email);
        inputTxtName = findViewById(R.id.registration_input_name);
        inputTxtSurname = findViewById(R.id.registration_input_surname);
        inputTxtPassword = findViewById(R.id.registration_input_password);
        btnSignup = findViewById(R.id.registration_btn_signup);
        loginLink = findViewById(R.id.registration_link_login);
        undeterminedOverlayProgressBar = findViewById(R.id.undetermined_progress_bar);
    }

    public void signup() {
        Log.d(TAG, "a user is registering to Inforinvestigador");

        if (!validateFields()) {
            return;
        }

        // Disable signup button while performing signup
        btnSignup.setEnabled(false);

        // Show progress bar
        ActivityUtils.animateViewWithFade(undeterminedOverlayProgressBar, View.VISIBLE, 1, 200);

        // Retrieve user's input
        String name = inputTxtName.getText().toString();
        String surname = inputTxtSurname.getText().toString();
        String email = inputTxtEmail.getText().toString();
        String password = inputTxtPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    Log.d(TAG, String.format("creating new user with email [%s]", email));
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.d(TAG, String.format("could not create user [%s]\n\t%s", email, task.getException()));
                        checkEmailAlreadyRegistered(email);
                    } else {
                        Log.d(TAG, String.format("created user [name: %s | surname: %s | email: %s]", name, surname, email));
                        sendVerificationEmail(Objects.requireNonNull(mAuth.getCurrentUser()));
                    }
                });
    }

    private void checkEmailAlreadyRegistered(String email) {
        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().getSignInMethods().size() > 0) {
                    String errorMessage = String.format(getString(R.string.registration_duplicated_email_error), email);
                    inputTxtEmail.setError(errorMessage);
                    onSignupFailed(null);
                }
            } else {
                onSignupFailed(getString(R.string.registration_generic_error));
            }
        });
    }

    private void sendVerificationEmail(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, String.format("verification email sent to [%s]", user.getEmail()));
                        // Show snackbar. After it shows and dismisses navigate to login activity
                        Snackbar.make(btnSignup, String.format(
                                getString(R.string.registration_verification_email_sent_message),
                                user.getEmail()),
                                Snackbar.LENGTH_LONG)
                                .addCallback(new Snackbar.Callback() {
                                    @Override
                                    public void onDismissed(Snackbar transientBottomBar, int event) {
                                        super.onDismissed(transientBottomBar, event);
                                        onSignupSuccess();
                                    }
                                }).show();
                    } else {
                        Log.d(TAG, String.format("error sending verification email to [%s]\n%s",
                                user.getEmail(),
                                Objects.requireNonNull(task.getException()).toString()));
                        // Could not send verification email, thus delete the created user
                        user.delete();
                        onSignupFailed(getString(R.string.registration_generic_error));
                    }
                });
    }

    public void onSignupSuccess() {
        btnSignup.setEnabled(true);
        ActivityUtils.animateViewWithFade(undeterminedOverlayProgressBar, View.VISIBLE, 1, 200);
        // after email is sent just logout the user and finish this activity
        FirebaseAuth.getInstance().signOut();
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed(String message) {
        ActivityUtils.animateViewWithFade(undeterminedOverlayProgressBar, View.GONE, 0, 200);
        btnSignup.setEnabled(true);
        if (StringUtils.isNotBlank(message)) {
            Snackbar.make(btnSignup, message, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Validate the values inserted by the user in the registration form fields
     *
     * @return True if all the fields are valid, false otherwise
     */
    public boolean validateFields() {
        boolean valid = true;

        String name = inputTxtName.getText().toString();
        String email = inputTxtEmail.getText().toString();
        String password = inputTxtPassword.getText().toString();
        String surname = inputTxtSurname.getText().toString();

        if (StringUtils.isBlank(name) || name.length() < 3) {
            Log.d(TAG, "validateFields: inserted name is not long enough");
            inputTxtName.setError("at least 3 characters");
            valid = false;
        } else {
            inputTxtName.setError(null);
        }

        if (StringUtils.isBlank(surname) || name.length() < 3) {
            Log.d(TAG, "validateFields: inserted name is not long enough");
            inputTxtName.setError("at least 3 characters");
            valid = false;
        } else {
            inputTxtName.setError(null);
        }

        if (StringUtils.isBlank(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Log.d(TAG, "validateFields: inserted email is not valid");
            inputTxtEmail.setError("enter a valid email address");
            valid = false;
        } else {
            inputTxtEmail.setError(null);
        }

        if (StringUtils.isBlank(password) || password.length() < 4 || password.length() > 10) {
            Log.d(TAG, "validateFields: inserted password is not valid");
            inputTxtPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            inputTxtPassword.setError(null);
        }

        return valid;
    }
}
