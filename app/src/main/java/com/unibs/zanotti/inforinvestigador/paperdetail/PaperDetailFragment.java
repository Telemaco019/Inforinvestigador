package com.unibs.zanotti.inforinvestigador.paperdetail;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.comments.ExpandableCommentGroup;
import com.unibs.zanotti.inforinvestigador.comments.ExpandableCommentItem;
import com.unibs.zanotti.inforinvestigador.comments.ReplyCommentActivity;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;
import com.unibs.zanotti.inforinvestigador.utils.Actions;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class PaperDetailFragment
        extends Fragment
        implements PaperDetailContract.View, ExpandableCommentItem.OnReplyClickedListener {

    private static final String TAG = String.valueOf(PaperDetailFragment.class);
    private static final String FRAGMENT_STRING_ARGUMENT_PAPER_ID = "PaperDetailFragment.argument.PAPER_ID";
    public static final String STRING_EXTRA_COMMENT_REPLY = "PaperDatailActivity.COMMENT_REPLY";
    private static final int RC_REPLY_TO_COMMENT = 1;

    private PaperDetailContract.Presenter presenter;
    /**
     * Group adapter for showing nested comments
     */
    private GroupAdapter<ViewHolder> groupAdapter;

    // View elements
    @BindView(R.id.paper_title)
    TextView paperTitle;
    @BindView(R.id.paper_abstract)
    TextView paperAbstract;
    @BindView(R.id.paper_date)
    TextView paperDate;
    @BindView(R.id.paper_authors)
    TextView paperAuthors;
    @BindView(R.id.paper_topics)
    TextView paperTopics;
    @BindView(R.id.paper_citations)
    TextView paperCitations;
    @BindView(R.id.paper_image)
    ImageView paperImage;
    @BindView(R.id.paper_doi)
    TextView paperDOI;
    @BindView(R.id.paper_publisher)
    TextView paperPublisher;
    @BindView(R.id.rv_paper_comments)
    RecyclerView rvComments;
    @BindView(R.id.paper_detail_comment_tf)
    EditText commentTf;
    @BindView(R.id.button_send_commment)
    ImageButton btnSendComment;
    @BindView(R.id.paper_detail_nested_scrollview)
    NestedScrollView scrollView;

    public PaperDetailFragment() {
        groupAdapter = new GroupAdapter<>();
    }

    public static PaperDetailFragment newInstance(String paperId) {
        Bundle arguments = new Bundle();
        arguments.putString(FRAGMENT_STRING_ARGUMENT_PAPER_ID, paperId);
        PaperDetailFragment fragment = new PaperDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paper_detail, container, false);
        ButterKnife.bind(this, view);

        // Setup recycler view
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), groupAdapter.getSpanCount());
        layoutManager.setSpanCount(groupAdapter.getSpanCount());
        rvComments.setAdapter(groupAdapter);
        rvComments.setLayoutManager(layoutManager);

        // Disable send comment button by default
        btnSendComment.setEnabled(false);

        commentTf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnSendComment.setEnabled(StringUtils.isNotBlank(s.toString()));
            }
        });

        return view;
    }


    @Override
    public void setPresenter(PaperDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showPaperTitle(String paperTitle) {
        this.paperTitle.setText(paperTitle);
    }

    @Override
    public void showPaperAbstract(String paperAbstract) {
        this.paperAbstract.setText(paperAbstract);
    }

    @Override
    public void showPaperAuthors(List<String> authors) {
        String authorsToString = String.join(", ", authors);
        this.paperAuthors.setText(authorsToString);
    }

    @Override
    public void showPaperPublisher(String publisher) {
        this.paperPublisher.setText(publisher);
    }

    @Override
    public void showPaperCitations(int citations) {
        this.paperCitations.setText(String.valueOf(citations));
    }

    @Override
    public void showPaperTopics(List<String> topics) {
        String topicsToString = String.join(", ", topics);
        this.paperTopics.setText(topicsToString);
    }

    @Override
    public void showPaperDOI(String DOI) {
        this.paperDOI.setText(DOI);
    }

    @Override
    public void showPaperDate(String date) {
        this.paperDate.setText(date);
    }

    @Override
    public void showPaperImage(int resImageId) {
        this.paperImage.setImageResource(resImageId);
    }

    @Override
    public void showComments(List<Comment> comments) {
        groupAdapter.addAll(generateExpandableCommentGroupList(comments));
    }

    @Override
    public void showNewComment(Comment newComment) {
        groupAdapter.add(this.generateExpandableCommentGroup(newComment));
    }

    /**
     * Dismiss the keyboard and clear the text of the input text field used for typing comments
     */
    @Override
    public void clearCommentInputField() {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ActivityUtils.dismissKeyboard(activity);
            commentTf.setText(null);
        }
    }

    @Override
    public void scrollViewToBottom() {
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    public void onReplyClicked(@NotNull Item<ViewHolder> item, @NotNull Comment comment) {
        Intent intent = new Intent(Actions.REPLY_TO_COMMENT);
        intent.putExtra(ReplyCommentActivity.STRING_EXTRA_REPLIED_COMMENT_BODY, comment.getBody());
        intent.putExtra(ReplyCommentActivity.STRING_EXTRA_REPLIED_COMMENT_AUTHOR, comment.getAuthor());
        startActivityForResult(intent, RC_REPLY_TO_COMMENT);

        // comment.setBody("AAAAAAAAAAAAAAAAAAAAAA");
        // item.notifyChanged();
        // groupAdapter.notifyItemChanged(groupAdapter.getAdapterPosition(item));
    }

    @OnClick(R.id.button_send_commment)
    public void onSendCommentClicked() {
        String comment = commentTf.getText().toString().trim();
        if (StringUtils.isNotBlank(comment)) {
            presenter.addComment(comment);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_REPLY_TO_COMMENT) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d(TAG, data.getStringExtra(STRING_EXTRA_COMMENT_REPLY));
            }
        }
    }

    private List<ExpandableCommentGroup> generateExpandableCommentGroupList(List<Comment> comments) {
        return comments.stream().map(this::generateExpandableCommentGroup).collect(Collectors.toList());
    }

    private ExpandableCommentGroup generateExpandableCommentGroup(Comment comment) {
        return new ExpandableCommentGroup(comment, 0, this);
    }
}
