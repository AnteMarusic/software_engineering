package org.polimi;

import java.util.HashMap;

public class Board {
    private final BagOfCards bag;
    private final HashMap<Coordinates, Card> board;
    private final int numOfPlayers;

    //board constructor
    public Board(int numOfPlayers, BagOfCards bag) {
        this.numOfPlayers = numOfPlayers;
        this.bag = bag;
        this.board = new HashMap<Coordinates, Card>();
        this.fill();
        this.updatePickablesAtFirst();
    }

    //fills the board with new cards taken from the bag
    public void fill() {
        int start, length;
        for (int i = 0; i < 9; i++) {
            int[] arr= getCorrectStartandLength(i);
            start = arr[0];
            length = arr[1];
            for (int j = 0; j < 9; j++) {
                Coordinates AUXkey = new Coordinates(i, j);
                if (j >= start && j < start + length) {
                    this.board.put(AUXkey, bag.collectCard());
                    this.board.get(AUXkey).setState(Card.State.NOT_PICKABLE); // sets each card's state to NOT_PICKABLE by default
                }
            }
        }
    }


    //returns the card found at the relative coordinates "coor"
    public Card getCardAtCoordinate(Coordinates coor) {
        Card tmp = new Card(this.board.get(coor).getColor(), this.board.get(coor).getState());
        this.removeCardAtCoordinate(coor);
        return tmp;
    }

    //removes a card from the board and updates each adjacent card's state to PICKABLE, if present
    private void removeCardAtCoordinate(Coordinates coor) {
        int x = coor.getX();
        int y = coor.getY();
        Coordinates[] coords = new Coordinates[4];
        coords[0] = new Coordinates(x, y + 1);
        coords[1] = new Coordinates(x + 1, y);
        coords[2] = new Coordinates(x, y - 1);
        coords[3] = new Coordinates(x - 1, y);
        for (int i = 0; i < 4; i++) {
            if (coords[i].CoordsAreValid() && board.get(coords[i]) != null) {
                board.get(coords[i]).setState(Card.State.PICKABLE);
            }
        }
        this.board.remove(coor);
    }


    //checks whether the board needs to be refreshed/refilled
    public boolean cardCheck() {
        int start = 0, length = 0;
        Coordinates AUXkey = new Coordinates(0, 0);
        Coordinates[] Adjacentcoords = new Coordinates[4];
        for (int i = 0; i < 9; i++) {
            int[] arr= getCorrectStartandLength(i);
            start = arr[0];
            length = arr[1];
            for (int j = 0; j < 9; j++) {
                if (j >= start && j < start + length) {
                    AUXkey.setXY(i, j);
                    if (board.get(AUXkey) != null) {
                        Adjacentcoords[0] = new Coordinates(i, j + 1);
                        Adjacentcoords[1] = new Coordinates(i + 1, j);
                        Adjacentcoords[2] = new Coordinates(i, j - 1);
                        Adjacentcoords[3] = new Coordinates(i - 1, j);
                        for (int k = 0; k < 4; k++) {
                            if (Adjacentcoords[i].CoordsAreValid() && board.get(Adjacentcoords[i]) != null) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    //refreshes the state of the cards once the board gets filled/refilled
    public void updatePickablesAtFirst() {
        Coordinates AUXkey = new Coordinates(0, 0);
        switch (numOfPlayers) {
            case 2 -> {
                for (int i = 1; i < 8; i++) {
                    AUXkey.setX(i);
                    switch (i) {
                        case 1 -> {
                            AUXkey.setY(4);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(5);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 2, 6 -> {
                            AUXkey.setY(3);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(5);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 3 -> {
                            AUXkey.setY(1);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(2);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(6);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 4 -> {
                            AUXkey.setY(1);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(7);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 5 -> {
                            AUXkey.setY(2);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(6);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(7);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 7 -> {
                            AUXkey.setY(3);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(4);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                    }
                }
            }
            case 3 -> {
                for (int i = 0; i < 9; i++) {
                    AUXkey.setX(i);
                    switch (i) {
                        case 0 -> {
                            AUXkey.setY(5);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 1 -> {
                            AUXkey.setY(4);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(5);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 2 -> {
                            AUXkey.setY(2);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(3);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(6);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 3 -> {
                            AUXkey.setY(0);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(1);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(6);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 4 -> {
                            AUXkey.setY(1);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(7);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 5 -> {
                            AUXkey.setY(2);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(7);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(8);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 6 -> {
                            AUXkey.setY(2);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(5);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(6);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 7 -> {
                            AUXkey.setY(3);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(4);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 8 -> {
                            AUXkey.setY(3);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                    }
                }
            }
            case 4 -> {
                for (int i = 0; i < 9; i++) {
                    AUXkey.setX(i);
                    switch (i) {
                        case 0 -> {
                            AUXkey.setY(4);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(5);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 1, 7 -> {
                            AUXkey.setY(3);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(5);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 2, 6 -> {
                            AUXkey.setY(2);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(6);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 3 -> {
                            AUXkey.setY(0);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(1);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(7);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 4 -> {
                            AUXkey.setY(0);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(8);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 5 -> {
                            AUXkey.setY(1);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(7);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(8);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 8 -> {
                            AUXkey.setY(3);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(4);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                    }
                }
            }
        }
    }

    public int[] getCorrectStartandLength(int row) {
        int start=0, length=0;
        int[] arrayofint = new int[2];
        switch (row) {
            case 0 -> {
                start = 3;
                length = 2;
                if (this.numOfPlayers < 4) {
                    length--;
                    if (this.numOfPlayers < 3) {
                        length--;
                    }
                }
            }
            case 1 -> {
                start = 3;
                length = 3;
                if (this.numOfPlayers < 4) {
                    length--;
                }
            }
            case 2, 6 -> {
                start = 2;
                length = 5;
                if (this.numOfPlayers < 3) {
                    start++;
                    length--;
                }
            }
            case 3 -> {
                start = 1;
                length = 8;
                if (this.numOfPlayers < 4) {
                    start++;
                    length--;
                    if (this.numOfPlayers < 3) {
                        length--;
                    }
                }
            }
            case 4 -> {
                start = 0;
                length = 9;
                if (this.numOfPlayers < 4) {
                    start++;
                    length--;
                }
            }
            case 5 -> {
                start = 0;
                length = 8;
                if (this.numOfPlayers < 4) {
                    length--;
                    if (this.numOfPlayers < 3) {
                        start++;
                        length--;
                    }
                }
            }
            case 7 -> {
                start = 3;
                length = 3;
                if (this.numOfPlayers < 4) {
                    start++;
                    length--;
                }
            }
            case 8 -> {
                start = 4;
                length = 2;
                if (this.numOfPlayers < 4) {
                    start++;
                    length--;
                    if (this.numOfPlayers < 3) {
                        length--;
                    }
                }
            }
        }
        arrayofint[0]= start;
        arrayofint[1]= length;
        return arrayofint;
    }
}