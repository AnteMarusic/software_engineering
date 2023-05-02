package org.polimi;

import org.polimi.server.model.Coordinates;

public class GameRules {
    private static final int COL = 5;
    public static int[] getCorrectStartAndLength(int row, int numOfPlayers) {
        int start=-1, length=-1;
        int[] arrayOfInt = new int[2];
        switch (row) {
            case 0 -> {
                start = 3;
                length = 2;
                if (numOfPlayers < 4) {
                    length--;
                    if (numOfPlayers < 3) {
                        length--;
                    }
                }
            }
            case 1 -> {
                start = 3;
                length = 3;
                if (numOfPlayers < 4) {
                    length--;
                }
            }
            case 2, 6 -> {
                start = 2;
                length = 5;
                if (numOfPlayers < 3) {
                    start++;
                    length -= 2;
                }
            }
            case 3 -> {
                start = 1;
                length = 8;
                if (numOfPlayers < 4) {
                    start++;
                    length--;
                    if (numOfPlayers < 3) {
                        length--;
                    }
                }
            }
            case 4 -> {
                start = 0;
                length = 9;
                if (numOfPlayers < 4) {
                    start++;
                    length -= 2;
                }
            }
            case 5 -> {
                start = 0;
                length = 8;
                if (numOfPlayers < 4) {
                    length--;
                    if (numOfPlayers < 3) {
                        start++;
                        length--;
                    }
                }
            }
            case 7 -> {
                start = 3;
                length = 3;
                if (numOfPlayers < 4) {
                    start++;
                    length--;
                }
            }
            case 8 -> {
                start = 4;
                length = 2;
                if (numOfPlayers < 4) {
                    start++;
                    length--;
                    if (numOfPlayers < 3) {
                        length--;
                    }
                }
            }
        }
        arrayOfInt[0]= start;
        arrayOfInt[1]= length;
        return arrayOfInt;
    }

    public static boolean boardRowColInBound(int row, int col, int numOfPlayers) {
        int[] temp;
        if (row >= 9 || row < 0) return false;
        temp = getCorrectStartAndLength(row, numOfPlayers);
        return col >= temp[0] && col < temp[0] + temp[1];
    }

    public boolean bookshelfColValid(int col) {
      return !(col >= COL || col < 0);
    }

    //public boolean areCoordinatesAligned (Coordinates[] coordinates) {

    //}
}
