package org.polimi.server.model.shared_goal;

import org.polimi.server.model.Card;

//5th SHARED GOAL, AT LEAST 3 COLUMNS WITH NO MORE THAN 3 COLORS
public class SharedGoal5 extends AbstractSharedGoal {
    public SharedGoal5(int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    protected boolean achieved(Card[][] tmpGrid) {
        int[] colorCount = new int[6];
        int rowCount = 0;
        int tmpCount;
        for(int j=0 ; j<5 ; j++){
            for(int k=0 ; k<6 ; k++){
                colorCount[k] = 0;
            }
            tmpCount = 0;
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
                if(colorCount[k]>0){
                    tmpCount++;
                }
            }
            if(tmpCount<=3){
                rowCount++;
            }
            if(rowCount>=3){
                return true;
            }
        }
        return false;
    }
}
