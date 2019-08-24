package com.unibs.zanotti.inforinvestigador.feature.addPaper;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseFragment;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import com.unibs.zanotti.inforinvestigador.domain.utils.StringUtils;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;
import com.unibs.zanotti.inforinvestigador.utils.Injection;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPaperFragment extends BaseFragment<AddPaperContract.View, AddPaperContract.Presenter>
        implements AddPaperContract.View {

    @BindView(R.id.add_paper_button_search_paper)
    ImageButton searchPaperButton;
    @BindView(R.id.add_paper_doi_edit_text)
    EditText paperDoiEditText;
    @BindView(R.id.undetermined_progress_bar)
    FrameLayout progressBar;
    @BindView(R.id.add_paper_sharing_comment_edit_text)
    EditText commentEditText;
    @BindView(R.id.add_paper_share_paper_button)
    MaterialButton submitButton;
    @BindView(R.id.add_paper_share_cancel_button)
    MaterialButton cancelButton;
    @BindView(R.id.paper_details_layout_container)
    LinearLayout paperDetailsLayoutContainer;

    @BindView(R.id.add_edit_paper_title)
    EditText paperTitleEditText;
    @BindView(R.id.add_edit_paper_authors)
    EditText paperAuthorsEditText;
    @BindView(R.id.add_edit_paper_date)
    EditText paperDateEditText;
    @BindView(R.id.add_edit_paper_publisher)
    EditText paperPublisherEditText;
    @BindView(R.id.add_edit_paper_citations)
    EditText paperCitationsEditText;
    @BindView(R.id.add_edit_paper_abstract)
    EditText paperAbstractEditText;

    @BindView(R.id.add_paper_buttons_layout)
    RelativeLayout submitButtonsLayout;

    public AddPaperFragment() {
        // Required empty public constructor
    }

    public static AddPaperFragment newInstance() {
        return new AddPaperFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_paper, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    protected AddPaperContract.Presenter createPresenter() {
        return new AddPaperPresenter(Injection.provideCrossrefService(),
                Injection.providePaperRepository(),
                Injection.provideUserRepository());
    }

    @OnClick(R.id.add_paper_button_search_paper)
    void onSearchPaperButtonClicked() {
        String doi = paperDoiEditText.getText().toString();
        paperDoiEditText.setError(null);
        if (StringUtils.isBlank(doi)) {
            paperDoiEditText.setError(getString(R.string.field_required_error_msg));
        } else {
            presenter.searchPaperButtonClicked(doi);
        }
    }

    @OnClick(R.id.add_paper_share_cancel_button)
    void onCancelButtonClicked() {
        presenter.cancelButtonClicked();
    }

    @OnClick(R.id.add_paper_share_paper_button)
    void onSubmitButtonClicked() {
        if (StringUtils.isBlank(commentEditText.getText().toString())) {
            commentEditText.setError(getString(R.string.field_required_error_msg));
        } else {
            presenter.submitButtonClicked(commentEditText.getText().toString());
        }
    }

    @Override
    public void showProgressBar() {
        ActivityUtils.animateViewWithFade(progressBar, View.VISIBLE, 1f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
    }

    @Override
    public void hideProgressBar() {
        ActivityUtils.animateViewWithFade(progressBar, View.GONE, 0f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
    }

    @Override
    public void showMessageDoiNotFound() {
        paperDoiEditText.setError(getString(R.string.add_paper_doi_not_found_message));
    }

    @Override
    public void showPaper(Paper paper) {
        ActivityUtils.dismissKeyboard(Objects.requireNonNull(getActivity()));
        ActivityUtils.animateViewWithFade(paperDetailsLayoutContainer, View.VISIBLE, 1f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
        ActivityUtils.animateViewWithFade(commentEditText, View.VISIBLE, 1f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
        ActivityUtils.animateViewWithFade(submitButtonsLayout, View.VISIBLE, 1f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);

        setText(paperTitleEditText, paper.getPaperTitle());
        setText(paperAuthorsEditText, paper.getPaperAuthors().isEmpty() ? StringUtils.BLANK : String.join(", ", paper.getPaperAuthors()));
        setText(paperDateEditText, paper.getPaperDate());
        setText(paperPublisherEditText, paper.getPaperPublisher());
        setText(paperCitationsEditText, paper.getPaperCitations() == null ? StringUtils.BLANK : String.valueOf(paper.getPaperCitations()));
        setText(paperAbstractEditText, paper.getPaperAbstract());
    }

    @Override
    public void hidePaper() {
        ActivityUtils.animateViewWithFade(paperDetailsLayoutContainer, View.GONE, 0f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
        ActivityUtils.animateViewWithFade(commentEditText, View.GONE, 0f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
        ActivityUtils.animateViewWithFade(submitButtonsLayout, View.GONE, 0f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
    }

    @Override
    public void clearDoiTextfield() {
        paperDoiEditText.setText(StringUtils.BLANK);
    }

    @Override
    public void clearCommentTextfield() {
        commentEditText.setText(StringUtils.BLANK);
    }

    @Override
    public void showSavePaperSuccessMessage() {
        Snackbar.make(submitButton,
                getString(R.string.share_paper_success_message),
                Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showSavePaperErrorMessage() {
        Snackbar.make(submitButton,
                getString(R.string.share_paper_error_message),
                Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void showSearchPaperError() {
        Snackbar.make(searchPaperButton,
                getString(R.string.search_paper_error),
                Snackbar.LENGTH_LONG)
                .show();
    }

    private void setText(EditText editText, String contentText) {
        if (StringUtils.isBlank(contentText)) {
            editText.setError(getString(R.string.add_paper_field_not_found_message));
            editText.setText(getString(R.string.add_paper_field_not_found_message));
        } else {
            editText.setText(contentText);
        }
    }
}
