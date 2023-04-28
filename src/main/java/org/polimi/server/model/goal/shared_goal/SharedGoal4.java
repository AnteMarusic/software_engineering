package org.polimi.server.model.goal.shared_goal;

import org.polimi.server.model.Card;


/**
 * concrete object that represents the 4th shared goal
 */
public class SharedGoal4 extends AbstractSharedGoal{
    public SharedGoal4(int numOfPlayer) {
        super(numOfPlayer);
    }

    /**
     * in order to achieve the points you must have 2 times a
     * 2x2 square blocks made by the cards of the same color
     * Exhaustive check, complexity o((ROW-3) * COL)
     */
    @Override
    protected boolean achieved(Card[][] tmpGrid){
        int count=0;
        for(int i=ROW-1 ; i>0 ; i--){
            for(int j=0 ; j<COL-1 ; j++){
                if(tmpGrid[i][j]!=null && tmpGrid[i-1][j]!=null  && tmpGrid[i-1][j+1]!=null  && tmpGrid[i][j+1]!=null) {
                    if (tmpGrid[i][j].getColor() == tmpGrid[i - 1][j].getColor() &&
                            tmpGrid[i][j].getColor() == tmpGrid[i - 1][j + 1].getColor() &&
                            tmpGrid[i][j].getColor() == tmpGrid[i][j + 1].getColor()
                    ) {
                        count++;
                    }
                }
            }
            if(count>=2){
                return true;
            }
        }
        return false;
    }
}
