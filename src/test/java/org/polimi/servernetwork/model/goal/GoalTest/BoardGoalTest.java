package org.polimi.servernetwork.model.goal.GoalTest;

import org.junit.jupiter.api.Test;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.goal.BoardGoal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardGoalTest {
    @Test
    void testCalucateBoardGoal() {
        BoardGoal boardGoal = new BoardGoal();
        Card[][] grid = new Card[6][5];
        grid[5][0]= new Card(Card.Color.WHITE, Card.State.IN_BOOKSHELF);
        grid[5][1]= new Card(Card.Color.WHITE, Card.State.IN_BOOKSHELF);
        grid[5][2]= new Card(Card.Color.WHITE, Card.State.IN_BOOKSHELF);
        grid[4][1]= new Card(Card.Color.WHITE, Card.State.IN_BOOKSHELF);
        //grid[3][1]= new Card(Card.Color.WHITE, Card.State.IN_BOOKSHELF);
        grid[3][2]= new Card(Card.Color.WHITE, Card.State.IN_BOOKSHELF);
        grid[2][2]= new Card(Card.Color.WHITE, Card.State.IN_BOOKSHELF);
        grid[4][2]= new Card(Card.Color.ORANGE, Card.State.IN_BOOKSHELF);
        grid[3][3]= new Card(Card.Color.ORANGE, Card.State.IN_BOOKSHELF);
        grid[4][3]= new Card(Card.Color.ORANGE, Card.State.IN_BOOKSHELF);
        grid[5][3]= new Card(Card.Color.ORANGE, Card.State.IN_BOOKSHELF);
        grid[3][4]= new Card(Card.Color.CYAN, Card.State.IN_BOOKSHELF);

        grid[4][4]= new Card(Card.Color.CYAN, Card.State.IN_BOOKSHELF);
        grid[5][4]= new Card(Card.Color.CYAN, Card.State.IN_BOOKSHELF);

        grid[2][0]= new Card(Card.Color.CYAN, Card.State.IN_BOOKSHELF);
        grid[3][0]= new Card(Card.Color.CYAN, Card.State.IN_BOOKSHELF);
        grid[4][0]= new Card(Card.Color.CYAN, Card.State.IN_BOOKSHELF);


        //int score = boardGoal.getScore(grid);
        int score = boardGoal.getScore(grid);
        assertEquals(10,score );
    }
}