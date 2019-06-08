package com.unibs.zanotti.inforinvestigador.profile.listFollowingAndFollowers;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.domain.model.User;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;

public class ListFollowingAndFollowersActivity extends AppCompatActivity {
    public static final String PARCELABLE_EXTRA_USER = "ListFollowingAndFollowersActivity.Extra.String.USER";
    public static final String INT_EXTRA_INITIAL_SELECTED_TAB = "ListFollowingAndFollowersActivity.Extra.String.INITIAL_SELECTED_TAB";
    public static final int LIST_FOLLOWING_AND_FOLLOWERS_ACTIVITY_REQUEST_CODE = 801;
    public static final int FOLLOWING_OR_FOLLOWERS_UPDATED_RC = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_following_and_followers);

        setupSupportActionBar();
        User user = getIntent().getParcelableExtra(PARCELABLE_EXTRA_USER);
        int initialTab = getIntent().getIntExtra(INT_EXTRA_INITIAL_SELECTED_TAB, 0);
        setSupportActionBarTitle(user.getName());

        ListFollowingAndFollowersFragment fragment = (ListFollowingAndFollowersFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = ListFollowingAndFollowersFragment.newInstance(user, initialTab);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_frame);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupSupportActionBar() {
        Toolbar toolbar = findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            // Show back icon
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // Set the font of the title
            //((TextView) toolbar.getChildAt(0)).setTypeface(getResources().getFont(R.font.montserrat_light));
        }
    }

    private void setSupportActionBarTitle(String title) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(title);
        }
    }
}
