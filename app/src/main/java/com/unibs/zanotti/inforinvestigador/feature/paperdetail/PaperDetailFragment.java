package com.unibs.zanotti.inforinvestigador.feature.paperdetail;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
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
    @BindView(R.id.paper_detail_slider_view_container)
    MaterialCardView sliderViewContainer;
    @BindView(R.id.paper_detail_gotosource_button)
    MaterialButton gotoSourceButton;
    @BindView(R.id.snabckar_view)
    CoordinatorLayout snackbarView;

    private Toolbar toolbar;

    private ImageSliderAdapter imageSliderAdapter;
    private boolean showAddToLibraryMenu = false;

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

        // Enables option menu listener (onOptionsItemSelected gets called)
        setHasOptionsMenu(true);

        if (getActivity() != null) {
            toolbar = getActivity().findViewById(R.id.top_bar);
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (showAddToLibraryMenu) {
            inflater.inflate(R.menu.menu_top_bar_paper_details_not_in_library, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void refreshToolbarMenu(boolean libraryContainsPaper) {
        this.showAddToLibraryMenu = !libraryContainsPaper;
        if (getActivity() != null) {
            getActivity().invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (getActivity() == null) {
            return super.onOptionsItemSelected(item);
        }

        if (item.getItemId() == android.R.id.home) {
            getActivity().setResult(Activity.RESULT_CANCELED);
            getActivity().finish();
        }

        if (item.getItemId() == R.id.menu_topbar_innermenu_addToLibrary) {
            presenter.addToLibraryClicked();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showPaperImages(List<Uri> paperImagesUriList) {
        if (sliderViewContainer.getVisibility() != View.VISIBLE) {
            sliderViewContainer.setVisibility(View.VISIBLE);
        }

        imageSliderAdapter.setDataset(paperImagesUriList);
        imageSliderAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.paper_detail_gotosource_button)
    void onGotoSourceButtonClicked() {
        presenter.gotoSourceButtonClicked();
    }

    @Override
    public void showPaperTitle(String paperTitle) {
        this.paperTitle.setText(paperTitle);
    }

    @Override
    public void showPaperAbstract(String paperAbstract) {
        if (this.paperAbstract.getVisibility() != View.VISIBLE) {
            this.paperAbstract.setVisibility(View.VISIBLE);
        }
        this.paperAbstract.setText(paperAbstract);
    }

    @Override
    public void showPaperAuthors(List<String> authors) {
        if (paperAuthors.getVisibility() != View.VISIBLE) {
            paperAuthors.setVisibility(View.VISIBLE);
        }
        String authorsToString = String.join(", ", authors);
        this.paperAuthors.setText(authorsToString);
    }

    @Override
    public void showPaperPublisher(String publisher) {
        if (paperPublisher.getVisibility() != View.VISIBLE) {
            paperPublisher.setVisibility(View.VISIBLE);
        }
        this.paperPublisher.setText(publisher);
    }

    @Override
    public void showPaperCitations(int citations) {
        this.paperCitations.setText(String.format("%d %s", citations, getResources().getString(R.string.citations_caption)));
    }

    @Override
    public void showPaperTopics(List<String> topics) {
        if (paperTopics.getVisibility() != View.VISIBLE) {
            paperTopics.setVisibility(View.VISIBLE);
        }
        String topicsToString = String.join(", ", topics);
        this.paperTopics.setText(topicsToString);
    }

    @Override
    public void showPaperDOI(String DOI) {
        this.paperDOI.setText(DOI);
    }

    @Override
    public void showPaperDate(String date) {
        if (paperDate.getVisibility() != View.VISIBLE) {
            paperDate.setVisibility(View.VISIBLE);
        }
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

    @Override
    public void navigateToPaperSource(String url) {
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browse);
    }

    @Override
    public void disableGotoSourceButton() {
        gotoSourceButton.setEnabled(false);
    }

    @Override
    public void showMessagePaperAddedToLibrary() {
        if (getView() != null) {
            Snackbar.make(snackbarView, getString(R.string.paper_added_to_library_message), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showMessageCannotAddPaperToLibrary() {
        if (getView() != null) {
            Snackbar.make(snackbarView, getString(R.string.generic_error_message), Snackbar.LENGTH_LONG).show();
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
