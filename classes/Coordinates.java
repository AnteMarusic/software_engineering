package org.example;

public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int i, int j) {
        setX(i);
        setY(j);
    }

    public Coordinates() {
        this.x = 0;
        this.y = 0;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setXY(int i, int j){
        this.x = i;
        this.y = j;
    }

    public void setX(int i){
        this.x = i;
    }

    public void setY(int j){
        this.y = j;
    }
}