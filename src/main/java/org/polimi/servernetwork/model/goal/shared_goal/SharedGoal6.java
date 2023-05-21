package org.polimi.servernetwork.model.goal.shared_goal;

import org.polimi.servernetwork.model.Card;

/**
 * concrete object that represents 6th shared goal
 */
public class SharedGoal6 extends AbstractSharedGoal{
    public SharedGoal6 (int numOfPlayer) {
        super(numOfPlayer);
    }

    /**
     * It saves in coloCount how many cards per color
     * we have in our bookshelf, then it checks
     * if there is at least a color with 6+ cards
     * Time complexity O((2*numOfColors) + (ROW*COL))
     */
    @Override
    protected boolean achieved(Card[][] tmpGrid){
        int [] colorCount = new int[NUMOFCOLORS];
        for(int i=0 ; i<NUMOFCOLORS ; i++){ //6 is num of different colors
            colorCount[i] = 0;
        }
        int count;
        for(int i=0 ; i<ROW ; i++){
            for(int j=0 ; j<COL ; j++){
                if(tmpGrid[i][j]!=null) {
                    switch (tmpGrid[i][j].getColor()) {
                        case PINK -> colorCount[0]++;
                        case CYAN -> colorCount[1]++;
                        case ORANGE -> colorCount[2]++;
                        case WHITE -> colorCount[3]++;
                        case GREEN -> colorCount[4]++;
                        case BLUE -> colorCount[5]++;
                    }
                }
            }
        }
        for(int i=0 ; i<NUMOFCOLORS ; i++){
            if(colorCount[i] >= 8){
                return true;
            }
        }
        return false;
    }
}
