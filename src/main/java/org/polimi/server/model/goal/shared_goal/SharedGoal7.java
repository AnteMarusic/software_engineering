package org.polimi.server.model.goal.shared_goal;

import org.polimi.server.model.Card;

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
        boolean pattern1 = true;
        boolean pattern2 = true;
        boolean pattern3 = true;
        boolean pattern4 = true;
        for(int j=0 ; j<COL ; j++){
            int i=j;
            if(tmpGrid[i][j].getColor() != tmpGrid[i][j].getColor()){
                pattern1 = false;
            }
            i=j+1;
            if(tmpGrid[i][j].getColor() != tmpGrid[i][j].getColor()){
                pattern2 = false;
            }
        }

        for(int j=COL-1 ; j>=0 ; j--){
            int i = - (j-COL-1);
            if(tmpGrid[i][j].getColor() != tmpGrid[i][j].getColor()){
                pattern3 = false;
            }
            i = (-(j-COL-1))+1;
            if(tmpGrid[i][j].getColor() != tmpGrid[i][j].getColor()){
                pattern4 = false;
            }
        }

        return pattern1 || pattern2 || pattern3 || pattern4;
    }
}