package org.polimi;

import org.jetbrains.annotations.NotNull;

public class Bookshelf {
    private static final int COL = 5;
    private static final int ROW = 6;
    final private Card[][] grid;
    final private int[] index; //index to insert
    private int maxInsertable;

    public Bookshelf() {
        this.grid = new Card[ROW][COL];
        this.maxInsertable = 3;
        this.index = new int[COL];
        for (int i = 0; i < COL; i ++) {
            this.index[i] = 0;
        }
    }


    public Card[][] getGrid () {
        Card[][] tempGrid = new Card[ROW][COL];
        for (int i = 0; i < ROW; i ++) {
            System.arraycopy(this.grid[i], 0, tempGrid[i], 0, COL);
        }
        return tempGrid;
    }

    public int getMaxInsertable() {
        return this.maxInsertable;
    }

    /*
     * @requires 0 <= col <= COL - 1
     */
    public int getInsertable (int col) {
        return ROW - this.index[col];
    }

    private void updateMaxInsertable () {
        int max = 0;
        for (int i = 0; i < COL; i ++) {
            if (getInsertable(i) > max) {
                max = getInsertable(i);
            }
        }
        this.maxInsertable = max;
    }



    /*
     * @requires 0 <= col <= COL - 1 && b != null && getMaxInsertable >= b.length && getInsertable (col) >= b.length
     *
     */
    public void insert(@NotNull Card[] cards, int col) {
        int j = 0;
        for (int i = index[col]; i < index[col] + cards.length; i ++) {
            this.grid[i][col] = cards[j];
            j ++;
        }
        this.index[col] = index[col] + cards.length;
        updateMaxInsertable();
    }

    public void print() {
        char c;
        for (int i = ROW - 1; i >= 0; i --) {
            for (int j = 0; j < COL; j ++) {
                if (this.grid[i][j] == null) {
                    c = 'N';
                }
                else {
                    c = convertColorToChar(this.grid[i][j].getColor());
                }
                System.out.print(c);
            }
            System.out.println(" ");
        }
    }

    private char convertColorToChar (Card.Color c) {
        if (c.equals(Card.Color.GREEN)) {
            return 'G';
        }
        if (c.equals(Card.Color.CYAN)) {
            return 'C';
        }
        if (c.equals(Card.Color.BLUE)) {
            return 'B';
        }
        if (c.equals(Card.Color.WHITE)) {
            return 'W';
        }if (c.equals(Card.Color.ORANGE)) {
            return 'O';
        }if (c.equals(Card.Color.PINK)) {
            return 'P';
        }
        return '?';
    }
}
