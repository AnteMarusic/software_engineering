package org.polimi.server.model;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Serializable {
    private int row;
    private int col;

    public Coordinates(int row, int col) {
        this.col = col;
        this.row = row;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
