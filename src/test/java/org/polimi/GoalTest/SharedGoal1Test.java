package org.polimi.GoalTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.polimi.server.model.Bookshelf;
import org.polimi.server.model.Card;

import static org.junit.jupiter.api.Assertions.*;

public class SharedGoal1Test {
    @Test
    void expectedFalse(){
        Card[][] toTest = new Card[6][5];
        toTest[0][0] = new Card(Card.Color.CYAN, Card.State.IN_BOOKSHELF);



    }

    @Test
    void expectedTrue(){

    }
}
