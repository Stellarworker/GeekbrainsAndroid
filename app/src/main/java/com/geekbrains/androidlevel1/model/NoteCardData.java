package com.geekbrains.androidlevel1.model;

// Класс, представляющий собой карточку заметки для списка заметок.
public class NoteCardData {

    private final String cardTitle;
    private final String cardDescription;
    private final String cardDate;

    public NoteCardData(String cardTitle, String cardDescription, String cardDate) {
        this.cardTitle = cardTitle;
        this.cardDescription = cardDescription;
        this.cardDate = cardDate;
    }

    public String getTitle() {
        return cardTitle;
    }

    public String getDescription() {
        return cardDescription;
    }

    public String getDate() {
        return cardDate;
    }
}
