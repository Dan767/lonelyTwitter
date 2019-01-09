package ca.ualberta.cs.lonelytwitter.models;


public class Happy extends Mood {
    private String mood;
    Happy() {
        this.mood = "Happy";
    }

    public String getMood() {
        return mood;
    }
}
