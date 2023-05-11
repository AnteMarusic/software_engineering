package org.polimi.server.model.goal.shared_goal;

import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.util.ArrayList;

/**
 * concrete object that represents 3rd shared goal
 */
public class SharedGoal3 extends AbstractSharedGoal{

    public SharedGoal3(int numOfPlayer) {
        super(numOfPlayer);
    }

    /**
     * in order to achieve the points you must have 4 columns made of
     * 4 cards of the same color
     * Exhaustive check, complexity O((ROW-3) * COL)
     */
    @Override
    protected boolean achieved(Card[][] tmpGrid) {
        int count=0;
        ArrayList<Coordinates> bannedCoordinates = new ArrayList<Coordinates>(21);
        Coordinates coor;
        for(int i=0 ; i<ROW ; i++){
            for(int j=0 ; j<COL ; j++) {
                coor = new Coordinates(i,j);
                if (!validCoordinates(i, j) || bannedCoordinates.contains(coor))
                    continue;
                if(i <= 2 && j <= 1){
                    if(checkFourinaRow(tmpGrid, coor, "row")){
                        banCoordinates(bannedCoordinates, coor, "row");
                        count++;
                        System.out.println(coor+" row");
                    }
                    else if(checkFourinaRow(tmpGrid, coor, "column")){
                        banCoordinates(bannedCoordinates, coor, "column");
                        count++;
                        System.out.println(coor + " column");
                    }
                }
                else if(i <= 2){
                    if(checkFourinaRow(tmpGrid, coor, "column")){
                        banCoordinates(bannedCoordinates, coor, "column");
                        count++;
                    }
                }
                else if(j <= 1){
                    if(checkFourinaRow(tmpGrid, coor, "row")){
                        banCoordinates(bannedCoordinates, coor, "row");
                        count++;
                    }
                }
                if(count >= 4)
                    return true;
            }
        }
        return false;
    }
    private boolean validCoordinates(int i, int j){
        return i >= 0 && i <= 2 || j >= 0 && j <= 1;
    }
    private boolean checkFourinaRow(Card[][] array, Coordinates startingCoords, String constantIndex){
        int row=startingCoords.getRow(), col= startingCoords.getCol();
        if(constantIndex.equals("row")){
            if(array[row][col]!=null && array[row][col+1]!=null && array[row][col+2]!=null && array[row][col+3]!=null){
                return array[row][col].getColor() == array[row][col+1].getColor() &&
                        array[row][col].getColor() == array[row][col+2].getColor() &&
                        array[row][col].getColor() == array[row][col+3].getColor();
            }
        }
        else if(constantIndex.equals("column")){
            if(array[row][col]!=null && array[row+1][col]!=null && array[row+2][col]!=null && array[row+3][col]!=null){
                return array[row][col].getColor() == array[row+1][col].getColor() &&
                        array[row][col].getColor() == array[row+2][col].getColor() &&
                        array[row][col].getColor() == array[row+3][col].getColor();
            }
        }
        return false;
    }
    private void banCoordinates(ArrayList<Coordinates> bannedCoordinates, Coordinates startingCoords, String constantIndex){
        int row = startingCoords.getRow(), col= startingCoords.getCol();
        Coordinates coor1, coor2, coor3;
        if(constantIndex.equals("row")){
            coor1= new Coordinates(row, col+1);
            coor2= new Coordinates(row, col+2);
            coor3= new Coordinates(row, col+3);
            bannedCoordinates.add(startingCoords);
            bannedCoordinates.add(coor1);
            bannedCoordinates.add(coor2);
            bannedCoordinates.add(coor3);
        }
        else if(constantIndex.equals("column")){
            coor1= new Coordinates(row+1, col);
            coor2= new Coordinates(row+2, col);
            coor3= new Coordinates(row+3, col);
            bannedCoordinates.add(startingCoords);
            bannedCoordinates.add(coor1);
            bannedCoordinates.add(coor2);
            bannedCoordinates.add(coor3);
        }
    }
}

