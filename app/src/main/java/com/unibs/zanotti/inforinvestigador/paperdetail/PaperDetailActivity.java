package com.unibs.zanotti.inforinvestigador.paperdetail;

import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;

public class PaperDetailActivity extends AppCompatActivity {
    public static final String EXTRA_PAPER_ID = "PAPER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_detail);
        setupSupportActionBar();

        String paperId = getIntent().getStringExtra(EXTRA_PAPER_ID);

        PaperDetailFragment paperDetailFragment = (PaperDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (paperDetailFragment == null) {
            paperDetailFragment = PaperDetailFragment.newInstance(paperId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), paperDetailFragment, R.id.content_frame);
        }

        new PaperDetailPresenter(paperId, paperDetailFragment);
    }

    private void setupSupportActionBar() {
        Toolbar toolbar = findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);

        // Show back icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set the title of the support action bar
        getSupportActionBar().setTitle(R.string.app_name);
        // Set the font of the support action bar
        TextView textView = (TextView) toolbar.getChildAt(0);
        textView.setTypeface(getResources().getFont(R.font.montserrat_light));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top_bar_view_paper, menu);
        return true;
    }
}
