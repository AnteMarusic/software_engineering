package org.polimi.client;

import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.util.List;

public class ClientBookshelf {
    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_PINK = "\u001B[35m"; //PINK
    public static final String ANSI_GREEN = "\u001B[32m"; //GREEN
    public static final String ANSI_WHITE = "\u001B[37m"; //WHITE
    public static final String ANSI_ORANGE = "\u001B[31m"; //ORANGE
    public static final String ANSI_CYAN = "\u001B[36m"; //CYAN
    public static final String ANSI_BLUE = "\u001B[34m"; //BLUE
    private static final int COL = 5;
    private static final int ROW = 6;
    private Card[][] grid;
    private int[] index;
    private int maxInsertable;

    public ClientBookshelf() {
        this.grid = new Card[ROW][COL];
        this.maxInsertable = 3;
        this.index = new int[COL];
        for (int i = 0; i < COL; i ++) {
            this.index[i] = ROW;
        }
    }

    public ClientBookshelf(Card[][] grid) {
        this.grid = grid;
        this.maxInsertable = 0;
        this.index = new int[COL];
        for (int i = 0; i < COL; i ++) {
            this.index[i]=0;
            for (int j = ROW - 1; j >= 0; j --) {
                if (grid[j][i] == null) {
                    this.index[i]++;
                }
            }
        }
        updateMaxInsertable();
    }

    public void setGrid(Card[][] grid) {
        this.grid = grid;
    }

    public void setIndex(int[] index) {
        this.index = index;
    }

    public void setMaxInsertable (int maxInsertable) {
        this.maxInsertable = maxInsertable;
    }

    public int getInsertable (int col) {
        return this.index[col];
    }

    public int getMaxInsertable () {
        return maxInsertable;
    }


    /**
     * Inserts a list of cards into a specific column of the bookshelf grid.
     * The cards are inserted from the top of the column downwards.
     * The index of the column is updated accordingly.
     *
     * @param cards The list of cards to be inserted.
     * @param col   The index of the column where the cards should be inserted.
     */
    public void insert(List<Card> cards, int col) {
        int j = 0;
        //index parte da 6
        for (int i = index[col]-1; i > index[col] - cards.size()-1; i--) {
            this.grid[i][col] = cards.get(j);
            j ++;
        }
        this.index[col] = index[col] - cards.size();
        updateMaxInsertable();
    }

    /**
     * Prints the player's bookshelf grid.
     * Each cell of the grid is represented by a colored square symbol.
     * If a cell is empty (null), it is represented by "N".
     * The colors of the squares correspond to the colors of the cards in the grid.
     */
    public void print() {
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

    /**
     * Prints the cards on the player's bookshelf.
     * The method prints the player's bookshelf grid, displaying the cards and their colors.
     * Empty spaces are represented by "|" characters.
     * Each card is represented by a colored square.
     */
    public void printMyBookshelf(){
        for (int i = 0; i < ROW; i++) {
            for(int k=0 ; k<COL ; k++) {
                System.out.print("+—+ ");
            }
            System.out.println();
            for (int j = 0; j < COL; j ++) {
                if (this.grid[i][j] == null) {
                    System.out.print("| | ");
                }
                else {
                    switch(this.grid[i][j].getColor()) {
                        case WHITE -> {
                            System.out.print("|");
                            System.out.print(ANSI_WHITE+ "□"+ANSI_RESET);
                            System.out.print("| ");
                        }
                        case BLUE -> {
                            System.out.print("|");
                            System.out.print(ANSI_BLUE+ "□"+ ANSI_RESET);
                            System.out.print("| ");
                        }
                        case ORANGE -> {
                            System.out.print("|");
                            System.out.print(ANSI_ORANGE+"□"+ANSI_RESET);
                            System.out.print("| ");
                        }
                        case PINK -> {
                            System.out.print("|");
                            System.out.print(ANSI_PINK+"□"+ANSI_RESET);
                            System.out.print("| ");
                        }
                        case CYAN -> {
                            System.out.print("|");
                            System.out.print(ANSI_CYAN+"□"+ANSI_RESET);
                            System.out.print("| ");
                        }
                        case GREEN -> {
                            System.out.print("|");
                            System.out.print(ANSI_GREEN+"□"+ANSI_RESET);
                            System.out.print("| ");
                        }
                    }
                }
            }
            System.out.println();
        }
        for(int k=0 ; k<COL ; k++) {
            System.out.print("+—+ ");
        }
        System.out.println();
        System.out.println();
        System.out.println();
    }

    /**
     * Updates the maximum number of insertable cards in a column.
     * The method iterates over each column of the bookshelf grid, calculates the number of insertable cards in the column,
     * and updates the maximum insertable value accordingly. The maximum insertable value is capped at 3.
     */
    private void updateMaxInsertable () {
        int max = 0;
        int insertable;
        for (int i = 0; i < COL; i ++) {
            insertable = index[i];
            if (insertable >= 3) {
                max = 3;
            }
            else if(insertable > max) {
                max = insertable;
            }
        }
        this.maxInsertable = max;
    }


    public Card seeCardAtCoordinates(Coordinates coor){
        return this.grid[coor.getRow()][coor.getCol()];
    }
}
