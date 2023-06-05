package org.polimi.servernetwork.model.goal.shared_goal;

import org.polimi.servernetwork.model.Card;

/**
 * concrete object that represents 9th shared goal
 */
public class SharedGoal9 extends AbstractSharedGoal{
    public SharedGoal9 (int numOfPlayer) {
        super(numOfPlayer);
    }

    /**
     *At least 2 columns covered with cards all different
     * coloured.
     * Time complexity, O(COL*(2*NUMOFCOLORS + ROW))
     */
    @Override
    protected boolean achieved(Card[][] tmpGrid){
        int[] colorCount = new int[NUMOFCOLORS];
        int columnCount = 0;
        boolean flag;
        for(int j=0 ; j<COL ; j++){
            for (int k = 0; k < NUMOFCOLORS; k++) {
                colorCount[k] = 0;
            }
            flag = true;
            if(columnIsFull(tmpGrid,j)) {
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
                    if (colorCount[k] >= 2) {
                        flag = false;
                    }
                }
                if (flag) {
                    columnCount++;
                }
                if (columnCount >= 2) {
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
