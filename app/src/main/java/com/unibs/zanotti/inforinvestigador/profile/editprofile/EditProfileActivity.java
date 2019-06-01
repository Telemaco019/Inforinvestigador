package com.unibs.zanotti.inforinvestigador.profile.editprofile;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;

public class EditProfileActivity extends AppCompatActivity {

    public static final String PARCELABLE_EXTRA_USER = "EditProfileActivity.Extra.String.USER";
    public static final int EDIT_PROFILE_ACTIVITY_REQUEST_CODE = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setupSupportActionBar();

        User user = getIntent().getParcelableExtra(PARCELABLE_EXTRA_USER);
        EditProfileFragment editProfileFragment = (EditProfileFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (editProfileFragment == null) {
            editProfileFragment = EditProfileFragment.newInstance(user);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), editProfileFragment, R.id.content_frame);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Put the item of the menu in the toolbar. Material Design is applied automatically
        inflater.inflate(R.menu.menu_top_bar_edit_profile, menu);

        return true;
    }

    private void setupSupportActionBar() {
        Toolbar toolbar = findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            // Show back icon
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // Set the title of the support action bar
            getSupportActionBar().setTitle(R.string.edit_profile_topbar_caption);
            // Set the font of the title
            ((TextView) toolbar.getChildAt(0)).setTypeface(getResources().getFont(R.font.montserrat_light));
        }
    }
}
