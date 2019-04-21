package com.unibs.zanotti.inforinvestigador.paperdetail;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.data.model.Comment;
import com.unibs.zanotti.inforinvestigador.data.source.local.PaperLocalDatasource;
import com.unibs.zanotti.inforinvestigador.data.source.local.dao.impl.DummyPaperLocalDao;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;

import java.util.ArrayList;

public class PaperDetailActivity extends AppCompatActivity {
    public static final String EXTRA_PAPER_ID = "PAPER_ID";
    public static final String EXTRA_COMMENTS_LIST = "COMMENTS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_detail);
        setupSupportActionBar();

        long paperId = getIntent().getLongExtra(EXTRA_PAPER_ID, 0l);
        ArrayList<Comment> comments = getIntent().getParcelableArrayListExtra(EXTRA_COMMENTS_LIST);

        PaperDetailFragment paperDetailFragment = (PaperDetailFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (paperDetailFragment == null) {
            paperDetailFragment = PaperDetailFragment.newInstance(paperId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), paperDetailFragment, R.id.content_frame);
        }

        new PaperDetailPresenter(paperId,
                new PaperLocalDatasource(new DummyPaperLocalDao()),
                paperDetailFragment,
                comments);
    }

    private void setupSupportActionBar() {
        Toolbar toolbar = findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);

        // Show back icon
        if (getSupportActionBar() != null) {
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
        inflater.inflate(R.menu.menu_top_bar_view_paper, menu);
        return true;
    }
}
