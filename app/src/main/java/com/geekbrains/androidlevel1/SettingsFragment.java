package com.geekbrains.androidlevel1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geekbrains.androidlevel1.FragmentTypes.FragmentType;

// Фрагмент, содержащий экран настроек программы.
public class SettingsFragment extends Fragment {
    private final FragmentType fragmentType = FragmentType.SETTINGS;

    public static SettingsFragment newInstance() {
        SettingsFragment f = new SettingsFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    public FragmentType getFragmentType() {
        return fragmentType;
    }
}