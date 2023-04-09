package org.polimi;

import org.polimi.personal_goal.PersonalGoal;

public class Main {
    public static void main(String[] args) {
        BagOfCards bag = new BagOfCards();
        Board board = new Board(4, bag);
        board.print();
        System.out.println("\n" + board.seeCardAtCoordinates(new Coordinates(3,1)) + "\n");
        board.getCardAtCoordinates(new Coordinates(3,1));
        board.print();
    }
}