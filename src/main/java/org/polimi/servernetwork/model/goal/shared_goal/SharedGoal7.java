package org.polimi.servernetwork.model.goal.shared_goal;

import org.polimi.servernetwork.model.Card;

/**
 * concrete object that represents 7th shared goal
 */
public class SharedGoal7 extends AbstractSharedGoal{
    public SharedGoal7 (int numOfPlayer) {
        super(numOfPlayer);
    }

    /**
     * We have 4 different possible diagonal in the bookshelf, it
     * checks every single one and then return in OR if any one of
     * the single patterns is correct.
     * TIme complexity O(2*COL)
     */
    @Override
    protected boolean achieved(Card[][] tmpGrid){
        if(tmpGrid[0][0]!=null && tmpGrid[1][1]!=null && tmpGrid[2][2]!=null && tmpGrid[3][3]!=null && tmpGrid[4][4]!=null &&
                (tmpGrid[0][0].getColor() == tmpGrid[1][1].getColor() && tmpGrid[0][0].getColor() == tmpGrid[2][2].getColor()
                        && tmpGrid[0][0].getColor() == tmpGrid[3][3].getColor() && tmpGrid[0][0].getColor() == tmpGrid[4][4].getColor())){
            return true;
        }

        if(tmpGrid[1][0]!=null && tmpGrid[2][1]!=null && tmpGrid[3][2]!=null && tmpGrid[4][3]!=null && tmpGrid[5][4]!=null &&
                (tmpGrid[1][0].getColor() == tmpGrid[2][1].getColor() && tmpGrid[1][0].getColor() == tmpGrid[3][2].getColor()
                        && tmpGrid[1][0].getColor() == tmpGrid[4][3].getColor() && tmpGrid[1][0].getColor() == tmpGrid[5][4].getColor())){
            return true;
        }

        if(tmpGrid[5][0]!=null && tmpGrid[4][1]!=null && tmpGrid[3][2]!=null && tmpGrid[2][3]!=null && tmpGrid[1][4]!=null &&
                (tmpGrid[5][0].getColor() == tmpGrid[4][1].getColor() && tmpGrid[5][0].getColor() == tmpGrid[3][2].getColor()
                        && tmpGrid[5][0].getColor() == tmpGrid[2][3].getColor() && tmpGrid[5][0].getColor() == tmpGrid[1][4].getColor())){
            return true;
        }

        if(tmpGrid[4][0]!=null && tmpGrid[3][1]!=null && tmpGrid[2][2]!=null && tmpGrid[1][3]!=null && tmpGrid[0][4]!=null &&
                (tmpGrid[4][0].getColor() == tmpGrid[3][1].getColor() && tmpGrid[4][0].getColor() == tmpGrid[2][2].getColor()
                        && tmpGrid[4][0].getColor() == tmpGrid[1][3].getColor() && tmpGrid[4][0].getColor() == tmpGrid[0][4].getColor())){
            return true;
        }

        return false;
    }
}