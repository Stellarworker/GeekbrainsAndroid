package com.geekbrains.androidlevel1;

// Этот интерфейс позволяет фрагменту со списком заметок открывать нужную заметку
// в режиме редактирования. Активити должна его имплементировать.
public interface UpdateNoteBehavior {
    void updateNote(Note note);
}
