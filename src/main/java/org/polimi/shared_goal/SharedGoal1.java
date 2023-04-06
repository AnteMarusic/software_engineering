package org.polimi.shared_goal;

import org.polimi.Bookshelf;
import org.polimi.Card;

//1st SHARED GOAL, X6 VERTICAL EQUAL COUPLES
public class SharedGoal1 extends AbstractSharedGoal {
    public SharedGoal1(int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    protected boolean achieved(Card[][] tmpGrid) {
        int count=0;
        for(int i=5 ; i>=1 ; i--){
            for(int j=0 ; j<5 ; j++){
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
