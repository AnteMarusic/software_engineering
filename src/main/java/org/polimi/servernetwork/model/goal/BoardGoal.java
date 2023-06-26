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
    public int getScore(Card[][] grid) {
        int row = 0 , col = 0;
        int number = 0;
        int score = 0;
        while (row < ROW && col < COL) {
            if (!visited.contains(new Coordinates(row, col))) {
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
        visited.add(new Coordinates(row, col));
        if (row == 5) {
            if (!visited.contains(new Coordinates(row, col + 1)) &&
                    grid[row][col + 1].getColor() == grid[row][col].getColor()){

                return 1 + countSnake(row, col + 1);
            }
        }
        else if (col == 4) {
            if (!visited.contains(new Coordinates(row + 1, col)) &&
                    grid[row + 1][col].getColor() == grid[row][col].getColor()) {
                return 1 + countSnake(row + 1, col);
            }
            else return 0;
        }

        else if (col < 4 && row < 5) {
            if (!visited.contains(new Coordinates(row, col + 1)) &&
                    grid[row][col + 1].getColor() == grid[row][col].getColor() &&
                    !visited.contains(new Coordinates(row + 1, col)) &&
                    grid[row + 1][col].getColor() == grid[row][col].getColor()) {
                return 1 + countSnake(row, col + 1) + countSnake(row + 1, col);
            }
            if (!visited.contains(new Coordinates(row, col + 1)) &&
                    grid[row][col + 1].getColor() == grid[row][col].getColor()){

                return 1 + countSnake(row, col + 1);
            }
            if (!visited.contains(new Coordinates(row + 1, col)) &&
                    grid[row + 1][col].getColor() == grid[row][col].getColor()) {
                return 1 + countSnake(row + 1, col);
            }
        }

        return 0;
    }
}
