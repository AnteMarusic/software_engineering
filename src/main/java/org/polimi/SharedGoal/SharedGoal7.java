package org.polimi.SharedGoal;

import org.polimi.Bookshelf;
import org.polimi.Card;

//7th SHARED GOAL, at least 5 cards as a diagonal in every direction
public class SharedGoal7 extends AbstractSharedGoal{
    public SharedGoal7 (int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    public boolean achieved(Card[][] tmpGrid){
        boolean pattern1 = true;
        boolean pattern2 = true;
        boolean pattern3 = true;
        boolean pattern4 = true;
        for(int j=0 ; j<5 ; j++){
            int i=j;
            if(tmpGrid[i][j].getColor() != tmpGrid[i][j].getColor()){
                pattern1 = false;
            }
            i=j+1;
            if(tmpGrid[i][j].getColor() != tmpGrid[i][j].getColor()){
                pattern2 = false;
            }
        }

        for(int j=4 ; j>=0 ; j--){
            int i = - (j-4);
            if(tmpGrid[i][j].getColor() != tmpGrid[i][j].getColor()){
                pattern3 = false;
            }
            i = (-(j-4))+1;
            if(tmpGrid[i][j].getColor() != tmpGrid[i][j].getColor()){
                pattern4 = false;
            }
        }

        return pattern1 || pattern2 || pattern3 || pattern4;
    }
}