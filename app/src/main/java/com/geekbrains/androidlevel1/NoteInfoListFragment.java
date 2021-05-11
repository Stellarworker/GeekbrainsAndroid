package com.geekbrains.androidlevel1;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import com.geekbrains.androidlevel1.FragmentTypes.FragmentType;


import com.geekbrains.androidlevel1.event_bus.EventBus;
import com.geekbrains.androidlevel1.model.NoteCardSourceImplementation;

// Этот фрагмент позволяет просматривать список заметок.
public class NoteInfoListFragment extends Fragment {
    private final FragmentType fragmentType = FragmentType.NOTE_INFO_LIST;
    private boolean isLandscape;
    private Note[] notes;
    private NotesAdapter adapter;

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
        initRecyclerView(view);
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

    private void initRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewLines);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new NotesAdapter(new NoteCardSourceImplementation().getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        adapter.SetOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                OpenNoteBehavior activity = (OpenNoteBehavior) getActivity();
                activity.openNote(notes[position]);
            }
        });
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
