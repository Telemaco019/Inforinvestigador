package com.unibs.zanotti.inforinvestigador.feature.editPaper;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;

public class EditPaperShareActivity extends AppCompatActivity {

    public static final String STRING_EXTRA_PAPER_ID = "EditPaperShareActivity.extra.string.PaperId";
    public static final int EDIT_PAPER_SHARE_ACTIVITY_REQUEST_CODE = 900;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_paper_share);
        setupSupportActionBar();

        String paperId = getIntent().getStringExtra(STRING_EXTRA_PAPER_ID);

        EditPaperShareFragment editPaperShareFragment = (EditPaperShareFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (editPaperShareFragment == null) {
            editPaperShareFragment = EditPaperShareFragment.newInstance(paperId);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), editPaperShareFragment, R.id.content_frame);
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
}
