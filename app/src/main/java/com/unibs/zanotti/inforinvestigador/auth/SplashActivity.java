package com.unibs.zanotti.inforinvestigador.auth;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.navigation.MainNavigationActivity;
import io.reactivex.disposables.CompositeDisposable;

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

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startMainActivity();
        } else {
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
