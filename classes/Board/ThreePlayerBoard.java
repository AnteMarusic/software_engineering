package org.example;
import java.util.HashMap;

class threePlayersBoard extends twoPlayersBoard{
    public threePlayersBoard(Player player, BagOfCards bag){
        super();
        Coordinates AUXkey = new Coordinates(0,0);
        AUXkey.setXY(2,3);
        board.get(AUXkey).setState(Card.State.NOT_PICKABLE);
        AUXkey.setXY(3,6);
        board.get(AUXkey).setState(Card.State.NOT_PICKABLE);
        AUXkey.setXY(5,2);
        board.get(AUXkey).setState(Card.State.NOT_PICKABLE);
        AUXkey.setXY(6,5);
        board.get(AUXkey).setState(Card.State.NOT_PICKABLE);
        fillAndUpdatePickablesAtFirstNew(bag);
    }
    protected void fillAndUpdatePickablesAtFirstNew (BagOfCards bag){      // riempio gli spazi vuoti (quelli con 3 pallini) e setto le nuove carte aggiunte a pickable
        Coordinates key = new Coordinates(0,4);
        board.put(key, bag.collectCard());
        board.get(key).setState(Card.State.PICKABLE);
        key.setXY(2, 2 );
        board.put(key, bag.collectCard());
        board.get(key).setState(Card.State.PICKABLE);
        key.setXY(2, 6 );
        board.put(key, bag.collectCard());
        board.get(key).setState(Card.State.PICKABLE);
        key.setXY(3, 8 );
        board.put(key, bag.collectCard());
        board.get(key).setState(Card.State.PICKABLE);
        key.setXY(5, 0 );
        board.put(key, bag.collectCard());
        board.get(key).setState(Card.State.PICKABLE);
        key.setXY(6, 2 );
        board.put(key, bag.collectCard());
        board.get(key).setState(Card.State.PICKABLE);
        key.setXY(6, 6 );
        board.put(key, bag.collectCard());
        board.get(key).setState(Card.State.PICKABLE);
        key.setXY(8, 5 );
        board.put(key, bag.collectCard());
        board.get(key).setState(Card.State.PICKABLE);
    }
    private boolean cardCheck() {
        int start=0, length=0;
        Coordinates AUXkey = new Coordinates(0,0);
        for (int i = 0; i < 9; i++) {
            AUXkey.setX(i);
            switch (i) {
                case 0 -> {
                    start = 3;
                    length = 1;
                }
                case 1 -> {
                    start = 3;
                    length = 2;
                }
                case 2, 6 -> {
                    start = 2;
                    length = 5;
                }
                case 3 -> {
                    start = 2;
                    length = 7;
                }
                case 4 -> {
                    start = 1;
                    length = 7;
                }
                case 5 -> {
                    start = 0;
                    length = 7;
                }
                case 7 -> {
                    start = 4;
                    length = 2;
                }
                case 8 -> {
                    start = 5;
                    length = 1;
                }
            }
            for (int j = 0; j < 9; j++) {
                while (j >= start && j < start + length) {
                    AUXkey.setXY(i, j);
                    if (board.get(AUXkey) != null) {
                        AUXkey.setXY(i, j-1);
                        if (j-1>0 && board.get(AUXkey) != null){
                            return false;
                        }
                        AUXkey.setY(j + 1);
                        if (j+1<9 && board.get(AUXkey) != null){
                            return false;
                        }
                        AUXkey.setXY(i - 1,j);
                        if (i-1>0 && board.get(AUXkey) != null){
                            return false;
                        }
                        AUXkey.setX(i + 1);
                        if (i+1<9 && board.get(AUXkey) != null){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}