package org.polimi;

public class Coordinates {
    private int row;
    private int col;

    public Coordinates(int row, int col) {
        setRow(row);
        setCol(col);
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public void setRowCol(int row, int col){
            setRow(row);
            setCol(col);
    }

    public void setRow(int i){
        this.row = i;
    }

    public void setCol(int j){
        this.col = j;
    }
    public boolean indexIsValid(int k){
        return k >= 0 && k <= 8;
    }
    public boolean CoordinatesAreValid(){
        return indexIsValid(row)&& indexIsValid(col);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
