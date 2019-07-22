package com.unibs.zanotti.inforinvestigador.feature.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;

public class ProfileActivity extends AppCompatActivity {
    public static final String STRING_EXTRA_RESEARCHER_ID = "ProfileActivity.Extra.String.RESEARCHER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupSupportActionBar();

        String researcherId = getIntent().getStringExtra(STRING_EXTRA_RESEARCHER_ID);

        ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (profileFragment == null) {
            profileFragment = ProfileFragment.newInstance(researcherId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), profileFragment, R.id.content_frame);
        }
    }

    private void setupSupportActionBar() {
        Toolbar toolbar = findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            // Show back icon
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // Set the title of the support action bar
            getSupportActionBar().setTitle(R.string.app_name);
            // Set the font of the title
            ((TextView) toolbar.getChildAt(0)).setTypeface(getResources().getFont(R.font.montserrat_light));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.setResult(Activity.RESULT_CANCELED);
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
