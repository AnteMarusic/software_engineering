package org.polimi.server.model;

public class BookshelfCoordinates extends AbstractCoordinates {
    private static final int COL = 5;
    private static final int ROW = 6;

    public BookshelfCoordinates(int row, int col) {
        super(row, col);
    }

    @Override
    public boolean isValid() {
        if (this.getRow() >= ROW || this.getRow() < 0) return false;
        else return this.getCol() < COL && this.getCol() >= 0;
    }
}
