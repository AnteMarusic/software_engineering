package org.polimi.server.model.goal.shared_goal;

import org.polimi.server.model.Card;
/**
 * concrete object that represents 11th shared goal
 */
public class SharedGoal11 extends AbstractSharedGoal{
    public SharedGoal11 (int numOfPlayer) {
        super(numOfPlayer);
    }

    /**
     *5 cards of the same color representing an X.
     * Time complexity: O(ROW*COL)
     */
    @Override
    protected boolean achieved(Card[][] tmp){
        for(int i=1 ; i<ROW-1 ; i++){
            for(int j=1 ; j<COL-1 ; j++){
                if(tmp[i][j]!=null && tmp[i-1][j-1]!=null && tmp[i+1][j-1]!=null && tmp[i-1][j+1]!=null && tmp[i+1][j+1]!=null) {
                    if ((tmp[i][j].getColor() == tmp[i - 1][j - 1].getColor()) &&
                            (tmp[i][j].getColor() == tmp[i - 1][j + 1].getColor()) &&
                            (tmp[i][j].getColor() == tmp[i + 1][j - 1].getColor()) &&
                            (tmp[i][j].getColor() == tmp[i + 1][j + 1].getColor())
                    ) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}