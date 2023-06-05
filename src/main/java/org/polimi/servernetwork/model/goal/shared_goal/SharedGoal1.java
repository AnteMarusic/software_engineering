package org.polimi.servernetwork.model.goal.shared_goal;

import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.util.ArrayList;

/**
 * concrete object that represents the 1st shared goal
 */
public class SharedGoal1 extends AbstractSharedGoal {
    public SharedGoal1(int numOfPlayer) {
        super(numOfPlayer);
    }

    /**
     * in order to achieve the points you must have 6 times a
     * couple made of cards of the same color.
     * Exhaustive check, complexity o((ROW/2) * COL)
     */
    @Override
    protected boolean achieved(Card[][] tmpGrid) {
        int count=0;
        Coordinates coor;
        Card tmpCard, righthandCard, bottomhandCard;
        ArrayList<Coordinates> bannedCoordinates = new ArrayList<Coordinates>(30);
        for(int i=0 ; i<ROW ; i++){
            for(int j=0 ; j<COL  ; j++){
                coor = new Coordinates(i,j);
                tmpCard = tmpGrid[i][j];
                if(i==5 && j==4)
                    break;
                if(tmpCard == null || bannedCoordinates.contains(coor))
                    continue;
                if(i==5){
                    righthandCard = tmpGrid[5][j+1];
                    if(righthandCard != null && checkAdjacentCard(tmpCard, righthandCard)){
                        count++;
                        banCoordinates(bannedCoordinates, coor, "right");
                    }
                }
                else if(j==4){
                    bottomhandCard=tmpGrid[i+1][4];
                    if(bottomhandCard != null && checkAdjacentCard(tmpCard, bottomhandCard)){
                        count++;
                        banCoordinates(bannedCoordinates, coor, "bottom");
                    }

                }
                else{
                    righthandCard = tmpGrid[i][j+1];
                    bottomhandCard=tmpGrid[i+1][j];
                    if(righthandCard != null && checkAdjacentCard(tmpCard, righthandCard)){
                        count++;
                        banCoordinates(bannedCoordinates, coor, "right");
                    }
                    else if(bottomhandCard != null && checkAdjacentCard(tmpCard, bottomhandCard)){
                        count++;
                        banCoordinates(bannedCoordinates, coor, "bottom");
                    }

                }
                if(count>=6){
                    return true;
            }

            }
        }
        return false;
    }
    private boolean checkAdjacentCard (Card startingCard, Card adjacentCard){
        Card.Color color= startingCard.getColor();
        if(color==adjacentCard.getColor())
            return true;
        return false;
    }

    private void banCoordinates(ArrayList<Coordinates> bannedCoordinates, Coordinates coor, String rightorbottom){
        if(rightorbottom.equals("right")){
            bannedCoordinates.add(coor);
            coor = new Coordinates(coor.getRow(),coor.getCol()+1);
            bannedCoordinates.add(coor);}
        else if(rightorbottom.equals("bottom")){
            bannedCoordinates.add(coor);
            coor = new Coordinates(coor.getRow()+1,coor.getCol());
            bannedCoordinates.add(coor);
        }
    }

}