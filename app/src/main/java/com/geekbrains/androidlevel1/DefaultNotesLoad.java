package com.geekbrains.androidlevel1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Этот класс позволяет вносить в программу заметки "по умолчанию".
// Сохранение заметок не реализовано.
public class DefaultNotesLoad implements NotesSaveLoadBehavior {
    private Note[] notes;

    public DefaultNotesLoad() {
        addDefaultNotes();
    }

    private void addDefaultNotes() {
        notes = new Note[2];
        notes[0] = new Note("Список дел", "Основное на сегодня",
                "Покрасить забор зелёной краской", Settings.stringToDate("07.05.2020 12:21:15"));
        notes[1] = new Note("Подарок другу", "Что-то для компьютера",
                "Новый SSD или комплект RAM DDR4", Settings.stringToDate("05.09.2020 17:01:54"));
    }

    @Override
    public void saveNote(Note note) {
        // Сохранение одной заметки - не реализовано.
    }

    @Override
    public void saveNotes(Note[] notes) {
        // Сохранение множества заметок - не реализовано.
    }

    public Note[] getNotes() {
        return notes;
    }

}
