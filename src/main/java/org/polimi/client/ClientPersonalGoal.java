package org.polimi.client;

import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

public class ClientPersonalGoal {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_PINK = "\u001B[35m"; //PINK
    public static final String ANSI_GREEN = "\u001B[32m"; //GREEN
    public static final String ANSI_WHITE = "\u001B[37m"; //WHITE
    public static final String ANSI_ORANGE = "\u001B[31m"; //ORANGE
    public static final String ANSI_CYAN = "\u001B[36m"; //CYAN
    public static final String ANSI_BLUE = "\u001B[34m"; //BLUE
    private static final int ROW = 6;
    private static final int COL = 5;
    private final Card.Color[][] personalGoal;

    public ClientPersonalGoal (Coordinates[] coordinates, Card.Color[] colors) {
        personalGoal = new Card.Color[ROW][COL];
        if (coordinates.length != 6) {
            throw new IllegalArgumentException("in personal goal there are always 6 coordinates");
        }
        if (colors.length != 6) {
            throw new IllegalArgumentException("in personal goal there are always 6 colors");
        }
        for (int i = 0; i < 6; i ++) {
            this.personalGoal[coordinates[i].getRow()][coordinates[i].getCol()] = colors[i];
        }
    }


    public void print() {
        for (int i = ROW - 1; i >= 0; i--) {
            for (int j = 0; j < COL; j++) {
                if (this.personalGoal[i][j] == null) {
                    System.out.print("N");
                } else {
                    switch (this.personalGoal[i][j]) {
                        case WHITE -> {
                            System.out.print(ANSI_WHITE + "□" + ANSI_RESET);
                        }
                        case BLUE -> {
                            System.out.print(ANSI_BLUE + "□" + ANSI_RESET);
                        }
                        case ORANGE -> {
                            System.out.print(ANSI_ORANGE + "□" + ANSI_RESET);
                        }
                        case PINK -> {
                            System.out.print(ANSI_PINK + "□" + ANSI_RESET);
                        }
                        case CYAN -> {
                            System.out.print(ANSI_CYAN + "□" + ANSI_RESET);
                        }
                        case GREEN -> {
                            System.out.print(ANSI_GREEN + "□" + ANSI_RESET);
                        }
                    }
                }
            }
            System.out.println(" ");
        }
    }
}
