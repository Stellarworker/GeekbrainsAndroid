package com.geekbrains.androidlevel1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Этот класс позволяет вносить в программу заметки "по умолчанию".
// Сохранение заметок не реализовано.
public class DefaultNotesLoad implements NotesSaveLoadBehavior {
    private List<Note> notes;

    public DefaultNotesLoad() {
        notes = new ArrayList<Note>();
        addDefaultNotes();
    }

    private void addDefaultNotes() {
        notes.add(new Note("Список дел", "Основное на сегодня",
                "Покрасить забор зелёной краской", Settings.stringToDate("07.05.2020 12:21")));
        notes.add(new Note("Подарок другу", "Что-то для компьютера",
                "Новый SSD или комплект RAM DDR4", Settings.stringToDate("05.09.2020 17:01")));
    }

    @Override
    public void saveNote(Note note) {
        // Сохранение одной заметки - не реализовано.
    }

    @Override
    public void saveNotes(List<Note> notes) {
        // Сохранение множества заметок - не реализовано.
    }

    public List<Note> getNotes() {
        return notes;
    }

}
