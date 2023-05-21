package org.polimi.servernetwork.model.goal.shared_goal;

import org.polimi.servernetwork.model.Card;

/**
 * concrete object that represents 5th shared goal
 */
public class SharedGoal5 extends AbstractSharedGoal {
    public SharedGoal5(int numOfPlayer) {
        super(numOfPlayer);
    }


    /**
     * Every time a column is visited the colorCount array stores which
     * colors and how many times ...
     * Then if the number of colors in a single column is less than 3
     * a variable is incremented, when that variable is greater than 2 (>=3)
     * the method returns true
     * Time complexity O(COL*(ROW+(2*numOfColors)))
     */
    @Override
    protected boolean achieved(Card[][] tmpGrid) {
        int[] colorCount = new int[6];
        int columnCount = 0;
        int tmpCount;
        for(int j=0 ; j<COL ; j++) {
            if (columnIsFull(tmpGrid, j)){
                for (int k = 0; k < NUMOFCOLORS; k++) {
                    colorCount[k] = 0;
                }
                tmpCount = 0;
                for (int i = 0; i < ROW; i++) {
                    if (tmpGrid[i][j] != null) {
                        switch (tmpGrid[i][j].getColor()) {
                            case PINK -> colorCount[0]++;
                            case CYAN -> colorCount[1]++;
                            case ORANGE -> colorCount[2]++;
                            case WHITE -> colorCount[3]++;
                            case GREEN -> colorCount[4]++;
                            case BLUE -> colorCount[5]++;
                        }
                    }
                }
                for (int k = 0; k < NUMOFCOLORS; k++) {
                    if (colorCount[k] > 0) {
                        tmpCount++;
                    }
                }
                if (tmpCount <= 3) {
                    columnCount++;
                }
                if (columnCount >= 3) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean columnIsFull(Card[][] tmpGrid, int col){
        for(int i=0 ; i<ROW ; i++){
            if(tmpGrid[i][col]==null){
                return false;
            }
        }
        return true;
    }
}
