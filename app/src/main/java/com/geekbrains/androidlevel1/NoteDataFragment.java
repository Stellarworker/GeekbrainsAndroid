package com.geekbrains.androidlevel1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geekbrains.androidlevel1.FragmentTypes.FragmentType;

// Этот фрагмент позволяет просматривать заметки.
public class NoteDataFragment extends Fragment {
    private final FragmentType fragmentType = FragmentType.NOTE_DATA;
    public static final String ARG_NOTE = "note";
    private Note note;

    public static NoteDataFragment newInstance(Note note) {
        NoteDataFragment f = new NoteDataFragment();
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
        View view = inflater.inflate(R.layout.fragment_note_data, container, false);
        TextView noteDataText = view.findViewById(R.id.noteDataText);
        if (note != null) {
            TextView noteDataScreenTitle = view.findViewById(R.id.noteDataScreenTitle);
            noteDataScreenTitle.setText(note.getTitle());
            noteDataText.setText(note.getText());
        }
        return view;
    }

    public FragmentType getFragmentType() {
        return fragmentType;
    }
}
