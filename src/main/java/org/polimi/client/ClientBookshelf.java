package org.polimi.client;

import org.jetbrains.annotations.NotNull;
import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;
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
    final private HashMap<Coordinates, Card> grid;
    final private int[] index;
    private int maxInsertable;

    public ClientBookshelf() {
        this.grid = new HashMap<>(COL*ROW);
        this.maxInsertable = 3;
        this.index = new int[COL];
        for (int i = 0; i < COL; i ++) {
            this.index[i] = 0;
        }
    }

    public int getInsertable (int col) {
        return ROW - this.index[col];
    }

    public void print() {
        for (int i = ROW - 1; i >= 0; i --) {
            for (int j = 0; j < COL; j ++) {
                if (this.grid.get(new Coordinates(i, j)) == null) {
                    System.out.print("N ");
                }
                else {
                    switch(this.grid.get(new Coordinates(i, j)).getColor()) {
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
        for (int i = ROW - 1; i >= 0; i --) {
            for(int k=0 ; k<COL ; k++) {
                System.out.print("+—+ ");
            }
            System.out.println();
            for (int j = 0; j < COL; j ++) {
                if (this.grid.get(new Coordinates(i, j)) == null) {
                    System.out.print("| | ");
                }
                else {
                    switch(this.grid.get(new Coordinates(i, j)).getColor()) {
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

    public int getMaxInsertable () {
        return maxInsertable;
    }

    public void insert(List<Card> cards, int col) {
        int j = 0;
        for (int i = index[col]; i < index[col] + cards.size(); i ++) {
            this.grid.put(new Coordinates(i , col), cards.get(j));
            j ++;
        }
        this.index[col] = index[col] + cards.size();
    }
}
