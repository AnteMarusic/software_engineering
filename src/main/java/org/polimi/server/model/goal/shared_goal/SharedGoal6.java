package org.polimi.server.model.goal.shared_goal;

import org.polimi.server.model.Card;

//6th SHARED GOAL, at least 8 cards with the same color no matter the position
public class SharedGoal6 extends AbstractSharedGoal{
    public SharedGoal6 (int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    protected boolean achieved(Card[][] tmpGrid){
        int [] colorCount = new int[6];
        for(int i=0 ; i<6 ; i++){
            colorCount[i] = 0;
        }
        int count;
        for(int i=0 ; i<5 ; i++){
            for(int j=0 ; j<4 ; j++){
                switch (tmpGrid[i][j].getColor()){
                    case PINK -> colorCount[0]++;
                    case CYAN -> colorCount[1]++;
                    case ORANGE -> colorCount[2]++;
                    case WHITE -> colorCount[3]++;
                    case GREEN -> colorCount[4]++;
                    case BLUE -> colorCount[5]++;
                }
            }
        }
        for(int i=0 ; i<6 ; i++){
            if(colorCount[i] >= 8){
                return true;
            }
        }
        return false;
    }
}
