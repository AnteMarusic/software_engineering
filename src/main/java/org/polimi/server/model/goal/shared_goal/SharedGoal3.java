package org.polimi.server.model.goal.shared_goal;

import org.polimi.server.model.Card;

//3rd SHARED GOAL, AT LEAST 4 COLUMN OF 4 SAME COLOR CARDS
public class SharedGoal3 extends AbstractSharedGoal{

    public SharedGoal3(int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    protected boolean achieved(Card[][] tmpGrid) {
        int count=0;
        for(int i=5 ; i>2 ; i--){
            for(int j=0 ; j<5 ; j++){
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
