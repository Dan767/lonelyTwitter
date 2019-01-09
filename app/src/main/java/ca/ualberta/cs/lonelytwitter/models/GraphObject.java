package ca.ualberta.cs.lonelytwitter.models;

public abstract class GraphObject {
    private int x;
    private int y;

    GraphObject() {
        this.x = 0;
        this.y = 0;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;

    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }



}
