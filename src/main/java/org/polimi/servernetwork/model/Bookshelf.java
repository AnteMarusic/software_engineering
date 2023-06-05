package org.polimi.servernetwork.model;

import org.jetbrains.annotations.NotNull;

import java.util.List;

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
            this.index[i] = ROW - 1;
        }
    }

    public int getMaxInsertable() {
        return maxInsertable;
    }

    /**
     * @return Card[][] that is a copy of the bookshelf
     */

    public Card[][] getGrid () {
        return grid;
    }

    /*
     * @requires 0 <= col <= COL - 1
     * @return the number of cards insertable in col
     */
    public int getInsertable (int col) {
        return this.index[col] + 1;
    }
    public boolean checkIfFull(){
        return this.maxInsertable == 0;
    }

    private void updateMaxInsertable () {
        int max = 0;
        int insertable;
        for (int i = 0; i < COL; i ++) {
            insertable = getInsertable(i);
            if (insertable > 3) {
                insertable = 3;
            }
            if (insertable > max) {
                max = insertable;
            }
        }
        this.maxInsertable = max;
    }



    /*
     * @requires 0 <= col <= COL - 1 && b != null && getMaxInsertable >= b.length
     * && getInsertable (col) >= b.length
     */
    public void insert(@NotNull List<Card> cards, int col) {
        int j = 0;
        if (col < 0 || col > 4) {
            throw new IndexOutOfBoundsException("column is not in bound");
        }
        if (cards.size() > 3 || cards.isEmpty()) {
            throw new IllegalArgumentException("the list of card provided is too long or too short");
        }
        if (cards.size() > maxInsertable || getInsertable(col) < cards.size()) {
            throw new IndexOutOfBoundsException("you tried to insert to many cards");
        }
        for (int i = index[col]; i > index[col] - cards.size(); i --) {
            this.grid[i][col] = cards.get(j);
            j ++;
        }
        this.index[col] = index[col] - cards.size();
        updateMaxInsertable();
    }

    public void print() {

        for (int i = 0; i < ROW; i ++) {
            for (int j = 0; j < COL; j ++) {
                if (this.grid[i][j] == null) {
                    System.out.print("N");
                }
                else {
                    System.out.print(this.grid[i][j].convertColorToChar());
                }
            }
            System.out.println(" ");
        }
    }
}
