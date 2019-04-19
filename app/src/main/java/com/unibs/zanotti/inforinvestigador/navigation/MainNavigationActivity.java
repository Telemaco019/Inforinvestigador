package com.unibs.zanotti.inforinvestigador.navigation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;

public class MainNavigationActivity extends AppCompatActivity implements MainNavigationContract.View {
    private MainNavigationContract.Presenter navigationPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the toolbar layout as toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);
        // Set the title of the support action bar
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.app_name);
        // Set the font of the support action bar
        TextView textView = (TextView) toolbar.getChildAt(0);
        textView.setTypeface(getResources().getFont(R.font.montserrat_light));

        // Set the presenter of the view
        new MainNavigationPresenter(this);

        // Add listener to bottom navigation bar
        BottomNavigationView bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setOnNavigationItemSelectedListener(e -> navigationPresenter.navigate(e.getItemId()));

        // Set default selected tab
        bottomNavigationBar.setSelectedItemId(R.id.bottom_bar_action_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Put the item of the menu in the toolbar. Material Design is applied automatically
        inflater.inflate(R.menu.menu_top_bar_recommendations, menu);

        return true;
    }

    @Override
    public void setPresenter(MainNavigationContract.Presenter presenter) {
        this.navigationPresenter = presenter;
    }

    @Override
    public void displayFragment(Fragment fragment) {
        ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.main_nav_fragment_holder);
    }
}
