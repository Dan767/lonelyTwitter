package ca.ualberta.cs.lonelytwitter.models;

public class Anger extends Mood {
    private String mood;
    Anger() {
        this.mood = "Angry";
    }

    public String getMood() {
        return mood;
    }
}
