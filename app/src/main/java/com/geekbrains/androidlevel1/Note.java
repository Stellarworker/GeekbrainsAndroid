package com.geekbrains.androidlevel1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Note implements Parcelable {
    private String title;
    private String description;
    private String text;
    private Date date;

    public Note(String title, String description, String text) {
        this.title = title;
        this.description = description;
        this.text = text;
        date = new Date();
    }

    public Note(String title, String description, String text, Date date) {
        this(title, description, text);
        this.date = date;
    }

    protected Note(Parcel in) {
        title = in.readString();
        description = in.readString();
        text = in.readString();
        date = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getTitle());
        dest.writeString(getDescription());
        dest.writeString(getText());
        dest.writeLong(getDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
