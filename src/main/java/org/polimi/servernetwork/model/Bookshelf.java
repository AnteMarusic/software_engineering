package org.polimi.servernetwork.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class Bookshelf implements Serializable {
    private static final int COL = 5;
    private static final int ROW = 6;
    /**
     * data structure representing bookshelf
     */
    final private Card[][] grid;
 // da qui va tolto
    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_PINK = "\u001B[35m"; //PINK
    public static final String ANSI_GREEN = "\u001B[32m"; //GREEN
    public static final String ANSI_WHITE = "\u001B[37m"; //WHITE
    public static final String ANSI_ORANGE = "\u001B[31m"; //ORANGE
    public static final String ANSI_CYAN = "\u001B[36m"; //CYAN
    public static final String ANSI_BLUE = "\u001B[34m"; //BLUE
    public void printbookshelf() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j ++) {
                if (this.grid[i][j] == null) {
                    System.out.print("N ");
                }
                else {
                    switch(this.grid[i][j].getColor()) {
                        case WHITE -> {
                            System.out.print(ANSI_WHITE+ "□ "+ANSI_RESET);
                        }
                        case BLUE -> {
                            System.out.print(ANSI_BLUE+ "□ "+ ANSI_RESET);
                        }
                        case ORANGE -> {
                            System.out.print(ANSI_ORANGE+"□ "+ANSI_RESET);
                        }
                        case PINK -> {
                            System.out.print(ANSI_PINK+"□ "+ANSI_RESET);
                        }
                        case CYAN -> {
                            System.out.print(ANSI_CYAN+"□ "+ANSI_RESET);
                        }
                        case GREEN -> {
                            System.out.print(ANSI_GREEN+"□ "+ANSI_RESET);
                        }
                    }
                }
            }
            System.out.println(" ");
        }
    }

    // fino a qui

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

    /**
     *
     * @return the number of cards insertable in col
     */
    public int getInsertable (int col) {
        return this.index[col] + 1;
    }
    public boolean checkIfFull(){
        return this.maxInsertable == 0;
    }

    /**
     * Updates the maximum number of cards that can be inserted into any column in the game board.
     * It iterates over each column and calculates the number of insertable cards using the getInsertable() method.
     * The maximum insertable value is capped at 3, following the game rules.
     * Finally, it sets the maximum insertable value as the new value for maxInsertable.
     */
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



    /**
     * Inserts a list of cards into the specified column of the game board.
     *
     * @param cards The list of cards to be inserted.
     * @param col   The column index where the cards should be inserted.
     * @throws IndexOutOfBoundsException if the column index is out of bounds.
     * @throws IllegalArgumentException  if the list of cards is too long or too short.
     * @throws IndexOutOfBoundsException if attempting to insert too many cards or there are insufficient insertable slots.
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
