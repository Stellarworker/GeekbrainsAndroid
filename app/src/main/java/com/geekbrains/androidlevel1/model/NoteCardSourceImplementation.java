package com.geekbrains.androidlevel1.model;

import android.content.res.Resources;

import com.geekbrains.androidlevel1.DefaultNotesLoad;
import com.geekbrains.androidlevel1.Note;
import com.geekbrains.androidlevel1.NotesSaveLoadBehavior;
import com.geekbrains.androidlevel1.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoteCardSourceImplementation implements NoteCardSource {
    private List<NoteCardData> dataSource;
    private List<Note> notes;
    private final NotesSaveLoadBehavior notesSaveLoad;

    public NoteCardSourceImplementation() {
        dataSource = new ArrayList<>(2);
        notesSaveLoad = new DefaultNotesLoad();
        loadNotes();
    }

    private void loadNotes() {
        notes = notesSaveLoad.getNotes();
        for (Note note : notes) {
            dataSource.add(new NoteCardData(
                    note.getTitle(), note.getDescription(), Settings.dateToString(note.getDate()))
            );
        }
    }

    @Override
    public List<NoteCardData> getData() {
       return dataSource;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void deleteNote (int position) {
        dataSource.remove(position);
        notes.remove(position);
    }

    public void addCardData(NoteCardData card) {
        dataSource.add(card);
    }

    public void addNote(Note note) {
        notes.add(note);
    }
}
