package org.polimi;

abstract class AbstractCoordinates {
    private final int row;
    private final int col;

    public AbstractCoordinates(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    abstract public boolean isValid();
}
