package org.polimi;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Bookshelf {
    private static final int COL = 5;
    private static final int ROW = 6;
    /**
     * data structure representing bookshelf
     */
    final private Card[][] grid;
    /**
     * index to insert, tracks how many cards have been inserted for each column
     */
    final private int[] index;
    /**
     * value that can assume values in the interval [0, 3]. It is the maximum number of cards
     * that can be inserted in bookshelf
     */
    private int maxInsertable;

    /**
     * creates an empty bookshelf
     */
    public Bookshelf() {
        this.grid = new Card[ROW][COL];
        this.maxInsertable = 3;
        this.index = new int[COL];
        for (int i = 0; i < COL; i ++) {
            this.index[i] = 0;
        }
    }

    public int getMaxInsertable() {
        return maxInsertable;
    }

    /**
     * @return Card[][] that is a copy of the bookshelf
     */
    public Card[][] getGrid () {
        Card[][] tempGrid = new Card[ROW][COL];
        for (int i = 0; i < ROW; i ++) {
            System.arraycopy(this.grid[i], 0, tempGrid[i], 0, COL);
        }
        return tempGrid;
    }

    /*
     * @requires 0 <= col <= COL - 1
     * @return the number of cards insertable in col
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
     * @requires 0 <= col <= COL - 1 && b != null && getMaxInsertable >= b.length
     * && getInsertable (col) >= b.length
     */
    public void insert(@NotNull ArrayList<Card> cards, int col) {
        int j = 0;
        for (int i = index[col]; i < index[col] + cards.size(); i ++) {
            this.grid[i][col] = cards.get(j);
            j ++;
        }
        this.index[col] = index[col] + cards.size();
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

    public boolean CheckIfFull(){
        return this.maxInsertable == 0;
    }
}
