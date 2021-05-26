package com.geekbrains.androidlevel1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geekbrains.androidlevel1.FragmentTypes.FragmentType;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

// Этот фрагмент позволяет редактировать заметки.
public class NoteUpdateFragment extends Fragment {
    private final FragmentType fragmentType = FragmentType.NOTE_UPDATE;
    public static final String ARG_NOTE = "note";
    private Note note;

    public static NoteUpdateFragment newInstance(Note note) {
        NoteUpdateFragment f = new NoteUpdateFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_update, container, false);
        if (note != null) {
            TextInputEditText noteUpdateScreenTitle = view.findViewById(R.id.noteUpdateScreenTitle);
            noteUpdateScreenTitle.setText(note.getTitle());
            TextInputEditText noteUpdateScreenDescription = view.findViewById(R.id.noteUpdateScreenDescription);
            noteUpdateScreenDescription.setText(note.getDescription());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(note.getDate());
            DatePicker datePicker = view.findViewById(R.id.inputDate);
            datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
            TimePicker timePicker = view.findViewById(R.id.inputTime);
            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(calendar.get(Calendar.HOUR));
            timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
            TextInputEditText noteUpdateScreenText = view.findViewById(R.id.noteUpdateScreenText);
            noteUpdateScreenText.setText(note.getText());
        }
        return view;
    }

    public FragmentType getFragmentType() {
        return fragmentType;
    }
}
