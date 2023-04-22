package org.polimi.server.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class Bookshelf {
    private static final int COL = 5;
    private static final int ROW = 6;
    /**
     * data structure representing bookshelf
     */
    final private HashMap<BookshelfCoordinates, Card> grid;
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
        this.grid = new HashMap<>(COL*ROW);
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
            for (int j = 0; j < COL; j ++) {
                tempGrid[i][j] = this.grid.get(new BookshelfCoordinates(i, j));
            }
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
            this.grid.put(new BookshelfCoordinates(i , col), cards.get(j));
            j ++;
        }
        this.index[col] = index[col] + cards.size();
        updateMaxInsertable();

    }

    public void print() {

        for (int i = ROW - 1; i >= 0; i --) {
            for (int j = 0; j < COL; j ++) {
                if (this.grid.get(new BookshelfCoordinates(i, j)) == null) {
                    System.out.print("N");
                }
                else {
                    System.out.print(this.grid.get(new BookshelfCoordinates(i, j)).convertColorToChar());
                }
            }
            System.out.println(" ");
        }
    }

    public boolean CheckIfFull(){
        return this.maxInsertable == 0;
    }
}
