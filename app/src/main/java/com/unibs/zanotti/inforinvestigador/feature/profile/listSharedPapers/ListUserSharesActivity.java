package com.unibs.zanotti.inforinvestigador.feature.profile.listSharedPapers;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;

public class ListUserSharesActivity extends AppCompatActivity {

    public static final String STRING_EXTRA_USER_ID = "com.zanotti.inforinvestigador.ListUserSharesActivity.String.Extra.UserId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user_shares);
        String userId = getIntent().getStringExtra(STRING_EXTRA_USER_ID);

        ListUserSharesFragment fragment = (ListUserSharesFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = ListUserSharesFragment.newInstance(userId);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_frame);
        }
    }
}
