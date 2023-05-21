package org.polimi.servernetwork.model.goal.shared_goal;

import org.polimi.servernetwork.model.Card;

/**
 * concrete object that represents 8th shared goal
 */
public class SharedGoal8 extends AbstractSharedGoal{
    public SharedGoal8 (int numOfPlayer) {
        super(numOfPlayer);
    }

    /**
     * At least 4 rows fulfilled with at maximum 3 different
     * colors per row
     * Time complexity: O()
     */
    @Override
    protected boolean achieved(Card[][] tmpGrid){
        int[] colorCount = new int[NUMOFCOLORS];
        int rowCount = 0;
        int tmpCount;
        for(int i=0 ; i<ROW ; i++){
            if(rowIsFull(tmpGrid, i)) {
                for (int k = 0; k < NUMOFCOLORS; k++) {
                    colorCount[k] = 0;
                }
                tmpCount = 0;
                for (int j = 0; j < 5; j++) {
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
                    rowCount++;
                }
                if (rowCount >= 4) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean rowIsFull(Card[][] tmpGrid, int row){
        for(int i=0 ; i<COL ; i++){
            if(tmpGrid[row][i]==null){
                return false;
            }
        }
        return true;
    }
}
