package com.unibs.zanotti.inforinvestigador.profile.editprofile;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;

public class EditProfileActivity extends AppCompatActivity {

    public static final String STRING_EXTRA_USER_ID = "EditProfileActivity.Extra.String.USER_ID";
    public static final int EDIT_PROFILE_ACTIVITY_REQUEST_CODE = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setResult(Activity.RESULT_OK);
        String userId = getIntent().getStringExtra(STRING_EXTRA_USER_ID);
        EditProfileFragment editProfileFragment = (EditProfileFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (editProfileFragment == null) {
            editProfileFragment = EditProfileFragment.newInstance(userId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), editProfileFragment, R.id.content_frame);
        }
    }
}
