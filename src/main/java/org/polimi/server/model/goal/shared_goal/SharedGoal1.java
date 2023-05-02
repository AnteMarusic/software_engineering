package org.polimi.server.model.goal.shared_goal;

import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.util.ArrayList;

import static org.polimi.GameRules.bookshelfRowColIsInBound;

/**
 * concrete object that represents the 1st shared goal
 */
public class SharedGoal1 extends AbstractSharedGoal {
    public SharedGoal1(int numOfPlayer) {
        super(numOfPlayer);
    }

    /**
     * in order to achieve the points you must have 6 times a vertical
     * couple made of cards of the same color.
     * Exhaustive check, complexity o((ROW/2) * COL)
     */
    @Override
    protected boolean achieved(Card[][] tmpGrid) {
        int count=0;
        ArrayList<ArrayList<Coordinates>> list = new ArrayList<ArrayList<Coordinates>>();
        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
        Coordinates coor;
        for(int i=0 ; i<ROW ; i++){
            for(int j=0 ; j<COL ; j++){
                coor = new Coordinates(i,j);
                if (explore(tmpGrid, coor)!=null){
                    result = explore(tmpGrid, coor);
                }
                if(distanceIsValid(tmpGrid, result, list)){
                    list.add(result);
                    count++;
                }
            }
            if(count>=6){
                return true;
            }
        }
        return false;
    }

    private ArrayList<Coordinates> explore (Card[][] grid, Coordinates startCoor){
        if(grid[startCoor.getRow()][startCoor.getCol()]!=null){
            Coordinates tmpCoor = new Coordinates(startCoor.getRow()-1, startCoor.getCol());
            if(bookshelfRowColIsInBound(tmpCoor)){
                if(grid[tmpCoor.getRow()][tmpCoor.getCol()]!=null) {
                    if (grid[startCoor.getRow()][startCoor.getCol()].getColor() == grid[tmpCoor.getRow()][tmpCoor.getCol()].getColor()) {
                        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
                        result.add(startCoor);
                        result.add(tmpCoor);
                        return result;
                    }
                }
            }
            tmpCoor = new Coordinates(startCoor.getRow()+1, startCoor.getCol());
            if(bookshelfRowColIsInBound(tmpCoor)){
                if(grid[tmpCoor.getRow()][tmpCoor.getCol()]!=null) {
                    if (grid[startCoor.getRow()][startCoor.getCol()].getColor() == grid[tmpCoor.getRow()][tmpCoor.getCol()].getColor()) {
                        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
                        result.add(startCoor);
                        result.add(tmpCoor);
                        return result;
                    }
                }
            }
            tmpCoor = new Coordinates(startCoor.getRow(), startCoor.getCol()+1);
            if(bookshelfRowColIsInBound(tmpCoor)){
                if(grid[tmpCoor.getRow()][tmpCoor.getCol()]!=null) {
                    if (grid[startCoor.getRow()][startCoor.getCol()].getColor() == grid[tmpCoor.getRow()][tmpCoor.getCol()].getColor()) {
                        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
                        result.add(startCoor);
                        result.add(tmpCoor);
                        return result;
                    }
                }
            }
            tmpCoor = new Coordinates(startCoor.getRow(), startCoor.getCol()-1);
            if(bookshelfRowColIsInBound(tmpCoor)){
                if(grid[tmpCoor.getRow()][tmpCoor.getCol()]!=null) {
                    if (grid[startCoor.getRow()][startCoor.getCol()].getColor() == grid[tmpCoor.getRow()][tmpCoor.getCol()].getColor()) {
                        ArrayList<Coordinates> result = new ArrayList<Coordinates>();
                        result.add(startCoor);
                        result.add(tmpCoor);
                        return result;
                    }
                }
            }
        }else{
            return null;
        }
        return null;
    }

    private boolean distanceIsValid (Card[][] grid, ArrayList<Coordinates> result, ArrayList<ArrayList<Coordinates>> list){
        for(int i=0; i<list.size() ; i++){
            for(int j=0 ; j<2 ; j++){
                if(     ((result.get(j).getRow()+1==list.get(i).get(0).getRow())&&(result.get(j).getCol()==list.get(i).get(0).getCol())) ||
                        ((result.get(j).getRow()==list.get(i).get(0).getRow())&&(result.get(j).getCol()-1==list.get(i).get(0).getCol())) ||
                        ((result.get(j).getRow()==list.get(i).get(0).getRow())&&(result.get(j).getCol()+1==list.get(i).get(0).getCol())) ||
                        ((result.get(j).getRow()-1==list.get(i).get(0).getRow())&&(result.get(j).getCol()==list.get(i).get(0).getCol())) ||
                        ((result.get(j).getRow()+1==list.get(i).get(1).getRow())&&(result.get(j).getCol()==list.get(i).get(1).getCol())) ||
                        ((result.get(j).getRow()==list.get(i).get(1).getRow())&&(result.get(j).getCol()-1==list.get(i).get(1).getCol())) ||
                        ((result.get(j).getRow()==list.get(i).get(1).getRow())&&(result.get(j).getCol()+1==list.get(i).get(1).getCol())) ||
                        ((result.get(j).getRow()-1==list.get(i).get(1).getRow())&&(result.get(j).getCol()==list.get(i).get(1).getCol()))
                ){
                    return false;
                }
            }
        }
        return true;
    }

}
