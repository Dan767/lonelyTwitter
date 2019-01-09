package ca.ualberta.cs.lonelytwitter.models;

import java.util.Date;

public abstract class Mood {
    private Date date;

    Mood() {
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
