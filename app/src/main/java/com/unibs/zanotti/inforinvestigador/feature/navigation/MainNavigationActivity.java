package com.unibs.zanotti.inforinvestigador.feature.navigation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.baseMVP.BaseActivity;

import java.util.HashMap;
import java.util.Map;


public class MainNavigationActivity extends BaseActivity<NavigationContract.View, NavigationContract.Presenter>
        implements NavigationContract.View {

    public static final String TAG_FRAGMENT_HOMEFEED = "homefeed";
    public static final String TAG_FRAGMENT_PROFILE = "profile";
    public static final String TAG_FRAGMENT_LIBRARY = "library";
    public static final String TAG_FRAGMENT_ADD_PAPER = "add";

    /**
     * Map the tag fragments to the ids of the respective bottom nav items
     */
    private Map<String, Integer> fragmentTagToItemId = new HashMap<>();

    private BottomNavigationView bottomNavigationBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Initialize map
        fragmentTagToItemId.put(TAG_FRAGMENT_HOMEFEED, R.id.bottom_bar_action_home);
        fragmentTagToItemId.put(TAG_FRAGMENT_PROFILE, R.id.bottom_bar_action_profile);
        fragmentTagToItemId.put(TAG_FRAGMENT_LIBRARY, R.id.bottom_bar_action_library);

        // Set the toolbar layout as toolbar
        Toolbar toolbar = findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);
        // Set the title of the support action bar
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.app_name);
        // Set the font of the support action bar
        TextView textView = (TextView) toolbar.getChildAt(0);
        textView.setTypeface(getResources().getFont(R.font.montserrat_light));

        // Add listener to bottom navigation bar
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setOnNavigationItemSelectedListener(e -> presenter.onBottomNavItemSelected(e.getItemId()));
        /*bottomNavigationBar.setOnNavigationItemReselectedListener(e -> {
            // TODO: scroll to top
        });*/

        // Initialize bottom nav by deselecting all the menu items
        for (int i = 0; i < bottomNavigationBar.getMenu().size(); i++) {
            bottomNavigationBar.getMenu().getItem(i).setChecked(false);
        }
    }

    @Override
    protected NavigationContract.Presenter createPresenter() {
        return new NavigationPresenter();
    }


    @Override
    public void selectBottomNavigationItem(String fragmentTag) {
        Integer itemId = fragmentTagToItemId.get(fragmentTag);
        if(itemId != null) {
            bottomNavigationBar.setSelectedItemId(itemId);
        }
    }

    @Override
    public void hideFragment(String fragmentTag) {
        getSupportFragmentManager().executePendingTransactions();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
        if(fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(fragment)
                    .commit();
        }
    }

    @Override
    public void showAddedFragment(String fragmentTag) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.executePendingTransactions();
        Fragment fragment = supportFragmentManager.findFragmentByTag(fragmentTag);
        if(fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .show(fragment)
                    .commit();
        }
    }

    @Override
    public void attachFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().executePendingTransactions();
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_nav_fragment_holder, fragment, tag)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Put the item of the menu in the toolbar. Material Design is applied automatically
        inflater.inflate(R.menu.menu_top_bar_main_navigation, menu);

        return true;
    }
}
