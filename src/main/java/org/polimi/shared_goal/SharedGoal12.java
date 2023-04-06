package org.polimi.shared_goal;

import org.polimi.Bookshelf;
import org.polimi.Card;

public class SharedGoal12 extends AbstractSharedGoal{
    public SharedGoal12 (int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    public boolean achieved(Card[][] tmpGrid){
        boolean flag=true;
        for(int i=0 ; i<6 ; i++){
            for(int j=0 ; j<5 ; j++){
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
        flag=false;
        for(int i=0 ; i<6 ; i++){
            for(int j=0 ; j<5 ; j++){
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
        for(int i=0 ; i<6 ; i++){
            for(int j=0 ; j<5 ; j++){
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
        for(int i=0 ; i<6 ; i++){
            for(int j=0 ; j<5 ; j++){
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
