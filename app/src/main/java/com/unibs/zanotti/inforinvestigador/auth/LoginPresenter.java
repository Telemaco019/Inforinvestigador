package com.unibs.zanotti.inforinvestigador.auth;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unibs.zanotti.inforinvestigador.utils.StringUtils;

public class LoginPresenter implements LoginContract.Presenter, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = String.valueOf(LoginPresenter.class);

    private LoginContract.View mView;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    public LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
        mView.setPresenter(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void start() {
        // Check if user already logged in
        mView.updateUI(mAuth.getCurrentUser());
    }

    @Override
    public void performLogin(String email, String password) {
        if (StringUtils.isBlank(email)) {
            mView.showInputEmailError("Insert the email");
        }

        if (StringUtils.isBlank(password)) {
            mView.showInputPasswordError("Insert the password");
        }

        if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(email)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail: success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                mView.updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail: failure", task.getException());
                                mView.showInputPasswordError("Wrong username or password");
                                mView.showPasswordForgotLink();
                                mView.updateUI(null);
                            }
                        }
                    });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
