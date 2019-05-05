package com.unibs.zanotti.inforinvestigador.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.domain.utils.DateUtils;
import com.unibs.zanotti.inforinvestigador.navigation.MainNavigationActivity;
import com.unibs.zanotti.inforinvestigador.utils.Injection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;

/**
 * Meant to check the authentication state of the user. If the user is already authenticated (through firebase), then
 * update the respective entity in the DB and start the main app activity, otherwise redirect the user to the login
 * activity.
 * <p>While the authentication checking process (and DB writing) is performed, the user sees the screen corresponding
 * to the theme defined for this activity </p>
 */
public class SplashActivity extends AppCompatActivity {
    private static final String TAG = String.valueOf(SplashActivity.class);
    private FirebaseAuth mAuth;
    private CompositeDisposable disposables;

    public SplashActivity() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        disposables = new CompositeDisposable();

        // Check if the user is already authenticated. Reload for getting the latest updated user from Firebase
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
            currentUser.reload().addOnCompleteListener(aVoid -> {
                // Update the user in the db
                disposables.add(Injection.provideUserRepository()
                        .saveUpdateUser(new User(currentUser.getUid(),
                                currentUser.getEmail(),
                                currentUser.getDisplayName(),
                                currentUser.getPhotoUrl(),
                                DateUtils.fromEpochTimestampMillis(currentUser.getMetadata().getCreationTimestamp())))
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                // Start the main activity
                                startMainActivity();
                            }

                            @Override
                            public void onError(Throwable e) {
                                // Start the login activity
                                Log.d(TAG, e.getMessage());
                            }
                        }));
            });
        else {
            startLoginActivity();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposables.dispose();
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainNavigationActivity.class));
        finish();
    }
}
