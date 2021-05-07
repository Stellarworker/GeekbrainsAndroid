package com.geekbrains.androidlevel1;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.geekbrains.androidlevel1.FragmentTypes.FragmentType;


import com.geekbrains.androidlevel1.event_bus.EventBus;

// Этот фрагмент позволяет просматривать список заметок.
public class NoteInfoListFragment extends Fragment {
    private final FragmentType fragmentType = FragmentType.NOTE_INFO_LIST;
    private boolean isLandscape;
    private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy";
    private Note[] notes;

    public static NoteInfoListFragment newInstance() {
        NoteInfoListFragment f = new NoteInfoListFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_info_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadNotes();
        refreshNoteList(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getBus().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getBus().unregister(this);
        super.onStop();
    }

    private void loadNotes() {
        NotesSaveLoadBehavior notesObject = new DefaultNotesLoad();
        notes = notesObject.getNotes();
    }

    private void refreshNoteList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        for (int i = 0; i < notes.length; i++) {
            CardView noteContainer = new CardView(getContext());
            LinearLayout noteLayout = new LinearLayout(getContext());
            noteLayout.setOrientation(LinearLayout.VERTICAL);
            TextView noteTitle = new TextView(getContext());
            noteTitle.setTextSize(getResources().getDimension(R.dimen.noteTitleTextSize));
            noteTitle.setText(notes[i].getTitle());
            TextView noteDescription = new TextView(getContext());
            noteDescription.setTextSize(getResources().getDimension(R.dimen.noteInfoTextSize));
            noteDescription.setText(notes[i].getDescription());
            TextView noteDate = new TextView(getContext());
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
            noteDate.setTextSize(getResources().getDimension(R.dimen.noteInfoTextSize));
            noteDate.setText(dateFormat.format(notes[i].getDate()));
            noteLayout.addView(noteTitle);
            noteLayout.addView(noteDescription);
            noteLayout.addView(noteDate);
            noteContainer.addView(noteLayout);
            layoutView.addView(noteContainer);
            final int fi = i;
            noteContainer.setOnClickListener((v) -> {
                OpenNoteBehavior activity = (OpenNoteBehavior) getActivity();
                activity.openNote(notes[fi]);
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    public FragmentType getFragmentType() {
        return fragmentType;
    }

    public Note[] getNotes() {
        return notes;
    }

}
