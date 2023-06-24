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
        System.out.println("print index:");
        for (int i = 0; i < COL; i ++) {
            for (int j = ROW - 1; j > 0; j --) {
                if (grid[j][i] == null) {
                    this.index[i] = ROW;
                    System.out.println("valore di index in clientbookshelf: "+index[i]+ "in posizione" + i);
                    break;
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

    public void insert(List<Card> cards, int col) {
        int j = 0;
        //index parte da 6
        System.out.println("sto inserendo questo numero di carte: " + cards.size());
        for (int i = index[col]-1; i > index[col] - cards.size()-1; i--) {
            this.grid[i][col] = cards.get(j);
            j ++;
        }
        this.index[col] = index[col] - cards.size();
    }

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
    public Card seeCardAtCoordinates(Coordinates coor){
        return this.grid[coor.getRow()][coor.getCol()];
    }
}
