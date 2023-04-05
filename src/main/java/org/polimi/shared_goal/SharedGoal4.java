package org.polimi.shared_goal;

import org.polimi.Bookshelf;
import org.polimi.Card;

//4th SHARED GOAL, two 2x2 matrices with same colors
public class SharedGoal4 extends AbstractSharedGoal{
    public SharedGoal4(int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    public boolean achieved(Card[][] tmpGrid){
        int count=0;
        for(int i=5 ; i>0 ; i--){
            for(int j=0 ; j<4 ; j++){
                if(     tmpGrid[i][j].getColor() == tmpGrid[i-1][j].getColor() &&
                        tmpGrid[i][j].getColor() == tmpGrid[i-1][j+1].getColor() &&
                        tmpGrid[i][j].getColor() == tmpGrid[i][j+1].getColor()
                ){
                    count++;
                }
            }
            if(count>=2){
                return true;
            }
        }
        return false;
    }
}
