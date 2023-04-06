package org.polimi.shared_goal;

import org.polimi.Bookshelf;
import org.polimi.Card;

public class SharedGoal12 extends AbstractSharedGoal{
    public SharedGoal12 (int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    public boolean achieved(Card[][] tmpGrid){
        boolean pattern1=true;
        boolean pattern2=true;
        boolean pattern3=true;
        boolean pattern4=true;
        for(int i=0 ; i<6 ; i++){
            for(int j=0 ; j<5 ; j++){
                if(i<=j){
                    if(tmpGrid[i][j]==null){
                        pattern1 = false;
                    }
                }else{
                    if(tmpGrid[i][j]!=null){
                        pattern1 = false;
                    }
                }
            }
        }
        for(int i=0 ; i<6 ; i++){
            for(int j=0 ; j<5 ; j++){
                if(i<=j+1){
                    if(tmpGrid[i][j]==null){
                        pattern2 = false;
                    }
                }else{
                    if(tmpGrid[i][j]!=null){
                        pattern2 = false;
                    }
                }
            }
        }
        for(int i=0 ; i<6 ; i++){
            for(int j=0 ; j<5 ; j++){
                if(j<=i){
                    if(tmpGrid[i][j]==null){
                        pattern3 = false;
                    }
                }else{
                    if(tmpGrid[i][j]!=null){
                        pattern3 = false;
                    }
                }
            }
        }
        for(int i=0 ; i<6 ; i++){
            for(int j=0 ; j<5 ; j++){
                if(j<=i+1){
                    if(tmpGrid[i][j]==null){
                        pattern4 = false;
                    }
                }else{
                    if(tmpGrid[i][j]!=null){
                        pattern4 = false;
                    }
                }
            }
        }
        return pattern1||pattern2||pattern3||pattern4;
    }
}
