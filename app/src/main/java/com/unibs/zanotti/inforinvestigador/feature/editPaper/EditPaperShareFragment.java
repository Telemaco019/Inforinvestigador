package com.unibs.zanotti.inforinvestigador.feature.editPaper;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class EditPaperShareFragment extends BaseFragment<EditPaperShareContract.View, EditPaperShareContract.Presenter>
        implements EditPaperShareContract.View {

    private static final String FRAGMENT_STRING_ARGUMENT_PAPER_ID = "EditPaperShareFragment.argument.parcelable.Paper";

    @BindView(R.id.undetermined_progress_bar)
    FrameLayout progressBar;
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
    @BindView(R.id.edit_paper_sharing_comment_edit_text)
    EditText sharingUserCommentEditText;
    @BindView(R.id.edit_paper_share_save_button)
    MaterialButton saveButton;

    @BindView(R.id.content_layout)
    LinearLayout contentLayout;

    public EditPaperShareFragment() {
        // Required empty public constructor
    }

    public static EditPaperShareFragment newInstance(String paperId) {
        Bundle arguments = new Bundle();
        arguments.putString(FRAGMENT_STRING_ARGUMENT_PAPER_ID, paperId);
        EditPaperShareFragment fragment = new EditPaperShareFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_paper_share, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected EditPaperShareContract.Presenter createPresenter() {
        return new EditPaperSharePresenter(Injection.providePaperRepository(), getArguments().getString(FRAGMENT_STRING_ARGUMENT_PAPER_ID));
    }

    @Override
    public void showPaper(Paper paper) {
        if (contentLayout.getVisibility() != View.VISIBLE) {
            ActivityUtils.animateViewWithFade(contentLayout, View.VISIBLE, 1f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
        }

        setText(paperTitleEditText, paper.getPaperTitle());
        setText(paperAuthorsEditText, paper.getPaperAuthors().isEmpty() ? StringUtils.BLANK : String.join(", ", paper.getPaperAuthors()));
        setText(paperDateEditText, paper.getPaperDate());
        setText(paperPublisherEditText, paper.getPaperPublisher());
        setText(paperCitationsEditText, paper.getPaperCitations() == null ? StringUtils.BLANK : String.valueOf(paper.getPaperCitations()));
        setText(paperAbstractEditText, paper.getPaperAbstract());

        sharingUserCommentEditText.setText(paper.getSharingUserComment());
    }

    @Override
    public void hideProgressBar() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            ActivityUtils.animateViewWithFade(progressBar, View.GONE, 0f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
        }
    }

    @Override
    public void showProgressBar() {
        if (progressBar.getVisibility() != View.VISIBLE) {
            ActivityUtils.animateViewWithFade(progressBar, View.VISIBLE, 1f, ActivityUtils.FADE_ANIMATION_STANDARD_DURATION_MS);
        }
    }

    @Override
    public void showSavePaperError() {
        Snackbar.make(saveButton, R.string.error_saving_paper, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void finishActivityWithResultOk() {
        if (getActivity() != null) {
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }
    }

    @OnClick(R.id.edit_paper_share_save_button)
    public void onSaveButtonClicked() {
        if (validSharingUserComment()) {
            presenter.saveButtonClicked(sharingUserCommentEditText.getText().toString());
        }
    }

    private boolean validSharingUserComment() {
        if (StringUtils.isBlank(sharingUserCommentEditText.getText().toString())) {
            sharingUserCommentEditText.setError(getString(R.string.field_required_error_msg));
            return false;
        }
        return true;
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
