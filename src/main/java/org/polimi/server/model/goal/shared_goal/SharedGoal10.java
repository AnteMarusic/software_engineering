package org.polimi.server.model.goal.shared_goal;

import org.polimi.server.model.Card;

/**
 * concrete object that represents 10th shared goal
 */
public class SharedGoal10 extends AbstractSharedGoal {
    public SharedGoal10 (int numOfPlayer) {
        super(numOfPlayer);
    }

    /**
     *At least 2 rows covered with different coloured cards.
     * Time complexity, O(ROW*(2*NUMOFCOLORS + COL))
     */
    @Override
    protected boolean achieved(Card[][] tmpGrid){
        int[] colorCount = new int[6];
        int rowCount = 0;
        boolean flag;
        for(int i=0 ; i<ROW ; i++){
            for(int k=0 ; k<NUMOFCOLORS ; k++){
                colorCount[k] = 0;
            }
            flag = true;
            for(int j=0 ; j<COL ; j++){
                if(tmpGrid[i][j]!=null) {
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
            for(int k=0 ; k<NUMOFCOLORS ; k++){
                if(colorCount[k] > 1){
                    flag = false;
                }
            }
            if(flag){
                rowCount++;
            }
            if(rowCount>=2){
                return true;
            }
        }
        return false;
    }
}