package com.unibs.zanotti.inforinvestigador.navigation;

import android.os.Bundle;
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
import com.unibs.zanotti.inforinvestigador.utils.Injection;


public class MainNavigationActivity extends AppCompatActivity {
    private static final String SAVED_STATE_CURRENT_FRAGMENT_KEY = "CurrentTabKey";
    private static final String TAG_FRAGMENT_HOMEFEED = "fragment_homefeed";
    private static final String TAG_FRAGMENT_PROFILE = "fragment_profile";
    private static final String TAG_FRAGMENT_LIBRARY = "fragment_library";

    /**
     * Map the ids fragments to the ids of the respective bottom nav items
     */
    private SparseIntArray fragmentIdToItemId = new SparseIntArray();
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
        switch (itemId) {
            case R.id.bottom_bar_action_home: {
                currentFragmentKey = R.id.fragment_homefeed;
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_HOMEFEED);
                if (fragment == null) {
                    fragment = HomefeedFragment.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_nav_fragment_holder, fragment, TAG_FRAGMENT_HOMEFEED)
                            .commit();
                }
                break;
            }
            case R.id.bottom_bar_action_profile: {
                currentFragmentKey = R.id.fragment_profile;
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_PROFILE);
                if (fragment == null) {
                    fragment = ProfileFragment.newInstance(Injection.provideUserRepository().getCurrentUserId());
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_nav_fragment_holder, fragment, TAG_FRAGMENT_PROFILE)
                            .commit();
                }
                break;
            }
            case R.id.bottom_bar_action_library: {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_LIBRARY);
                if (fragment == null) {
                    fragment = LibraryFragment.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_nav_fragment_holder, fragment, TAG_FRAGMENT_LIBRARY)
                            .commit();
                }
                currentFragmentKey = R.id.fragment_library;
                break;
            }
        }

        return true;
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
