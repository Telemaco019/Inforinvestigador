package com.unibs.zanotti.inforinvestigador.addPaper;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPaperFragment extends BaseFragment<AddPaperContract.View, AddPaperContract.Presenter>
        implements AddPaperContract.View {

    public AddPaperFragment() {
        // Required empty public constructor
    }

    public static AddPaperFragment newInstance() {
//        Bundle arguments = new Bundle();
//        arguments.putString(FRAGMENT_STRING_ARGUMENT_PAPER_ID, paperId);
        AddPaperFragment fragment = new AddPaperFragment();
//        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_paper, container, false);
    }

    @Override
    protected AddPaperContract.Presenter createPresenter() {
        return new AddPaperPresenter();
    }
}
