package org.polimi.shared_goal;

import org.polimi.Bookshelf;
import org.polimi.Card;

public class SharedGoal11 extends AbstractSharedGoal{
    public SharedGoal11 (int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    protected boolean achieved(Card[][] tmp){
        for(int i=1 ; i<5 ; i++){
            for(int j=1 ; j<4 ; j++){
                if(     (tmp[i][j].getColor() == tmp[i-1][j-1].getColor()) &&
                        (tmp[i][j].getColor() == tmp[i-1][j+1].getColor()) &&
                        (tmp[i][j].getColor() == tmp[i+1][j-1].getColor()) &&
                        (tmp[i][j].getColor() == tmp[i+1][j+1].getColor())
                ){
                    return true;
                }
            }
        }
        return false;
    }
}