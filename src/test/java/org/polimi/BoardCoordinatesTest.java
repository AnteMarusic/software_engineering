package org.polimi;

import org.junit.jupiter.api.Test;
import org.polimi.server.model.coordinates.BoardCoordinates;

import static org.junit.jupiter.api.Assertions.*;

class BoardCoordinatesTest {

    @Test
    void outOfBounds() {
        BoardCoordinates.setNumOfPlayers(4);
        BoardCoordinates b = new BoardCoordinates(-1, 100);
        assertFalse(b.isValid());
    }

    @Test
    void firstRowInBoundButAlwaysFalse() {
        BoardCoordinates.setNumOfPlayers(4);
        BoardCoordinates b = new BoardCoordinates(0, 0);
        assertFalse(b.isValid());
    }

    @Test
    void firstRowInBoundButFalse2Player() {
        BoardCoordinates.setNumOfPlayers(2);
        BoardCoordinates b = new BoardCoordinates(0, 3);
        assertFalse(b.isValid());
    }

    @Test
    void firstRowInBoundButFalse3Player() {
        BoardCoordinates.setNumOfPlayers(3);
        BoardCoordinates b = new BoardCoordinates(0, 4);
        assertFalse(b.isValid());
    }

    @Test
    void secondRowInBoundButAlwaysFalse() {
        BoardCoordinates.setNumOfPlayers(4);
        BoardCoordinates b = new BoardCoordinates(1, 0);
        assertFalse(b.isValid());
    }

    @Test
    void secondRowInBoundButFalse2Player() {
        BoardCoordinates.setNumOfPlayers(2);
        BoardCoordinates b = new BoardCoordinates(1, 3);
        assertFalse(b.isValid());
    }

    @Test
    void secondRowInBoundButFalse3Player() {
        BoardCoordinates.setNumOfPlayers(3);
        BoardCoordinates b = new BoardCoordinates(1, 4);
        assertFalse(b.isValid());
    }

    @Test
    void secondRowInBoundButFalse4Player() {
        BoardCoordinates.setNumOfPlayers(4);
        BoardCoordinates b = new BoardCoordinates(1, 4);
        assertFalse(b.isValid());
    }


}