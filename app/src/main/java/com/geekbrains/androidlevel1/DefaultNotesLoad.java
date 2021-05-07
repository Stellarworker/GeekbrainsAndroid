package com.geekbrains.androidlevel1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Этот класс позволяет вносить в программу заметки "по умолчанию".
// Сохранение заметок не реализовано.
public class DefaultNotesLoad implements NotesSaveLoadBehavior {
    private Note[] notes;
    private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy";

    DefaultNotesLoad() {
        addDefaultNotes();
    }

    private void addDefaultNotes() {
        notes = new Note[2];
        notes[0] = new Note("Список дел", "Основное на сегодня",
                "Покрасить забор зелёной краской", stringToDate(DATE_FORMAT_PATTERN, "07.05.2020"));
        notes[1] = new Note("Подарок другу", "Что-то для компьютера",
                "Новый SSD или комплект RAM DDR4", stringToDate(DATE_FORMAT_PATTERN, "05.09.2020"));
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

    // Метод позволяет переводить дату из строкового типа данных в тип данных Date по определённому паттерну.
    private Date stringToDate(String dateFormatPattern, String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
        try {
            return dateFormat.parse(stringDate);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
