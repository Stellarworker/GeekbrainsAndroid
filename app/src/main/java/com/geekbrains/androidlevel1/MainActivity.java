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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            useLandscapeOrientation();
        } else {
            usePortraitOrientation();
        }
    }

    private void initView() {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
    }

    // регистрация drawer
    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Обработка навигационного меню
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
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();
        if (navigateFragment(id)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean navigateFragment(int id) {
        int orientation = getResources().getConfiguration().orientation;
        int containerID;
        switch (id) {
            case R.id.action_settings:
                containerID = (orientation == Configuration.ORIENTATION_LANDSCAPE) ? R.id.fragmentContainer2 : R.id.mainFragmentContainer;
                SettingsFragment settingsFragment = SettingsFragment.newInstance();
                addFragmentToContainer(settingsFragment, settingsFragment.getFragmentType(), containerID);
                return true;
            case R.id.action_note_list:
                containerID = (orientation == Configuration.ORIENTATION_LANDSCAPE) ? R.id.fragmentContainer1 : R.id.mainFragmentContainer;
                NoteInfoListFragment noteInfoListFragment = NoteInfoListFragment.newInstance();
                addFragmentToContainer(noteInfoListFragment, noteInfoListFragment.getFragmentType(), containerID);
                return true;
            case R.id.action_about:
                containerID = (orientation == Configuration.ORIENTATION_LANDSCAPE) ? R.id.fragmentContainer2 : R.id.mainFragmentContainer;
                AboutFragment aboutFragment = AboutFragment.newInstance();
                addFragmentToContainer(aboutFragment, aboutFragment.getFragmentType(), containerID);
                return true;
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void usePortraitOrientation() {
        NoteInfoListFragment list = NoteInfoListFragment.newInstance();
        addFragmentToContainer(list, list.getFragmentType(), R.id.mainFragmentContainer);
    }

    private void useLandscapeOrientation() {
        NoteInfoListFragment list = NoteInfoListFragment.newInstance();
        addFragmentToContainer(list, list.getFragmentType(), R.id.fragmentContainer1);
        NoteDummyFragment dummy = NoteDummyFragment.newInstance();
        addFragmentToContainer(dummy, dummy.getFragmentType(), R.id.fragmentContainer2);
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
    }

    @Override
    public void onBackPressed() {
        int orientation = getResources().getConfiguration().orientation;
        int minBackStackCapacity = (orientation == Configuration.ORIENTATION_PORTRAIT) ? 1 : 2;
        if (getSupportFragmentManager().getBackStackEntryCount() > minBackStackCapacity) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void openNote(Note note) {
        int orientation = getResources().getConfiguration().orientation;
        int targetContainer = (orientation == Configuration.ORIENTATION_LANDSCAPE) ? R.id.fragmentContainer2 : R.id.mainFragmentContainer;
        NoteDataFragment data = NoteDataFragment.newInstance(note);
        addFragmentToContainer(data, data.getFragmentType(), targetContainer);
    }
}