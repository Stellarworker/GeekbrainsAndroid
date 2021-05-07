package com.geekbrains.androidlevel1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geekbrains.androidlevel1.FragmentTypes.FragmentType;

// Это фрагмент-заглушка для альбомной ориентации экрана. Показывается сразу при запуске программы.
public class NoteDummyFragment extends Fragment {
    private final FragmentType fragmentType = FragmentType.NOTE_DUMMY;

    public static NoteDummyFragment newInstance() {
        NoteDummyFragment f = new NoteDummyFragment();
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
        return inflater.inflate(R.layout.fragment_note_dummy, container, false);
    }

    public FragmentType getFragmentType() {
        return fragmentType;
    }

}
