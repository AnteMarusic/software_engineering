package org.polimi.client;

import org.polimi.GameRules;
import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;
import org.polimi.server.model.Game;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Predicate;

public class ClientController {
    private CLI cli;
    private GameEnv gameEnv;
    private Scanner scanner;

    public ClientController(GameEnv gameEnv) {
        this.cli = new CLI();
        Scanner scanner = new Scanner(System.in);
        gameEnv = null;
    }

    public void setGameEnv(GameEnv gameEnvironment) {
        this.gameEnv = gameEnvironment;
    }

    /**
     * gets from std input one to three coordinates
     * ensures that the arrayList containing these coordinates is dim one to three and contains valid coordinates
     * ensures that the dimension of the arrayList isn't greater than maxInsertable in bookshelf
     * ensures that the cards are picked in a line from the board
     */
    public void chooseCards() {
        //since the cards have to be picked in a line, each card picked has to have one constant coordinate
        int counter = 0;
        Coordinates c1 = null, c2 = null, c3 = null;
        int row, col;
        int numberToPick;
        int maxInsertable = gameEnv.getMaxInsertable();
        boolean flag;

        ArrayList<Coordinates> chosenCards = new ArrayList<>(maxInsertable);

        do {
            System.out.println("how many cards do you want to pick?");
            numberToPick = scanner.nextInt();
            if (numberToPick >= 3) {
                System.out.println("you can pick at most three cards");
            }
            if (numberToPick < 0) {
                System.out.println("you have to pick at least one card");
            }
        } while (numberToPick >= 3 || numberToPick < 0);

        while (counter < numberToPick) {
            switch (counter) {
                case 0 -> {
                    do {
                        flag = false;

                        System.out.println("Type row number (0 to 8)");
                        row = scanner.nextInt();
                        System.out.println("Type col number (0 to 8)");
                        col = scanner.nextInt();
                        if (!GameRules.boardRowColInBound(row, col, gameEnv.getNumOfPlayers())) {
                            System.out.println("coordinates not in bound");
                        } else {
                            c1 = new Coordinates(row, col);
                            if (gameEnv.boardSeeCardAtCoordinates(new Coordinates(row, col)) == null) {
                                System.out.println("the card has already been taken! please choose another one");
                            } else if (gameEnv.isCardPickable(c1)) {
                                System.out.println("ok");
                                chosenCards.add(c1);
                                flag = true;
                            } else {
                                System.out.println("this card is not pickable yet");
                            }
                        }
                    } while (!flag);
                    counter++;
                }
                case 1 -> {
                    flag = false;
                    do {
                        System.out.println("Choose your next Card");
                        System.out.println("Type row number (0 to 8)");
                        row = scanner.nextInt();
                        System.out.println("Type col number (0 to 8)");
                        col = scanner.nextInt();
                        if (!GameRules.boardRowColInBound(row, col, gameEnv.getNumOfPlayers())) {
                            System.out.println("coordinates not in bound");
                        } else {
                            c2 = new Coordinates(row, col);
                            if (gameEnv.boardSeeCardAtCoordinates(new Coordinates(row, col)) == null) {
                                System.out.println("the card has already been taken! please choose another one");
                            } else if (gameEnv.isCardPickable(c2)) {
                                if (GameRules.areCoordinatesAligned(c1, c2)) {
                                    System.out.println("ok");
                                    chosenCards.add(c2);
                                    flag = true;
                                } else {
                                    System.out.println("this card isn't aligned with the first one");
                                }
                            } else {
                                System.out.println("this card is not pickable yet");
                            }
                        }
                    } while (!flag);
                    counter++;
                }
                case 2 -> {
                    flag = false;
                    do {
                        System.out.println("Choose your next Card");
                        System.out.println("Type row number (0 to 8)");
                        row = scanner.nextInt();
                        System.out.println("Type col number (0 to 8)");
                        col = scanner.nextInt();
                        if (!GameRules.boardRowColInBound(row, col, gameEnv.getNumOfPlayers())) {
                            System.out.println("coordinates not in bound");
                        } else {
                            c3 = new Coordinates(row, col);
                            if (gameEnv.boardSeeCardAtCoordinates(new Coordinates(row, col)) == null) {
                                System.out.println("the card has already been taken! please choose another one");
                            } else if (gameEnv.isCardPickable(c3)) {
                                if (GameRules.areCoordinatesAligned(c1, c2, c3)) {
                                    System.out.println("ok");
                                    chosenCards.add(c3);
                                    flag = true;
                                } else {
                                    System.out.println("this card isn't aligned with the first one");
                                }
                            } else {
                                System.out.println("this card is not pickable yet");
                            }
                        }
                    } while (!flag);
                    counter++;
                }
            }
        }
    }

    public void orderChosenCards(ArrayList<Coordinates> toOrder) {
        ArrayList<Coordinates> temp = new ArrayList<>(toOrder.size());
        int position;
        int i = 0;

        while (i < toOrder.size()) {
            System.out.println("Where do you want to put the card in position " + i +" ?\n");
            position = scanner.nextInt();
            if (temp.get(position) != null) {
                if (position >= 0 && position < toOrder.size()) {
                    temp.add(toOrder.get(i));
                    i++;
                }
                else {
                    System.out.println(position + "is not in the interval [0, toInsert.length]...\n Please choose again\n");
                }
            }
            else
                System.out.println("There's already a card in position "+position+", choose another...");
        }
    }
}