package org.polimi.servernetwork.model.goal.shared_goal;

import org.polimi.servernetwork.model.Card;

/**
 * concrete object that represents the 2nd shared goal
 */
public class SharedGoal2 extends AbstractSharedGoal{
    public SharedGoal2(int numOfPlayer) {
        super(numOfPlayer);
    }

    /**
     * in order to achieve the points you must have 4
     * cards of the same color at the 4 corners.
     * Direct check, time complexity O(1)
     */
    @Override
    protected boolean achieved(Card[][] tmpGrid) {
        if(tmpGrid[0][0]!=null && tmpGrid[ROW-1][COL-1]!=null && tmpGrid[0][COL-1]!=null && tmpGrid[ROW-1][0]!=null) {
            return (tmpGrid[0][0].getColor() == tmpGrid[ROW - 1][COL - 1].getColor() &&
                    tmpGrid[0][0].getColor() == tmpGrid[0][COL - 1].getColor() &&
                    tmpGrid[0][0].getColor() == tmpGrid[ROW - 1][0].getColor()
            );
        }else{
            return false;
        }
    }
}
