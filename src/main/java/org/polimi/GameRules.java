package org.polimi;

import org.polimi.servernetwork.model.Coordinates;

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

    public static boolean bookshelfRowColIsInBound(Coordinates coor){
        return ((coor.getRow()>=0 && coor.getRow()<=5) && (coor.getCol()>=0 && coor.getCol()<=4));
    }

    public static boolean bookshelfColInBound (int col) {
        return col >=0 && col<=4;
    }

    public static boolean boardRowColInBound(int row, int col, int numOfPlayers) {
        int[] temp;
        if (row >= 9 || row < 0) return false;
        temp = getCorrectStartAndLength(row, numOfPlayers);
        return col >= temp[0] && col < temp[0] + temp[1];
    }

    /**
     * @requires c1 and c2 are different coordinates (at least one amongst row col is different)
     * @param c1 first coordinate (positive values only)
     * @param c2 second coordinate (positive values only)
     * @return true if aligned
     */
    public static boolean areCoordinatesAligned (Coordinates c1, Coordinates c2) {
        return (c2.getRow() == c1.getRow() && (c2.getCol() == c1.getCol() + 1 || c2.getCol() == c1.getCol() - 1)) ||
                c2.getCol() == c1.getCol() && ((c2.getRow() == c1.getRow() + 1 ||c2.getRow() == c1.getRow() - 1));
    }

    /**
     * @requires c1, c2 and c3 are different coordinates (at least one amongst row col is different)
     * @param c1 first coordinate (positive values only)
     * @param c2 second coordinate (positive values only)
     * @param c3 third coordinate (positive values only)
     * @return true if aligned
     */
    public static boolean areCoordinatesAligned (Coordinates c1, Coordinates c2, Coordinates c3) {
        return ((c2.getRow() == c1.getRow() && c3.getRow() == c2.getRow () &&
                (c2.getCol() == c1.getCol() + 1 && c3.getCol() == c1.getCol() + 2) || (c2.getCol() == c1.getCol() - 1) && c3.getCol() == c1.getCol() - 2))
                ||
                (c2.getCol() == c1.getCol() && c3.getCol() == c2.getCol () &&
                        ((c2.getRow() == c1.getRow() + 1 && c3.getRow() == c1.getRow() + 2)||c2.getRow() == c1.getRow() - 1 && c3.getRow() == c1.getRow() - 2));
    }

}
