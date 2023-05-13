package org.polimi.server.model.goal.shared_goal;

import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.util.ArrayList;

/**
 * concrete object that represents 3rd shared goal
 */
public class SharedGoal3Kala extends AbstractSharedGoal{

    public SharedGoal3Kala(int numOfPlayer) {
        super(numOfPlayer);
    }
    @Override
    protected boolean achieved(Card[][] tmpGrid) {
        int count=0;
        ArrayList<Coordinates> bannedCoordinates = new ArrayList<Coordinates>(21);
        Coordinates coor;
        for(int i=0 ; i<ROW ; i++){
            for(int j=0 ; j<COL ; j++) {
                coor = new Coordinates(i,j);
                if (tmpGrid[i][j]==null || !validCoordinates(i, j) || bannedCoordinates.contains(coor))
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
                        System.out.println(coor + " column");
                    }
                }
                else if(j <= 1){
                    if(checkFourinaRow(tmpGrid, coor, "row")){
                        banCoordinates(bannedCoordinates, coor, "row");
                        count++;
                        System.out.println(coor+" row");
                    }
                }
                if(count >= 4)
                    return true;
            }
        }

        //second iteration, reading now rows from right to left
        bannedCoordinates = new ArrayList<Coordinates>(21);
        count = 0;
        for(int i=0 ; i<ROW ; i++){
            for(int j=COL-1 ; j>=0 ; j--) {
                coor = new Coordinates(i,j);
                if (tmpGrid[i][j]==null || !validCoordinates2(i, j) || bannedCoordinates.contains(coor))
                    continue;
                if(i <= 2 && j >= 3){
                    if(checkRowRightToLeft(tmpGrid, coor)){
                        banCoordinatesRowRightToLeft(bannedCoordinates, coor);
                        count++;
                        System.out.println(coor+" row 2");
                    }
                    else if(checkFourinaRow(tmpGrid, coor, "column")){
                        banCoordinates(bannedCoordinates, coor, "column");
                        count++;
                        System.out.println(coor + " column 2");
                    }
                }
                else if(i <= 2){
                    if(checkFourinaRow(tmpGrid, coor, "column")){
                        banCoordinates(bannedCoordinates, coor, "column");
                        count++;
                        System.out.println(coor + " column 2");
                    }
                }
                else if(j <= 1){
                    if(checkRowRightToLeft(tmpGrid, coor)){
                        banCoordinatesRowRightToLeft(bannedCoordinates, coor);
                        count++;
                        System.out.println(coor+" row 2");
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
    private boolean checkRowRightToLeft(Card[][] array, Coordinates startingCoords){
        int row=startingCoords.getRow(), col= startingCoords.getCol();
        if(array[row][col]!=null && array[row][col-1]!=null && array[row][col-2]!=null && array[row][col-3]!=null){
            return array[row][col].getColor() == array[row][col-1].getColor() &&
                    array[row][col].getColor() == array[row][col-2].getColor() &&
                    array[row][col].getColor() == array[row][col-3].getColor();
            }

        return false;
    }
    private boolean validCoordinates2(int i, int j){
        return i >= 0 && i <= 2 || j >= 3 && j <= 4;
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
    private void banCoordinatesRowRightToLeft(ArrayList<Coordinates> bannedCoordinates, Coordinates startingCoords){
        int row = startingCoords.getRow(), col= startingCoords.getCol();
        Coordinates coor1, coor2, coor3;
            coor1= new Coordinates(row, col-1);
            coor2= new Coordinates(row, col-2);
            coor3= new Coordinates(row, col-3);
            bannedCoordinates.add(startingCoords);
            bannedCoordinates.add(coor1);
            bannedCoordinates.add(coor2);
            bannedCoordinates.add(coor3);
    }

}

