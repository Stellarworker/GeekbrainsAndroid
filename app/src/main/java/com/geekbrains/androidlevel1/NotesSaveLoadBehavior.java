package com.geekbrains.androidlevel1;

import java.util.List;

// Этот интерфейс используется для сохранения и загрузки заметок.
public interface NotesSaveLoadBehavior {
    void saveNote(Note note);

    void saveNotes(List<Note> notes);

    List<Note> getNotes();
}
