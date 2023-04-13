package org.polimi;

public class BoardCoordinates extends AbstractCoordinates{
    private static int numOfPlayers;
    private static final int MAX_DIM = 9;
    public BoardCoordinates(int row, int col) {
        super(row, col);
    }

    public static void setNumOfPlayers(int numOfPlayers) {
        BoardCoordinates.numOfPlayers = numOfPlayers;
    }

    @Override
    public boolean isValid() {
        int[] temp;
        if (this.getRow() >= 9 || this.getRow() < 0) return false;
        temp = getCorrectStartAndLength(this.getRow());
        return this.getCol() >= temp[0] && this.getCol() < temp[0] + temp[1];
    }


    private int[] getCorrectStartAndLength(int row) {
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
                    length--;
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
                    length--;
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
}
