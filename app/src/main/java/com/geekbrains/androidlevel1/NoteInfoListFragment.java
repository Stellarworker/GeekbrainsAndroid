package com.geekbrains.androidlevel1;

import android.content.Intent;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.geekbrains.androidlevel1.event_bus.EventBus;

public class NoteInfoListFragment extends Fragment {
    private boolean isLandscape;
    public static final String CURRENT_NOTE = "CurrentNote";
    private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy";
    private Note currentNote;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_info_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
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

    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] noteTitles = getResources().getStringArray(R.array.noteTitle);
        String[] noteDescriptions = getResources().getStringArray(R.array.noteDescription);
        String[] noteDates = getResources().getStringArray(R.array.noteDate);
        String[] noteTexts = getResources().getStringArray(R.array.noteText);

        for (int i = 0; i < 2; i++) {
            CardView noteContainer = new CardView(getContext());
            LinearLayout noteLayout = new LinearLayout(getContext());
            noteLayout.setOrientation(LinearLayout.VERTICAL);
            TextView noteTitle = new TextView(getContext());
            noteTitle.setTextSize(20);
            noteTitle.setText(noteTitles[i]);
            TextView noteDescription = new TextView(getContext());
            noteDescription.setText(noteDescriptions[i]);
            TextView noteDate = new TextView(getContext());
            noteDate.setText(noteDates[i]);
            noteLayout.addView(noteTitle);
            noteLayout.addView(noteDescription);
            noteLayout.addView(noteDate);
            noteContainer.addView(noteLayout);
            layoutView.addView(noteContainer);
            final int fi = i;
            final Date finalNoteDate = stringToDate(DATE_FORMAT_PATTERN, noteDates[i]);
            noteContainer.setOnClickListener((v) -> {
                currentNote = new Note(noteTitles[fi], noteDescriptions[fi], noteTexts[fi], finalNoteDate);
                openNote(currentNote);
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = new Note(getResources().getStringArray(R.array.noteTitle)[0],
                    getResources().getStringArray(R.array.noteDescription)[0],
                    getResources().getStringArray(R.array.noteText)[0],
                    stringToDate(DATE_FORMAT_PATTERN, getResources().getStringArray(R.array.noteDate)[0]));
        }
        if (isLandscape) {
            openLandNote(currentNote);
        }
    }

    private void openNote(Note currentNote) {
        if (isLandscape) {
            openLandNote(currentNote);
        } else {
            openPortNote(currentNote);
        }
    }

    private void openLandNote(Note currentNote) {
        NoteDataFragment detail = NoteDataFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentNoteData, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void openPortNote(Note currentNote) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteDataActivity.class);
        intent.putExtra(NoteDataFragment.ARG_NOTE, currentNote);
        startActivity(intent);
    }

    private Date stringToDate(String dateFormatPattern, String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
        try {
            return dateFormat.parse(stringDate);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
