package org.polimi.server.model.goal.shared_goal;

import org.polimi.server.model.Card;

/**
 * concrete object that represents 12th shared goal
 */
public class SharedGoal12 extends AbstractSharedGoal{
    public SharedGoal12 (int numOfPlayer) {
        super(numOfPlayer);
    }
    /**
     *the bookshelf has an upper zone without cards and a lower one filled with cards.
     * There must be a diagonal separating these 2 zones.
     * CHeck of every 4 possible pattern, time complexity O(4*(ROW*COL))
     */
    @Override
    protected boolean achieved(Card[][] tmpGrid){
        boolean flag=true;
        for(int i=0 ; i<ROW ; i++){
            for(int j=0 ; j<COL ; j++){
                if(i<=j){
                    if(tmpGrid[i][j]==null){
                        flag = false;
                    }
                }else{
                    if(tmpGrid[i][j]!=null){
                        flag = false;
                    }
                }
            }
        }
        if(flag){
            return true;
        }
        flag=true;
        for(int i=0 ; i<ROW ; i++){
            for(int j=0 ; j<COL ; j++){
                if(i<=j+1){
                    if(tmpGrid[i][j]==null){
                        flag = false;
                    }
                }else{
                    if(tmpGrid[i][j]!=null){
                        flag = false;
                    }
                }
            }
        }
        if(flag){
            return true;
        }
        flag = true;
        for(int i=0 ; i<ROW ; i++){
            for(int j=0 ; j<COL ; j++){
                if(j<=i){
                    if(tmpGrid[i][j]==null){
                        flag = false;
                    }
                }else{
                    if(tmpGrid[i][j]!=null){
                        flag = false;
                    }
                }
            }
        }
        if(flag){
            return true;
        }
        flag = true;
        for(int i=0 ; i<ROW ; i++){
            for(int j=0 ; j<COL; j++){
                if(j<=i+1){
                    if(tmpGrid[i][j]==null){
                        flag = false;
                    }
                }else{
                    if(tmpGrid[i][j]!=null){
                        flag = false;
                    }
                }
            }
        }
        if(flag){
            return true;
        }
        return false;
    }
}
