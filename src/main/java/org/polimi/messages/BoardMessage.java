package org.polimi.messages;

import org.jetbrains.annotations.NotNull;
import org.polimi.GameRules;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.io.*;
import java.nio.Buffer;
import java.util.Map;

public class BoardMessage extends Message implements Serializable {
    private final Map<Coordinates, Card> board;
    /*
    private final static int numOfPlayers = 2;
    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_PINK = "\u001B[35m"; //PINK
    public static final String ANSI_GREEN = "\u001B[32m"; //GREEN
    public static final String ANSI_WHITE = "\u001B[37m"; //WHITE
    public static final String ANSI_ORANGE = "\u001B[31m"; //ORANGE
    public static final String ANSI_CYAN = "\u001B[36m"; //CYAN
    public static final String ANSI_BLUE = "\u001B[34m"; //BLUE
    private final static int DIM = 9;

     */
    public BoardMessage (Map<Coordinates, Card> board){
        super("server", MessageType.BOARDMESSAGE);
        this.board = board;
    }

    public Map<Coordinates, Card> getBoard() {
        return board;
    }
    @Override
    public String toString() {
        return mapToString(board);
    }

    public static String mapToString(Map<?, ?> map) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append(" = ")
                    .append(entry.getValue())
                    .append(", ");
        }

        // Remove the trailing comma and space
        if (stringBuilder.length() > 2) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        return stringBuilder.toString();
    }

    /*
    //debug, funziona solo con board di due giocatori
    private String printBoard() {
        int start, length;
        int[] temp;
        Card card;
        // Create a ByteArrayOutputStream to hold the printed output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Create a PrintStream that writes to the ByteArrayOutputStream
        PrintStream printStream = new PrintStream(outputStream);

        // Redirect the standard output to the PrintStream
        System.setOut(printStream);

        System.out.print("    ");
        for(int i = 0; i< DIM; i++){
            System.out.print(" " + i + "  ");
        }
        System.out.println();

        // da sistemare
        for (int row = 0; row < DIM; row ++) {
            temp = GameRules.getCorrectStartAndLength(row, numOfPlayers);
            start = temp [0];
            length = temp[1];
            if(row==0){
                System.out.print("   ");
                for (int i = 0; i < start; i++) {
                    System.out.print("    ");
                }
                System.out.print(" ");
                for (int i=start; i< start + length; i++) {
                    System.out.print("+—+ ");
                }
                System.out.println();
            }
            //stampa seconda riga, colori
            System.out.print(row);
            System.out.print("   ");
            for (int i = 0; i < start; i++) {
                System.out.print("    ");
            }
            for (int col = start; col < length + start; col ++) {
                card = this.board.get(new Coordinates(row, col));
                if(card == null)
                    System.out.print("| | ");
                else {
                    switch(card.getColor()) {
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
            System.out.print("\n");

            //stampa terza riga "+—+"
            temp = GameRules.getCorrectStartAndLength(row+1, numOfPlayers);
            start = temp [0];
            length = temp[1];
            System.out.print("   ");
            for (int i = 0; i < start; i++) {
                System.out.print("    ");
            }
            System.out.print(" ");
            for (int col = start; col < length + start; col++) {
                System.out.print("+—+ ");
            }
            System.out.println();
        }
        return outputStream.toString();
    }

     */
}
