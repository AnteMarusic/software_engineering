package org.polimi;

public class Coordinates {
    private int x;//#row
    private int y;//#column

    public Coordinates(int i, int j) {
        setX(i);
        setY(j);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setXY(int i, int j){
            setX(i);
            setY(j);
    }

    public void setX(int i){
        this.x = i;
    }

    public void setY(int j){
        this.y = j;
    }
    public boolean IndexisValid(int k){
        return k >= 0 && k <= 8;
    }
    public boolean CoordsAreValid(){
        return IndexisValid(x)&&IndexisValid(y);
    }

}
