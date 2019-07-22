package com.unibs.zanotti.inforinvestigador.feature.navigation;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.unibs.zanotti.inforinvestigador.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainNavigationErrorFragment extends Fragment {


    public MainNavigationErrorFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new MainNavigationErrorFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_error, container, false);
    }

}
