package org.polimi.shared_goal;

import org.polimi.Bookshelf;
import org.polimi.Card;

public class SharedGoal10 extends AbstractSharedGoal {
    public SharedGoal10 (int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    protected boolean achieved(Card[][] tmpGrid){
        int[] colorCount = new int[6];
        int rowCount = 0;
        boolean flag;
        for(int i=0 ; i<6 ; i++){
            for(int k=0 ; k<6 ; k++){
                colorCount[k] = 0;
            }
            flag = true;
            for(int j=0 ; j<5 ; j++){
                switch (tmpGrid[i][j].getColor()){
                    case PINK -> colorCount[0]++;
                    case CYAN -> colorCount[1]++;
                    case ORANGE -> colorCount[2]++;
                    case WHITE -> colorCount[3]++;
                    case GREEN -> colorCount[4]++;
                    case BLUE -> colorCount[5]++;
                }
            }
            for(int k=0 ; k<6 ; k++){
                if(colorCount[k] > 1){
                    flag = false;
                }
            }
            if(flag){
                rowCount++;
            }
            if(rowCount>=2){
                return true;
            }
        }
        return false;
    }
}