package com.geekbrains.androidlevel1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements OpenNoteBehavior {

    private FragmentTypes.FragmentType mainContainer = null;
    private FragmentTypes.FragmentType container1 = null;
    private FragmentTypes.FragmentType container2 = null;
    private Note currentNote = null;
    private final String MAIN_CONTAINER_KEY = "MAIN_CONTAINER_KEY";
    private final String CONTAINER1_KEY = "CONTAINER1_KEY";
    private final String CONTAINER2_KEY = "CONTAINER2_KEY";
    private final String CURRENT_NOTE_KEY = "CURRENT_NOTE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (isOrientationLandscape()) {
            useLandscapeOrientation(savedInstanceState);
        } else {
            usePortraitOrientation(savedInstanceState);
        }
    }

    private void initView() {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
    }

    // Регистрация drawer.
    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Обработка навигационного меню.
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (navigateFragment(id)) {
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пункта меню приложения (активити).
        int id = item.getItemId();
        if (navigateFragment(id)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    private boolean navigateFragment(int id) {
        int containerID;
        switch (id) {
            case R.id.action_settings:
                containerID = (isOrientationLandscape()) ? R.id.fragmentContainer2 : R.id.mainFragmentContainer;
                SettingsFragment settingsFragment = SettingsFragment.newInstance();
                addFragmentToContainer(settingsFragment, settingsFragment.getFragmentType(), containerID);
                return true;
            case R.id.action_note_list:
                containerID = (isOrientationLandscape()) ? R.id.fragmentContainer1 : R.id.mainFragmentContainer;
                NoteInfoListFragment noteInfoListFragment = NoteInfoListFragment.newInstance();
                addFragmentToContainer(noteInfoListFragment, noteInfoListFragment.getFragmentType(), containerID);
                return true;
            case R.id.action_about:
                containerID = (isOrientationLandscape()) ? R.id.fragmentContainer2 : R.id.mainFragmentContainer;
                AboutFragment aboutFragment = AboutFragment.newInstance();
                addFragmentToContainer(aboutFragment, aboutFragment.getFragmentType(), containerID);
                return true;
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!isOrientationLandscape()) {
            switch (container2) {
                case NOTE_DUMMY:
                    mainContainer = FragmentTypes.FragmentType.NOTE_INFO_LIST;
                default:
                    mainContainer = container2;
                    break;
            }
            outState.putString(MAIN_CONTAINER_KEY, mainContainer.toString());
        } else {
            switch (mainContainer) {
                case NOTE_INFO_LIST:
                    container2 = FragmentTypes.FragmentType.NOTE_DUMMY;
                default:
                    container2 = mainContainer;
                    break;
            }
            outState.putString(CONTAINER2_KEY, container2.toString());
        }
        if (currentNote != null) {
            outState.putParcelable(CURRENT_NOTE_KEY, currentNote);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void usePortraitOrientation(Bundle bundle) {
        if (bundle != null) {
            FragmentTypes.FragmentType fragmentType = FragmentTypes.FragmentType.valueOf(bundle.getString(MAIN_CONTAINER_KEY));
            switch (fragmentType) {
                case NOTE_DATA:
                    currentNote = bundle.getParcelable(CURRENT_NOTE_KEY);
                    openNote(currentNote);
                    break;
                case SETTINGS:
                    SettingsFragment settings = SettingsFragment.newInstance();
                    addFragmentToContainer(settings, settings.getFragmentType(), R.id.mainFragmentContainer);
                    break;
                case ABOUT:
                    AboutFragment about = AboutFragment.newInstance();
                    addFragmentToContainer(about, about.getFragmentType(), R.id.mainFragmentContainer);
                    break;
                default:
                    NoteInfoListFragment list = NoteInfoListFragment.newInstance();
                    addFragmentToContainer(list, list.getFragmentType(), R.id.mainFragmentContainer);
            }
            return;
        }
        NoteInfoListFragment list = NoteInfoListFragment.newInstance();
        addFragmentToContainer(list, list.getFragmentType(), R.id.mainFragmentContainer);
    }

    private void useLandscapeOrientation(Bundle bundle) {
        NoteInfoListFragment list = NoteInfoListFragment.newInstance();
        addFragmentToContainer(list, list.getFragmentType(), R.id.fragmentContainer1);
        NoteDummyFragment dummy = NoteDummyFragment.newInstance();
        addFragmentToContainer(dummy, dummy.getFragmentType(), R.id.fragmentContainer2);
        if (bundle != null) {
            FragmentTypes.FragmentType fragmentType = FragmentTypes.FragmentType.valueOf(bundle.getString(CONTAINER2_KEY));
            switch (fragmentType) {
                case NOTE_DATA:
                    currentNote = bundle.getParcelable(CURRENT_NOTE_KEY);
                    openNote(currentNote);
                    break;
                case SETTINGS:
                    SettingsFragment settings = SettingsFragment.newInstance();
                    addFragmentToContainer(settings, settings.getFragmentType(), R.id.fragmentContainer2);
                    break;
                case ABOUT:
                    AboutFragment about = AboutFragment.newInstance();
                    addFragmentToContainer(about, about.getFragmentType(), R.id.fragmentContainer2);
                    break;
                default:
            }
            return;
        }

    }

    @SuppressLint("NonConstantResourceId")
    public void addFragmentToContainer(Fragment fragment, FragmentTypes.FragmentType fragmentType, int container) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().addToBackStack(fragmentType.toString());
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            ft.replace(container, fragment);
        } else {
            ft.add(container, fragment);
        }
        ft.commit();
        switch (container) {
            case R.id.fragmentContainer1:
                container1 = fragmentType;
                break;
            case R.id.fragmentContainer2:
                container2 = fragmentType;
                break;
            default:
                mainContainer = fragmentType;
                break;
        }
    }

    @Override
    public void onBackPressed() {
        int minBackStackCapacity = (!isOrientationLandscape()) ? 1 : 2;
        if (getSupportFragmentManager().getBackStackEntryCount() > minBackStackCapacity) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void openNote(Note note) {
        int targetContainer = (isOrientationLandscape()) ? R.id.fragmentContainer2 : R.id.mainFragmentContainer;
        NoteDataFragment data = NoteDataFragment.newInstance(note);
        addFragmentToContainer(data, data.getFragmentType(), targetContainer);
        currentNote = note;
    }

    public boolean isOrientationLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}