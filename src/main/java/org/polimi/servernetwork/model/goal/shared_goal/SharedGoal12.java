package org.polimi.servernetwork.model.goal.shared_goal;

import org.polimi.servernetwork.model.Card;

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
    protected boolean achieved(Card[][] tmpGrid) {
        int tmp;
        return pattern1(tmpGrid) || pattern2(tmpGrid) || pattern3(tmpGrid) || pattern4(tmpGrid);
    }

    private boolean pattern1 (Card[][] tmpGrid){
        int tmp;
        for(int j=0 ; j<COL ; j++){
            for(int i=ROW-1 ; i>=0 ; i--){
                if(i+j>=5){
                    if(tmpGrid[i][j]==null) {
                        return false;
                    }
                }else{
                    if(tmpGrid[i][j]!=null){
                        return false;
                    }
                }

            }
        }
        return true;
    }
    private boolean pattern2 (Card[][] tmpGrid){
        int tmp;
        for(int j=0 ; j<COL ; j++){
            for(int i=ROW-1 ; i>=0 ; i--){
                if(i+j>=4){
                    if(tmpGrid[i][j]==null) {
                        return false;
                    }
                }else{
                    if(tmpGrid[i][j]!=null){
                        return false;
                    }
                }

            }
        }
        return true;
    }

    private boolean pattern3 (Card[][] tmpGrid){
        int tmp;
        for(int j=COL-1 ; j>=0 ; j--){
            for(int i=ROW-1 ; i>=0 ; i--){
                if(i-j>=1){
                    if(tmpGrid[i][j]==null) {
                        return false;
                    }
                }else{
                    if(tmpGrid[i][j]!=null){
                        return false;
                    }
                }

            }
        }
        return true;
    }

    private boolean pattern4 (Card[][] tmpGrid){
        int tmp;
        for(int j=COL-1 ; j>=0 ; j--){
            for(int i=ROW-1 ; i>=0 ; i--){
                if(i-j>=0){
                    if(tmpGrid[i][j]==null) {
                        return false;
                    }
                }else{
                    if(tmpGrid[i][j]!=null){
                        return false;
                    }
                }

            }
        }
        return true;
    }
}
