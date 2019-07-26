package com.unibs.zanotti.inforinvestigador.feature.paperdetail;


import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseFragment;
import com.unibs.zanotti.inforinvestigador.domain.model.Comment;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;
import com.unibs.zanotti.inforinvestigador.feature.paperdetail.adapter.CommentsAdapter;
import com.unibs.zanotti.inforinvestigador.feature.paperdetail.adapter.ImageSliderAdapter;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;
import com.unibs.zanotti.inforinvestigador.utils.Injection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PaperDetailFragment
        extends BaseFragment<PaperDetailContract.View, PaperDetailContract.Presenter>
        implements PaperDetailContract.View, CommentsAdapter.CommentLikeListener {

    private static final String FRAGMENT_STRING_ARGUMENT_PAPER_ID = "PaperDetailFragment.argument.PAPER_ID";

    private CommentsAdapter commentsAdapter;

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
    @BindView(R.id.undetermined_progress_bar)
    View progressBar;
    @BindView(R.id.content_layout)
    View contentLayout;
    @BindView(R.id.imageSlider)
    SliderView sliderView;
    private ImageSliderAdapter imageSliderAdapter;

    public PaperDetailFragment() {
        commentsAdapter = new CommentsAdapter(new ArrayList<>(), this);
    }

    public static PaperDetailFragment newInstance(String paperId) {
        Bundle arguments = new Bundle();
        arguments.putString(FRAGMENT_STRING_ARGUMENT_PAPER_ID, paperId);
        PaperDetailFragment fragment = new PaperDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paper_detail, container, false);
        ButterKnife.bind(this, view);

        // Setup recycler view
        rvComments.setAdapter(commentsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
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

        // Setup image slider
        imageSliderAdapter = new ImageSliderAdapter(getContext());
        sliderView.setSliderAdapter(imageSliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

        return view;
    }

    @Override
    public void showPaperImages(List<Uri> paperImagesUriList) {
        if (sliderView.getVisibility() != View.VISIBLE) {
            sliderView.setVisibility(View.VISIBLE);
        }

        imageSliderAdapter.setDataset(paperImagesUriList);
        imageSliderAdapter.notifyDataSetChanged();
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
    public void scrollViewToFirstComment() {
        if (rvComments.getAdapter() != null && rvComments.getAdapter().getItemCount() > 0) {
            float y = rvComments.getY() + rvComments.getChildAt(0).getY() - 250; // -250px for having some offset
            scrollView.smoothScrollTo(0, (int) y);
        }
    }

    @Override
    public void showComment(Comment comment) {
        commentsAdapter.updateDataset(comment);
    }

    @Override
    public void hideProgressBar() {
        ActivityUtils.animateViewWithFade(progressBar, View.GONE, 0f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
    }

    @Override
    public void showContentLayout() {
        if (contentLayout.getVisibility() != View.VISIBLE) {
            ActivityUtils.animateViewWithFade(contentLayout, View.VISIBLE, 1f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
        }
    }

    @OnClick(R.id.button_send_commment)
    public void onSendCommentClicked() {
        String comment = commentTf.getText().toString().trim();
        if (StringUtils.isNotBlank(comment)) {
            presenter.addComment(comment);
        }
    }

    @Override
    protected PaperDetailContract.Presenter createPresenter() {
        String string = getArguments().getString(FRAGMENT_STRING_ARGUMENT_PAPER_ID);
        return new PaperDetailPresenter(string,
                Injection.providePaperRepository(),
                Injection.provideUserRepository());
    }

    @Override
    public void onLikeClicked(Comment comment) {
        presenter.commentLikeClicked(comment);
    }
}
