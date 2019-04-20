package com.unibs.zanotti.inforinvestigador.paperdetail;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.unibs.zanotti.inforinvestigador.R;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.ViewHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PaperDetailFragment extends Fragment implements PaperDetailContract.View {
    private static final String ARGUMENT_PAPER_ID = "PAPER_ID";

    private PaperDetailContract.Presenter presenter;
    /**
     * Group adapter for showing nested comments
     */
    private GroupAdapter<ViewHolder> groupAdapter;

    // View elements
    private TextView paperTitle;
    private TextView paperAbstract;
    private TextView paperDate;
    private TextView paperAuthors;
    private TextView paperTopics;
    private TextView paperCitations;
    private ImageView paperImage;
    private TextView paperDOI;
    private TextView paperPublisher;

    public PaperDetailFragment() {
        // Required empty public constructor
    }

    public static PaperDetailFragment newInstance(String paperId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_PAPER_ID, paperId);
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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paper_detail, container, false);

        paperAbstract = view.findViewById(R.id.paper_abstract);
        paperTitle = view.findViewById(R.id.paper_title);
        paperDate = view.findViewById(R.id.paper_date);
        paperImage = view.findViewById(R.id.paper_image);
        paperDOI = view.findViewById(R.id.paper_doi);
        paperAuthors = view.findViewById(R.id.paper_authors);
        paperTopics = view.findViewById(R.id.paper_topics);
        paperCitations = view.findViewById(R.id.paper_citations);
        paperPublisher = view.findViewById(R.id.paper_publisher);

        RecyclerView rvPaperComments = view.findViewById(R.id.rv_paper_comments);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),groupAdapter.getSpanCount());
        layoutManager.setSpanCount(groupAdapter.getSpanCount());
        rvPaperComments.setAdapter(groupAdapter);
        rvPaperComments.setLayoutManager(layoutManager);

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
        String authorsToString = String.join(",", authors);
        this.paperAuthors.setText(authorsToString);
    }

    @Override
    public void showPaperPublisher(String publisher) {
        this.paperPublisher.setText(publisher);
    }

    @Override
    public void showPaperCitations(String citations) {
        this.paperCitations.setText(citations);
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
}
