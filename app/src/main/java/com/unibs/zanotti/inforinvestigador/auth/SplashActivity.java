package com.unibs.zanotti.inforinvestigador.auth;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.navigation.MainNavigationActivity;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    public SplashActivity() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        // Check if the user is already authenticated. Reload for getting the latest updated user from Firebase
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
            currentUser.reload().addOnCompleteListener(aVoid -> startMainActivity());
        else
            startLoginActivity();
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
