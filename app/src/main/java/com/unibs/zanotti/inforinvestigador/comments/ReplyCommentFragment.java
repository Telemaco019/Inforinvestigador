package com.unibs.zanotti.inforinvestigador.comments;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;

public class ReplyCommentFragment extends Fragment implements ReplyCommentContract.View {
    private static final String FRAGMENT_PARCELABLE_ARGUMENT_PARENT_COMMENT = "ReplyCommentFragment.argument.PARENT_COMMENT";

    @BindView(R.id.reply_to_comment_author_name)
    TextView authorName;
    @BindView(R.id.reply_to_comment_replied_comment_body)
    TextView repliedCommentBody;
    @BindView(R.id.reply_to_comment_edit_text)
    EditText editText;

    private ReplyCommentContract.Presenter mPresenter;

    public ReplyCommentFragment() {
        // Required empty public constructor
    }

    public static ReplyCommentFragment newInstance(Comment parentComment) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(FRAGMENT_PARCELABLE_ARGUMENT_PARENT_COMMENT, parentComment);
        ReplyCommentFragment fragment = new ReplyCommentFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reply_comment, container, false);

        ButterKnife.bind(this, view);
        setupSupportActionBar(view);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.stop();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
                break;
            }
            case R.id.topbar_reply_comment_send_btn: {
                getActivity().setResult(Activity.RESULT_OK);
                Comment parentComment = getArguments().getParcelable(FRAGMENT_PARCELABLE_ARGUMENT_PARENT_COMMENT);
                mPresenter.addReply(parentComment, editText.getText().toString());
                getActivity().finish();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Put the item of the menu in the toolbar. Material Design is applied automatically
        inflater.inflate(R.menu.menu_top_bar_reply_comment, menu);
        // Get toolbar
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
    }

    @Override
    public void setPresenter(ReplyCommentContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    private void setupSupportActionBar(View view) {
        // Set the toolbar layout as toolbar
        Toolbar toolbar = view.findViewById(R.id.top_bar);
        toolbar.setTitle(getString(R.string.reply_to_comment_caption));

        AppCompatActivity parentActivity = (AppCompatActivity) getActivity();
        parentActivity.setSupportActionBar(toolbar);
        ActionBar supportActionBar = parentActivity.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
