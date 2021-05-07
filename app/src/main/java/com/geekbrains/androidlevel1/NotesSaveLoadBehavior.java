package com.geekbrains.androidlevel1;

// Этот интерфейс используется для сохранения и загрузки заметок.
public interface NotesSaveLoadBehavior {
    void saveNote(Note note);

    void saveNotes(Note[] notes);

    Note[] getNotes();
}
