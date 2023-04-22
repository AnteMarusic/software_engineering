package org.polimi.server.model.goal.shared_goal;

import org.polimi.server.model.Card;

/**
 * concrete object that represents 3rd shared goal
 */
public class SharedGoal3 extends AbstractSharedGoal{

    public SharedGoal3(int numOfPlayer) {
        super(numOfPlayer);
    }

    /**
     * in order to achieve the points you must have 4 columns made of
     * 4 cards of the same color
     * Exhaustive check, complexity o((ROW-3) * COL)
     */
    @Override
    protected boolean achieved(Card[][] tmpGrid) {
        int count=0;
        for(int i=ROW-1 ; i>2 ; i--){
            for(int j=0 ; j<COL ; j++){
                if(     tmpGrid[i][j].getColor() == tmpGrid[i-1][j].getColor() &&
                        tmpGrid[i][j].getColor() == tmpGrid[i-2][j].getColor() &&
                        tmpGrid[i][j].getColor() == tmpGrid[i-3][j].getColor()
                ){
                    count++;
                }
            }
            if(count >= 4){
                return true;
            }
        }
        return false;
    }
}
