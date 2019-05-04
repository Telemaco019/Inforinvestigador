package com.unibs.zanotti.inforinvestigador.comments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;
import com.unibs.zanotti.inforinvestigador.paperdetail.PaperDetailFragment;

public class ReplyCommentActivity extends AppCompatActivity {
    public static final String STRING_EXTRA_REPLIED_COMMENT_BODY = "ReplyCommentActivity.extra.REPLIED_COMMENT_BODY";
    public static final String STRING_EXTRA_REPLIED_COMMENT_AUTHOR = "ReplyCommentActivity.extra.REPLIED_COMMENT_AUTHOR";

    @BindView(R.id.reply_to_comment_author_name)
    TextView authorName;
    @BindView(R.id.reply_to_comment_replied_comment_body)
    TextView repliedCommentBody;
    @BindView(R.id.reply_to_comment_edit_text)
    EditText editText;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_comment);
        ButterKnife.bind(this);
        setupSupportActionBar();

        intent = new Intent();

        repliedCommentBody.setText(getIntent().getStringExtra(STRING_EXTRA_REPLIED_COMMENT_BODY));
        authorName.setText(getIntent().getStringExtra(STRING_EXTRA_REPLIED_COMMENT_AUTHOR));
    }

    private void setupSupportActionBar() {
        // Set the toolbar layout as toolbar
        Toolbar toolbar = findViewById(R.id.top_bar);
        toolbar.setTitle(getString(R.string.reply_to_comment_caption));
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Put the item of the menu in the toolbar. Material Design is applied automatically
        inflater.inflate(R.menu.menu_top_bar_reply_comment, menu);

        final MenuItem toolbarSendBtn = menu.findItem(R.id.topbar_reply_comment_send_btn);

        // Disable send button by default
        toolbarSendBtn.setEnabled(false);
        // Enable/Disable send button according to edit text content
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                toolbarSendBtn.setEnabled(StringUtils.isNotBlank(s.toString()));
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                this.setResult(Activity.RESULT_CANCELED,intent);
                finish();
                break;
            }
            case R.id.topbar_reply_comment_send_btn: {
                this.setResult(Activity.RESULT_OK,intent);
                intent.putExtra(PaperDetailFragment.STRING_EXTRA_COMMENT_REPLY, editText.getText().toString());
                finish();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
