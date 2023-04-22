package org.polimi.server.model.goal.shared_goal;

import org.polimi.server.model.Card;

/**
 * concrete object that represents the 1st shared goal
 */
public class SharedGoal1 extends AbstractSharedGoal {
    public SharedGoal1(int numOfPlayer) {
        super(numOfPlayer);
    }

    /**
     * in order to achieve the points you must have 6 times a vertical
     * couple made of cards of the same color.
     * Exhaustive check, complexity o((ROW/2) * COL)
     */
    @Override
    protected boolean achieved(Card[][] tmpGrid) {
        int count=0;
        for(int i=ROW-1 ; i>=1 ; i=i-2){
            for(int j=0 ; j<COL ; j++){
                if(tmpGrid[i][j].getColor() == tmpGrid[i-1][j].getColor()){
                    count++;
                }
            }
            if(count>=6){
                return true;
            }
        }
        return false;
    }

}
