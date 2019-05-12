package com.unibs.zanotti.inforinvestigador.navigation;

import android.os.Bundle;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unibs.zanotti.inforinvestigador.LibraryFragment;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.homefeed.HomefeedFragment;
import com.unibs.zanotti.inforinvestigador.profile.ProfileFragment;
import com.unibs.zanotti.inforinvestigador.utils.ActivityUtils;
import com.unibs.zanotti.inforinvestigador.utils.Injection;


public class MainNavigationActivity extends AppCompatActivity {
    private static final String SAVED_STATE_CURRENT_FRAGMENT_KEY = "CurrentTabKey";

    /**
     * Map the ids fragments to the ids of the respective bottom nav items
     */
    private SparseIntArray fragmentIdToItemId = new SparseIntArray();
    private SparseArray<Fragment.SavedState> savedStateSparseArray = new SparseArray<>();
    private int currentFragmentKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Initialize map
        fragmentIdToItemId.put(R.id.fragment_homefeed, R.id.bottom_bar_action_home);
        fragmentIdToItemId.put(R.id.fragment_profile, R.id.bottom_bar_action_profile);
        fragmentIdToItemId.put(R.id.fragment_library, R.id.bottom_bar_action_library);

        // Set the toolbar layout as toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);
        // Set the title of the support action bar
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.app_name);
        // Set the font of the support action bar
        TextView textView = (TextView) toolbar.getChildAt(0);
        textView.setTypeface(getResources().getFont(R.font.montserrat_light));

        // Add listener to bottom navigation bar
        BottomNavigationView bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setOnNavigationItemSelectedListener(e -> navigate(e.getItemId()));
        bottomNavigationBar.setOnNavigationItemReselectedListener(e -> {
           // TODO: scroll to top
        });

        // Initialize bottom nav by deselecting all the menu items
        for (int i = 0; i < bottomNavigationBar.getMenu().size(); i++) {
            bottomNavigationBar.getMenu().getItem(i).setChecked(false);
        }

        if (savedInstanceState != null) {
            currentFragmentKey = savedInstanceState.getInt(SAVED_STATE_CURRENT_FRAGMENT_KEY);
            bottomNavigationBar.setSelectedItemId(fragmentIdToItemId.get(currentFragmentKey));
        } else {
            // Set default selected tab
            bottomNavigationBar.setSelectedItemId(R.id.bottom_bar_action_home);
        }
    }

    private boolean navigate(int itemId) {
        Fragment destinationFragment = null;

        switch (itemId) {
            case R.id.bottom_bar_action_home: {
                destinationFragment = HomefeedFragment.newInstance();
                currentFragmentKey = R.id.fragment_homefeed;
                break;
            }
            case R.id.bottom_bar_action_profile: {
                destinationFragment = ProfileFragment.newInstance(Injection.provideUserRepository().getCurrentUserId());
                currentFragmentKey = R.id.fragment_profile;
                break;
            }
            case R.id.bottom_bar_action_library: {
                destinationFragment = LibraryFragment.newInstance();
                currentFragmentKey = R.id.fragment_library;
                break;
            }
        }

        if (destinationFragment != null) {
            ActivityUtils.replaceFragment(getSupportFragmentManager(), destinationFragment, R.id.main_nav_fragment_holder);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Put the item of the menu in the toolbar. Material Design is applied automatically
        inflater.inflate(R.menu.menu_top_bar_recommendations, menu);

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_STATE_CURRENT_FRAGMENT_KEY, currentFragmentKey);
    }
}
