package org.polimi.servernetwork.model.goal.GoalTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;
import org.polimi.servernetwork.model.goal.PersonalGoal;
import static org.junit.jupiter.api.Assertions.*;

public class PersonalGoalTest {
    private PersonalGoal goal;
    @BeforeEach
    void setUp () {
        //index = 5 corresponds to the goal number 6 (because we start to count from 0)
        goal = new PersonalGoal(5);
    }
    @Test
    public void readFileAndPickRandomPersonalGoalTest() {
        Coordinates[] resultCoord = new Coordinates[6];
        Card.Color[] resultColor = new Card.Color[6];

        resultCoord[0] = new Coordinates(0,2);
        resultCoord[1] = new Coordinates(0,4);
        resultCoord[2] = new Coordinates(2,3);
        resultCoord[3] = new Coordinates(4,1);
        resultCoord[4] = new Coordinates(4,3);
        resultCoord[5] = new Coordinates(5,0);

        resultColor[0] = Card.Color.CYAN;
        resultColor[1] = Card.Color.GREEN;
        resultColor[2] = Card.Color.WHITE;
        resultColor[3] = Card.Color.ORANGE;
        resultColor[4] = Card.Color.BLUE;
        resultColor[5] = Card.Color.PINK;

        assertArrayEquals (resultCoord, goal.getCoordinates());
        assertArrayEquals(resultColor, goal.getColors());
    }
}
