package org.polimi.servernetwork.model.goal;

import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BoardGoal implements Goal, Serializable {
    private final static int ROW = 6;
    private final static int COL = 5;
    private Card[][] grid;
    private List<Coordinates> visited;

    public BoardGoal(){
        this.grid = new Card[ROW][COL];
        this. visited = new ArrayList<>();
    }

    /**
     * @requires grid is a copy of the grid to check
     */
    @Override
    public int getScore(Card[][] grid2) {
        this.grid=grid2;
        visited.clear();
        int row = 0 , col = 0;
        int number = 0;
        int score = 0;
        while (row < ROW && col < COL) {
            number=0;
            if (grid[row][col]!=null && !visited.contains(new Coordinates(row, col)) ) {
                visited.add(new Coordinates(row, col));
                number = 1 + countSnake(row, col);
            }
            if (number == 3)
                score += 2;
            if (number == 4)
                score += 3;
            if (number == 5)
                score += 5;
            if (number >= 6)
                score += 8;

            if (col == 4) {
                row += 1;
            }
            col = (col + 1) % COL;
        }
        return score;
    }

    private int countSnake (int row, int col) {
        if (row == 0 && col == 0){ // giù, dx
            if (check_down(row, col) && check_right(row, col)) {    // giù e dx
                visited.add(new Coordinates(row+1, col));
                visited.add(new Coordinates(row, col+1));
                return 2 + countSnake(row, col + 1) + countSnake(row + 1, col);
            }
            if (check_right(row,col)){     // solo dx
                visited.add(new Coordinates(row, col+1));
                return 1 + countSnake(row, col + 1);
            }
            if (check_down(row,col)){   // solo giù
                visited.add(new Coordinates(row+1, col));
                return 1 + countSnake(row + 1, col);
            }
        }
        if (row == 0 && col == 4){ // giù, sx
            if( check_down(row,col) && check_left(row,col)){  // giù e sx
                visited.add(new Coordinates(row, col-1));
                visited.add(new Coordinates(row+1, col));
                return 2 + countSnake(row, col-1) + countSnake(row + 1, col);
            }
            if (check_down(row,col)) {  // solo giù
                visited.add(new Coordinates(row+1, col));
                return 1 + countSnake(row + 1, col);
            }
            if (check_left(row,col)) {  // solo sx
                visited.add(new Coordinates(row, col-1));
                return 1 + countSnake(row, col - 1);
            }
        }
        if (row == 5 && col == 0){ // su, dx
            if (check_up(row,col) && check_right(row,col)) {    // su e dx
                visited.add(new Coordinates(row, col+1));
                visited.add(new Coordinates(row-1, col));
                return 2 + countSnake(row, col + 1) + countSnake(row - 1, col);
            }
            if (check_right(row,col)){     // solo dx
                visited.add(new Coordinates(row, col+1));
                return 1 + countSnake(row, col + 1);
            }
            if (check_up(row,col)){   // solo giù
                visited.add(new Coordinates(row-1, col));
                return 1 + countSnake(row - 1, col);
            }
        }
        if (row == 5 && col == 4){ // su, sx
            if(check_up(row,col) && check_left(row,col)){  // su e sx
                visited.add(new Coordinates(row, col-1));
                visited.add(new Coordinates(row-1, col));
                return 2 + countSnake(row, col-1) + countSnake(row - 1, col);
            }
            if (check_up(row,col)) {  // solo su
                visited.add(new Coordinates(row-1, col));
                return 1 + countSnake(row - 1, col);
            }
            if (check_left(row,col)) {  // solo sx
                visited.add(new Coordinates(row, col-1));
                return 1 + countSnake(row, col - 1);
            }
        }
        if (row == 0 && col > 0 && col < 4){ // giù, dx, sx
            if (check_down(row,col) && check_right(row,col) && check_left(row,col)){    // giù, dx che sx
                visited.add(new Coordinates(row, col-1));
                visited.add(new Coordinates(row, col+1));
                visited.add(new Coordinates(row+1, col));
                return 3 + countSnake(row, col-1) + countSnake(row, col + 1) + countSnake(row + 1, col);
            }
            if (check_down(row,col) && check_right(row,col)) {    // giù e dx
                visited.add(new Coordinates(row, col+1));
                visited.add(new Coordinates(row+1, col));
                return 2 + countSnake(row, col + 1) + countSnake(row + 1, col);
            }
            if(check_down(row,col) && check_left(row,col)){  // giù e sx
                visited.add(new Coordinates(row, col-1));
                visited.add(new Coordinates(row+1, col));
                return 2 + countSnake(row, col-1) + countSnake(row + 1, col);
            }
            if (check_left(row,col) && check_right(row,col)) { // controllo dx e sx
                visited.add(new Coordinates(row, col-1));
                visited.add(new Coordinates(row, col+1));
                return 2 + countSnake(row, col-1) + countSnake(row, col + 1);
            }
            if (check_down(row,col)){   // solo giù
                visited.add(new Coordinates(row+1, col));
                return 1 + countSnake(row + 1, col);
            }
            if (check_left(row,col)) {  // solo sx
                visited.add(new Coordinates(row, col-1));
                return 1 + countSnake(row, col - 1);
            }
            if (check_right(row,col)) {     // solo dx
                visited.add(new Coordinates(row, col+1));
                return 1 + countSnake(row, col + 1);
            }
        }
        if (row == 5 && col > 0 && col < 4){ // su, dx, sx
            if (check_up(row,col) && check_right(row,col) && check_left(row,col)){    // su dx che sx
                visited.add(new Coordinates(row, col-1));
                visited.add(new Coordinates(row, col+1));
                visited.add(new Coordinates(row-1, col));
                return 3 + countSnake(row, col-1) + countSnake(row, col + 1) + countSnake(row - 1, col);
            }
            if (check_up(row,col) && check_right(row,col)) {    // su e dx
                visited.add(new Coordinates(row, col+1));
                visited.add(new Coordinates(row-1, col));
                return 2 + countSnake(row, col + 1) + countSnake(row - 1, col);
            }
            if(check_up(row,col) && check_left(row,col)){  // su e sx
                visited.add(new Coordinates(row, col-1));
                visited.add(new Coordinates(row-1, col));
                return 2 + countSnake(row, col-1) + countSnake(row - 1, col);
            }
            if (check_left(row,col) && check_right(row,col)) { // controllo dx e sx
                visited.add(new Coordinates(row, col-1));
                visited.add(new Coordinates(row, col+1));
                return 2 + countSnake(row, col-1) + countSnake(row, col + 1);
            }
            if (check_up(row,col)) {  // solo su
                visited.add(new Coordinates(row-1, col));
                return 1 + countSnake(row - 1, col);
            }
            if (check_left(row,col)) {  // solo sx
                visited.add(new Coordinates(row, col-1));
                return 1 + countSnake(row, col - 1);
            }
            if (check_right(row,col)){     // solo dx
                visited.add(new Coordinates(row, col+1));
                return 1 + countSnake(row, col + 1);
            }
        }
        if (col == 0 && row > 0 && row < 5){ // giù, su, dx
            if( check_up(row,col) && check_down(row,col) && check_right(row,col)){ // giù, su, dx
                visited.add(new Coordinates(row-1, col));
                visited.add(new Coordinates(row+1, col));
                visited.add(new Coordinates(row, col+1));
                return 3 + countSnake(row-1, col) + countSnake( row+1, col) + countSnake( row, col+1);
            }
            if (check_down(row,col) && check_up(row,col)) {    // giù e su
                visited.add(new Coordinates(row-1, col));
                visited.add(new Coordinates(row+1, col));
                return 2 + countSnake(row-1, col) + countSnake(row + 1, col);
            }
            if (check_down(row,col) && check_right(row,col)) {    // giù e dx
                visited.add(new Coordinates(row+1, col));
                visited.add(new Coordinates(row, col+1));
                return 2 + countSnake(row, col + 1) + countSnake(row + 1, col);
            }
            if (check_up(row,col) && check_right(row,col)) {    // su e dx
                visited.add(new Coordinates(row-1, col));
                visited.add(new Coordinates(row, col+1));
                return 2 + countSnake(row, col + 1) + countSnake(row - 1, col);
            }
            if (check_up(row,col)) {  // solo su
                visited.add(new Coordinates(row-1, col));
                return 1 + countSnake(row - 1, col);
            }
            if (check_down(row,col)){   // solo giù
                visited.add(new Coordinates(row+1, col));
                return 1 + countSnake(row + 1, col);
            }
            if (check_right(row,col)){     // solo dx
                visited.add(new Coordinates(row, col+1));
                return 1 + countSnake(row, col + 1);
            }
        }
        if (col == 4 && row > 0 && row < 5){ // giù, su, sx
            if( check_up(row,col) && check_down(row,col) && check_left(row,col)){ // giù, su, sx
                visited.add(new Coordinates(row-1, col));
                visited.add(new Coordinates(row+1, col));
                visited.add(new Coordinates(row, col-1));
                return 3 + countSnake(row-1, col) + countSnake( row+1, col) + countSnake( row, col-1);
            }
            if (check_down(row,col) && check_up(row,col)) {    // giù e su
                visited.add(new Coordinates(row-1, col));
                visited.add(new Coordinates(row+1, col));
                return 2 + countSnake(row-1, col) + countSnake(row + 1, col);
            }
            if(check_down(row,col) && check_left(row,col)){  // giù e sx
                visited.add(new Coordinates(row+1, col));
                visited.add(new Coordinates(row, col-1));
                return 2 + countSnake(row, col-1) + countSnake(row + 1, col);
            }
            if(check_up(row,col) && check_left(row,col)){  // su e sx
                visited.add(new Coordinates(row-1, col));
                visited.add(new Coordinates(row, col-1));
                return 2 + countSnake(row, col-1) + countSnake(row - 1, col);
            }
            if (check_up(row,col)) {  // solo su
                visited.add(new Coordinates(row-1, col));
                return 1 + countSnake(row - 1, col);
            }
            if (check_down(row,col)){   // solo giù
                visited.add(new Coordinates(row+1, col));
                return 1 + countSnake(row + 1, col);
            }
            if (check_left(row,col)){     // solo sx
                visited.add(new Coordinates(row, col-1));
                return 1 + countSnake(row, col - 1);
            }
        }
        if (row > 0 && row <5 && col>0 && col<4){ // giù, su, sx, dx
            if(check_up(row,col) && check_down(row,col) && check_right(row,col) && check_left(row,col)){ // giù, su, sx, dx
                visited.add(new Coordinates(row+1, col));
                visited.add(new Coordinates(row-1, col));
                visited.add(new Coordinates(row, col+1));
                visited.add(new Coordinates(row, col-1));
                return 4 + countSnake(row-1, col) + countSnake( row+1, col) + countSnake( row, col-1) + countSnake(row, col+1);
            }
            if( check_up(row,col) && check_down(row,col) && check_left(row,col)){ // giù, su, sx
                visited.add(new Coordinates(row+1, col));
                visited.add(new Coordinates(row-1, col));
                visited.add(new Coordinates(row, col-1));
                return 3 + countSnake(row-1, col) + countSnake( row+1, col) + countSnake( row, col-1);
            }
            if( check_up(row,col) && check_down(row,col) && check_right(row,col)){ // giù, su, dx
                visited.add(new Coordinates(row+1, col));
                visited.add(new Coordinates(row-1, col));
                visited.add(new Coordinates(row, col+1));
                return 3 + countSnake(row-1, col) + countSnake( row+1, col) + countSnake( row, col+1);
            }
            if (check_up(row,col) && check_right(row,col) && check_left(row,col)){    // su dx  sx
                visited.add(new Coordinates(row-1, col));
                visited.add(new Coordinates(row, col+1));
                visited.add(new Coordinates(row, col-1));
                return 3 + countSnake(row, col-1) + countSnake(row, col + 1) + countSnake(row - 1, col);
            }
            if (check_down(row,col) && check_right(row,col) && check_left(row,col)){    // giù, dx che sx
                visited.add(new Coordinates(row+1, col));
                visited.add(new Coordinates(row, col+1));
                visited.add(new Coordinates(row, col-1));
                return 3 + countSnake(row, col-1) + countSnake(row, col + 1) + countSnake(row + 1, col);
            }
            if (check_down(row,col) && check_up(row,col)) {    // giù e su
                visited.add(new Coordinates(row+1, col));
                visited.add(new Coordinates(row-1, col));
                return 2 + countSnake(row-1, col) + countSnake(row + 1, col);
            }
            if(check_down(row,col) && check_left(row,col)){  // giù e sx
                visited.add(new Coordinates(row+1, col));
                visited.add(new Coordinates(row, col-1));
                return 2 + countSnake(row, col-1) + countSnake(row + 1, col);
            }
            if(check_up(row,col) && check_left(row,col)){  // su e sx
                visited.add(new Coordinates(row-1, col));
                visited.add(new Coordinates(row, col-1));
                return 2 + countSnake(row, col-1) + countSnake(row - 1, col);
            }
            if (check_left(row,col) && check_right(row,col)) { // dx e sx
                visited.add(new Coordinates(row, col+1));
                visited.add(new Coordinates(row, col-1));
                return 2 + countSnake(row, col-1) + countSnake(row, col + 1);
            }
            if (check_up(row,col) && check_right(row,col)) {    // su e dx
                visited.add(new Coordinates(row-1, col));
                visited.add(new Coordinates(row, col+1));
                return 2 + countSnake(row, col + 1) + countSnake(row - 1, col);
            }
            if (check_down(row,col) && check_right(row,col)) {    // giù e dx
                visited.add(new Coordinates(row+1, col));
                visited.add(new Coordinates(row, col+1));
                return 2 + countSnake(row, col + 1) + countSnake(row + 1, col);
            }
            if (check_up(row,col)) {  // solo su
                visited.add(new Coordinates(row-1, col));
                return 1 + countSnake(row - 1, col);
            }
            if (check_down(row,col)){   // solo giù
                visited.add(new Coordinates(row+1, col));
                return 1 + countSnake(row + 1, col);
            }
            if (check_left(row,col)){     // solo sx
                visited.add(new Coordinates(row, col-1));
                return 1 + countSnake(row, col - 1);
            }
            if (check_right(row,col)){     // solo dx
                visited.add(new Coordinates(row, col+1));
                return 1 + countSnake(row, col + 1);
            }
        }

        return 0;
    }


    private boolean check_up (int row, int col){
        if(!visited.contains(new Coordinates(row - 1, col)) && grid[row-1][col]!= null &&
                grid[row - 1][col].getColor() == grid[row][col].getColor())
            return true;
        else
            return false;
    }
    private boolean check_down (int row, int col){
        if(!visited.contains(new Coordinates(row + 1, col)) && grid[row+1][col]!= null &&
                grid[row + 1][col].getColor() == grid[row][col].getColor())
            return true;
        else
            return false;
    }
    private boolean check_left (int row, int col){
        if(!visited.contains(new Coordinates(row, col - 1)) && grid[row][col - 1] != null &&
                grid[row][col - 1].getColor() == grid[row][col].getColor())
            return true;
        else
            return false;
    }
    private boolean check_right (int row, int col){
        if(!visited.contains(new Coordinates(row, col + 1)) && grid[row][col + 1] != null &&
                grid[row][col + 1].getColor() == grid[row][col].getColor())
            return true;
        else
            return false;
    }
}
