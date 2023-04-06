package org.polimi.shared_goal;

import org.polimi.Bookshelf;
import org.polimi.Card;

//9th SHARED GOAL, at least 2 columns with all 6 cards different
public class SharedGoal9 extends AbstractSharedGoal{
    public SharedGoal9 (int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    protected boolean achieved(Card[][] tmpGrid){
        int[] colorCount = new int[6];
        int columnCount = 0;
        boolean flag;
        for(int j=0 ; j<5 ; j++){
            for(int k=0 ; k<6 ; k++){
                colorCount[k] = 0;
            }
            flag = true;
            for(int i=0 ; i<6 ; i++){
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
                columnCount++;
            }
            if(columnCount>=2){
                return true;
            }
        }
        return false;
    }
}
