package com.unibs.zanotti.inforinvestigador.feature.profile.listSharedPapers;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;

public class ListUserSharesActivity extends AppCompatActivity {

    public static final String STRING_EXTRA_USER_ID = "com.zanotti.inforinvestigador.ListUserSharesActivity.String.Extra.UserId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_shares);
        setupSupportActionBar();
        String userId = getIntent().getStringExtra(STRING_EXTRA_USER_ID);

        ListUserSharesFragment fragment = (ListUserSharesFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = ListUserSharesFragment.newInstance(userId);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_frame);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Put the item of the menu in the toolbar. Material Design is applied automatically
        inflater.inflate(R.menu.menu_top_bar_with_search, menu);

        return true;
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
