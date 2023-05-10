package org.polimi.server.model.goal.shared_goal;

import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * concrete object that represents the 4th shared goal
 */
public class SharedGoal4 extends AbstractSharedGoal{
    public SharedGoal4(int numOfPlayer) {
        super(numOfPlayer);
    }

    /**
     * in order to achieve the points you must have 2 times a
     * 2x2 square blocks made by the cards of the same color
     * Exhaustive check, complexity o((ROW-3) * COL)
     */
    @Override
    protected boolean achieved(Card[][] tmpGrid) {
        Coordinates[][] blocksFound = new Coordinates[2][4];
        Coordinates[] auxCoor = new Coordinates[4];
        int index=0;
        for(int i=1 ; i<ROW ; i++){
            for(int j=1 ; j<COL ; j++){
                if(tmpGrid[i-1][j-1]!=null && tmpGrid[i][j-1]!=null && tmpGrid[i-1][j]!=null && tmpGrid[i][j]!=null){
                    if(     (tmpGrid[i][j].getColor() == tmpGrid[i-1][j-1].getColor()) &&
                            (tmpGrid[i][j].getColor() == tmpGrid[i-1][j].getColor()) &&
                            (tmpGrid[i][j].getColor() == tmpGrid[i][j-1].getColor())
                    ){
                        if(index==0) {
                            blocksFound[0][0] = new Coordinates(i, j);
                            blocksFound[0][1] = new Coordinates(i - 1, j - 1);
                            blocksFound[0][2] = new Coordinates(i, j - 1);
                            blocksFound[0][3] = new Coordinates(i - 1, j);
                            index++;
                        }else{
                            auxCoor[0] = new Coordinates(i, j);
                            auxCoor[1] = new Coordinates(i - 1, j - 1);
                            auxCoor[2] = new Coordinates(i, j - 1);
                            auxCoor[3] = new Coordinates(i - 1, j);
                            if(!doesOverlap(blocksFound[0], auxCoor)){
                                blocksFound[index][0] = new Coordinates(i, j);
                                blocksFound[index][1] = new Coordinates(i - 1, j - 1);
                                blocksFound[index][2] = new Coordinates(i, j - 1);
                                blocksFound[index][3] = new Coordinates(i - 1, j);
                                index++;
                            }
                        }
                    }
                }
            }
            if(index>=2){
                return true;
            }
        }
        return false;
    }

    private boolean doesOverlap(Coordinates[] firstBlock, Coordinates[] newBlock){
        for(int i=0 ; i<firstBlock.length ; i++){
            for(int j=0 ; j<newBlock.length ; j++){
                if(firstBlock[i].equals(newBlock[j])){
                    return true;
                }
            }
        }
        return false;
    }

}
