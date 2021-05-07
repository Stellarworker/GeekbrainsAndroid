package com.geekbrains.androidlevel1;

// Этот интерфейс позволяет фрагменту со списком заметок открывать нужную заметку
// в подходящем фрагменте. Активити должна его имплементировать.
public interface OpenNoteBehavior {
    void openNote(Note note);
}
