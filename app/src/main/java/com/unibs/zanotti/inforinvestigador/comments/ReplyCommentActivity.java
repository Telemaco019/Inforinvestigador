package com.unibs.zanotti.inforinvestigador.comments;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;
import com.unibs.zanotti.inforinvestigador.utils.Injection;

public class ReplyCommentActivity extends AppCompatActivity {
    public static final String STRING_EXTRA_REPLIED_COMMENT_BODY = "ReplyCommentActivity.extra.REPLIED_COMMENT_BODY";
    public static final String STRING_EXTRA_REPLIED_COMMENT_AUTHOR = "ReplyCommentActivity.extra.REPLIED_COMMENT_AUTHOR";
    public static final String PARCELABLE_EXTRA_PARENT_COMMENT = "PaperDatailActivity.PARENT_COMMENT";

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_comment);


        Comment parentComment = getIntent().getParcelableExtra(PARCELABLE_EXTRA_PARENT_COMMENT);

        ReplyCommentFragment fragment = (ReplyCommentFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = ReplyCommentFragment.newInstance(parentComment);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.content_frame);
        }

        new ReplyCommentPresenter(fragment, Injection.providePaperRepository(),Injection.provideUserRepository());
    }
}
