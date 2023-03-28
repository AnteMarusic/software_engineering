package org.example;
import java.util.HashMap;

public class fourPlayersBoard extends threePlayersBoard {
    public fourPlayersBoard(Player player, BagOfCards bag){
        super();
        Coordinates AUXkey = new Coordinates(0,0);
        AUXkey.setXY(1,4);
        board.get(AUXkey).setState(Card.State.NOT_PICKABLE);
        AUXkey.setXY(4,1);
        board.get(AUXkey).setState(Card.State.NOT_PICKABLE);
        AUXkey.setXY(4,7);
        board.get(AUXkey).setState(Card.State.NOT_PICKABLE);
        AUXkey.setXY(7,4);
        board.get(AUXkey).setState(Card.State.NOT_PICKABLE);
        fillAndUpdatePickablesAtFirstNew2(bag);

    }
    protected void fillAndUpdatePickablesAtFirstNew2 (BagOfCards bag){
        Coordinates key = new Coordinates(0,5);
        board.put(key, bag.collectCard());
        board.get(key).setState(Card.State.PICKABLE);
        key.setXY(1, 5 );
        board.put(key, bag.collectCard());
        board.get(key).setState(Card.State.PICKABLE);
        key.setXY(3, 1 );
        board.put(key, bag.collectCard());
        board.get(key).setState(Card.State.PICKABLE);
        key.setXY(4, 0 );
        board.put(key, bag.collectCard());
        board.get(key).setState(Card.State.PICKABLE);
        key.setXY(4, 8 );
        board.put(key, bag.collectCard());
        board.get(key).setState(Card.State.PICKABLE);
        key.setXY(5, 7 );
        board.put(key, bag.collectCard());
        board.get(key).setState(Card.State.PICKABLE);
        key.setXY(7, 3 );
        board.put(key, bag.collectCard());
        board.get(key).setState(Card.State.PICKABLE);
        key.setXY(8, 4 );
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
                    length = 2;
                }
                case 1 -> {
                    start = 3;
                    length = 3;
                }
                case 2, 6 -> {
                    start = 2;
                    length = 5;
                }
                case 3 -> {
                    start = 1;
                    length = 8;
                }
                case 4 -> {
                    start = 0;
                    length = 9;
                }
                case 5 -> {
                    start = 0;
                    length = 8;
                }
                case 7 -> {
                    start = 3;
                    length = 3;
                }
                case 8 -> {
                    start = 4;
                    length = 2;
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
                        if (x-1>0 && board.get(AUXkey) != null){
                            return false;
                        }
                        AUXkey.setX(i + 1);
                        if (x+1<9 && board.get(AUXkey) != null){
                            return false;
                        }
                    }
                }
            }
        }
        return true;

    }
}