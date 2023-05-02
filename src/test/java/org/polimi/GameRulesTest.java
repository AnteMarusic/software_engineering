package org.polimi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.polimi.GameRules.boardRowColInBound;
import static org.polimi.GameRules.getCorrectStartAndLength;

class GameRulesTest {

    @Test
    void getCorrectStartAndLengthTest() {
        int[] expectedResult = new int[2];

        //row 0
        expectedResult[0] = 3;
        expectedResult[1] = 0;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(0,2));

        expectedResult[0] = 3;
        expectedResult[1] = 1;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(0,3));

        expectedResult[0] = 3;
        expectedResult[1] = 2;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(0,4));


        //row 1
        expectedResult[0] = 3;
        expectedResult[1] = 2;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(1,2));

        expectedResult[0] = 3;
        expectedResult[1] = 2;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(1,3));

        expectedResult[0] = 3;
        expectedResult[1] = 3;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(1,4));


        //row 2
        expectedResult[0] = 3;
        expectedResult[1] = 3;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(2,2));

        expectedResult[0] = 2;
        expectedResult[1] = 5;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(2,3));

        expectedResult[0] = 2;
        expectedResult[1] = 5;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(2,4));


        //row 3
        expectedResult[0] = 2;
        expectedResult[1] = 6;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(3,2));

        expectedResult[0] = 2;
        expectedResult[1] = 7;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(3,3));

        expectedResult[0] = 1;
        expectedResult[1] = 8;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(3,4));


        //row 4
        expectedResult[0] = 1;
        expectedResult[1] = 7;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(4,2));

        expectedResult[0] = 1;
        expectedResult[1] = 7;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(4,3));

        expectedResult[0] = 0;
        expectedResult[1] = 9;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(4,4));


        //row 5
        expectedResult[0] = 1;
        expectedResult[1] = 6;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(5,2));

        expectedResult[0] = 0;
        expectedResult[1] = 7;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(5,3));

        expectedResult[0] = 0;
        expectedResult[1] = 8;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(5,4));

        //row 6
        expectedResult[0] = 3;
        expectedResult[1] = 3;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(6,2));

        expectedResult[0] = 2;
        expectedResult[1] = 5;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(6,3));

        expectedResult[0] = 2;
        expectedResult[1] = 5;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(6,4));


        //row 7
        expectedResult[0] = 4;
        expectedResult[1] = 2;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(7,2));

        expectedResult[0] = 4;
        expectedResult[1] = 2;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(7,3));

        expectedResult[0] = 3;
        expectedResult[1] = 3;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(7,4));

        //row 8
        expectedResult[0] = 5;
        expectedResult[1] = 0;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(8,2));

        expectedResult[0] = 5;
        expectedResult[1] = 1;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(8,3));

        expectedResult[0] = 4;
        expectedResult[1] = 2;
        assertArrayEquals(expectedResult, getCorrectStartAndLength(8,4));

    }

    @Test
    void boardRowColInBoundTest() {
        assertFalse(boardRowColInBound(9, 3, 3));
        assertFalse(boardRowColInBound(-1, 3, 3));
        assertTrue(boardRowColInBound(6, 6, 3));
        assertFalse(boardRowColInBound(6, 7, 3));
    }

    @Test
    void bookshelfColValid() {
    }
}