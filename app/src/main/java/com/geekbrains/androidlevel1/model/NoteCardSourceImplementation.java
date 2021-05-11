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
    private final List<NoteCardData> dataSource;
    private final NotesSaveLoadBehavior notesSaveLoad;

    public NoteCardSourceImplementation() {
        dataSource = new ArrayList<>(2);
        notesSaveLoad = new DefaultNotesLoad();
    }

    @Override
    public List<NoteCardData> getData() {
        Note[] notes = notesSaveLoad.getNotes();
        for (int i = 0; i < notes.length; i++) {
            dataSource.add(new NoteCardData(
                    notes[i].getTitle(), notes[i].getDescription(), Settings.dateToString(notes[i].getDate()))
            );
        }
        return dataSource;
    }

}
