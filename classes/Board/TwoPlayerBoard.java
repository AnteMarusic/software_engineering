package org.example;

import java.util.HashMap;

class twoPlayersBoard extends Board {
    //2 players constructor
    public twoPlayersBoard(Player player, BagOfCards bag) {
        //call super constructor
        super(player, bag);
        //initialize hash map based on num of players
        int start;
        int length;
        int value=0;
        this.board = new HashMap<Coordinates, Card>();
        //fill board with card the first time
        this.fill(bag);
        this.updatePickablesAtFirst();
    }
    protected void fill(BagOfCards bag){
        int start=0, length=0;
        for (int i = 1; i < 9; i++) {
            switch (i) {
                case 1 -> {
                    start = 4;
                    length = 2;
                }
                case 2, 6 -> {
                    start = 3;
                    length = 3;
                }
                case 3 -> {
                    start = 1;
                    length = 6;
                }
                case 4 -> {
                    start = 1;
                    length = 7;
                }
                case 5 -> {
                    start = 2;
                    length = 6;
                }
                case 7 -> {
                    start = 3;
                    length = 2;
                }
                case 8 -> {
                    start = 0;
                    length = 0;
                }
            }
            for (int j = 0; j < 9; j++) {
                Coordinates AUXkey = new Coordinates(i,j);
                while(j>=start && j<start+length){
                    board.put(AUXkey, bag.collectCard());
                    board.get(AUXkey).setState(Card.State.NOT_PICKABLE); // setto di defoult tutte le carte messe nella board a non pickable siccome il metodo dopo mi sistema a pickable quelle prendibili
                }
            }
        }
    }

    public void updatePickablesAtFirst(){
        Coordinates AUXkey = new Coordinates(0,0);
        for (int i = 1; i < 9; i++) {
            AUXkey.setX(i);
            switch (i) {
                case 1 -> {
                    AUXkey.setY(3);
                    board.get(AUXkey).setState(Card.State.PICKABLE);
                    AUXkey.setY(4);
                    board.get(AUXkey).setState(Card.State.PICKABLE);
                }
                case 2, 6 -> {
                    AUXkey.setY(3);
                    board.get(AUXkey).setState(Card.State.PICKABLE);
                    AUXkey.setY(5);
                    board.get(AUXkey).setState(Card.State.PICKABLE);
                }
                case 3 -> {
                    AUXkey.setY(2);
                    board.get(AUXkey).setState(Card.State.PICKABLE);
                    AUXkey.setY(6);
                    board.get(AUXkey).setState(Card.State.PICKABLE);
                    AUXkey.setY(7);
                    board.get(AUXkey).setState(Card.State.PICKABLE);
                }
                case 4 -> {
                    AUXkey.setY(1);
                    board.get(AUXkey).setState(Card.State.PICKABLE);
                    AUXkey.setY(7);
                    board.get(AUXkey).setState(Card.State.PICKABLE);
                }
                case 5 -> {
                    AUXkey.setY(1);
                    board.get(AUXkey).setState(Card.State.PICKABLE);
                    AUXkey.setY(2);
                    board.get(AUXkey).setState(Card.State.PICKABLE);
                    AUXkey.setY(6);
                    board.get(AUXkey).setState(Card.State.PICKABLE);
                }
                case 7 -> {
                    AUXkey.setY(4);
                    board.get(AUXkey).setState(Card.State.PICKABLE);
                    AUXkey.setY(5);
                    board.get(AUXkey).setState(Card.State.PICKABLE);
                }
            }
        }
    }
    public boolean cardCheck() {
        int start=0, length=0;
        Coordinates AUXkey = new Coordinates(0,0);
        for (int i = 1; i < 8; i++) {
            AUXkey.setX(i);
            switch (i) {
                case 1 -> {
                    start = 3;
                    length = 2;
                }
                case 2, 6 -> {
                    start = 3;
                    length = 3;
                }
                case 3 -> {
                    start = 2;
                    length = 6;
                }
                case 4 -> {
                    start = 1;
                    length = 7;
                }
                case 5 -> {
                    start = 1;
                    length = 6;
                }
                case 7 -> {
                    start = 4;
                    length = 2;
                }
            }
            for (int j = 1; j < 8; j++) {
                while (j >= start && j < start + length) {
                    AUXkey.setXY(i, j);
                    if (board.get(AUXkey) != null) {
                        AUXkey.setXY(i, j-1);
                        if (board.get(AUXkey) != null){
                            return false;
                        }
                        AUXkey.setY(j + 1);
                        if (board.get(AUXkey) != null){
                            return false;
                        }
                        AUXkey.setXY(i - 1,j);
                        if (board.get(AUXkey) != null){
                            return false;
                        }
                        AUXkey.setX(i + 1);
                        if (board.get(AUXkey) != null){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}