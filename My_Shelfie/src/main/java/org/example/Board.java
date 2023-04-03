package org.example;

import java.util.HashMap;

public class Board {
    private BagOfCards bag;
    private HashMap<Coordinates, Card> board;
    private int numOfPlayers;

    //board constructor
    public Board(int numOfPlayers, BagOfCards bag ){
        this.numOfPlayers = numOfPlayers;
        this.bag = bag;
        this.board = new HashMap<Coordinates, Card>();
        this.fill();
        this.updatePickablesAtFirst();
    }

    //fills board with new card taken from the bag
    public void fill(){
        int start=0, length=0;
        switch (this.numOfPlayers) {
            case 2 :{
                for (int i = 1; i < 8; i++) {
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
                    }
                    for (int j = 1; j < 8; j++) {
                        Coordinates AUXkey = new Coordinates(i, j);
                        while (j >= start && j < start + length) {
                            this.board.put(AUXkey, bag.collectCard());
                            this.board.get(AUXkey).setState(Card.State.NON_PICKABLE); // setto di default tutte le carte messe nella board a non pickable siccome il metodo dopo mi sistema a pickable quelle prendibili
                        }
                    }
                }
            }
            case 3 :{
                for (int i = 0; i < 9; i++) {
                    switch (i) {
                        case 0 -> {
                            start = 5;
                            length = 1;
                        }
                        case 1 -> {
                            start = 4;
                            length = 2;
                        }
                        case 2, 6 -> {
                            start = 2;
                            length = 5;
                        }
                        case 3 -> {
                            start = 0;
                            length = 7;
                        }
                        case 4 -> {
                            start = 1;
                            length = 7;
                        }
                        case 5 -> {
                            start = 2;
                            length = 7;
                        }
                        case 7 -> {
                            start = 3;
                            length = 2;
                        }
                        case 8 -> {
                            start = 3;
                            length = 1;
                        }
                    }
                    for (int j = 0; j < 9; j++) {
                        Coordinates AUXkey = new Coordinates(i, j);
                        while (j >= start && j < start + length) {
                            this.board.put(AUXkey, bag.collectCard());
                            this.board.get(AUXkey).setState(Card.State.NON_PICKABLE); // setto di defoult tutte le carte messe nella board a non pickable siccome il metodo dopo mi sistema a pickable quelle prendibili
                        }
                    }
                }
            }
            case 4 :{
                for (int i = 0; i < 9; i++) {
                    switch (i) {
                        case 0 -> {
                            start = 4;
                            length = 2;
                        }
                        case 1 , 7 -> {
                            start = 3;
                            length = 3;
                        }
                        case 2, 6 -> {
                            start = 2;
                            length = 5;
                        }
                        case 3 -> {
                            start = 0;
                            length = 8;
                        }
                        case 4 -> {
                            start = 0;
                            length = 9;
                        }
                        case 5 -> {
                            start = 1;
                            length = 8;
                        }
                        case 8 -> {
                            start = 3;
                            length = 2;
                        }
                    }
                    for (int j = 0; j < 9; j++) {
                        Coordinates AUXkey = new Coordinates(i, j);
                        while (j >= start && j < start + length) {
                            this.board.put(AUXkey, bag.collectCard());
                            this.board.get(AUXkey).setState(Card.State.NON_PICKABLE); // setto di default tutte le carte messe nella board a non pickable siccome il metodo dopo mi sistema a pickable quelle prendibili
                        }
                    }
                }
            }
        }
    }

    //return card in position Coordinate coor
    private Card getCardAtCoordinate(Coordinates coor){
        Card tmp = new Card(this.board.get(coor).getColor() , this.board.get(coor).getState());
        this.removeCardAtCoordinate(coor);
        return tmp;
    }

    //remove a card from the board and set the reference to null, and updates adjacent
    private void removeCardAtCoordinate(Coordinates coor){
        int x = coor.getX();
        int y = coor.getY();
        Coordinates AUXcoor = new Coordinates(x,y-1);
        if (y-1>0 && this.board.get(AUXcoor)!= null) {
            this.board.get(AUXcoor).setState(Card.State.PICKABLE);
        }
        AUXcoor.setY(y+2);
        if (y+1<9 && this.board.get(AUXcoor)!= null){
            this.board.get(AUXcoor).setState(Card.State.PICKABLE);
        }
        AUXcoor.setXY(x-1, y-1);
        if (x-1>0 && this.board.get(AUXcoor)!= null) {
            this.board.get(AUXcoor).setState(Card.State.PICKABLE);
        }
        AUXcoor.setX(x+2);
        if (x+1<9 && this.board.get(AUXcoor)!= null) {
            this.board.get(AUXcoor).setState(Card.State.PICKABLE);
        }
        AUXcoor.setX(x-1);
        this.board.remove(AUXcoor);
    }


    //checks whether the board needs to be refreshed/refilled
    public boolean cardCheck(){
        switch (this.numOfPlayers){
            int start, length;
            Coordinates AUXkey = new Coordinates(0,0);
            case 2 ->{
                for (int i = 1; i < 8; i++) {
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
                    }
                    for (int j = 1; j < 8; j++) { //assicurarsi degli indici
                        while (j >= start && j < start + length) {
                            AUXkey.setXY(i, j);
                            if (board.get(AUXkey) != null) {
                                AUXkey.setY(j-1);
                                if (board.get(AUXkey) != null){
                                    return false;
                                }
                                AUXkey.setY(j + 2);
                                if (board.get(AUXkey) != null){
                                    return false;
                                }
                                AUXkey.setXY(i - 1,j-1);
                                if (board.get(AUXkey) != null){
                                    return false;
                                }
                                AUXkey.setX(i + 2);
                                if (board.get(AUXkey) != null){
                                    return false;
                                }
                            }
                        }
                    }
                }
                return true;
            }
            case 3 ->{
                for (int i = 0; i < 9; i++) {
                    switch (i) {
                        case 0 -> {
                            start = 5;
                            length = 1;
                        }
                        case 1 -> {
                            start = 4;
                            length = 2;
                        }
                        case 2, 6 -> {
                            start = 2;
                            length = 5;
                        }
                        case 3 -> {
                            start = 0;
                            length = 7;
                        }
                        case 4 -> {
                            start = 1;
                            length = 7;
                        }
                        case 5 -> {
                            start = 2;
                            length = 7;
                        }
                        case 7 -> {
                            start = 3;
                            length = 2;
                        }
                        case 8 -> {
                            start = 3;
                            length = 1;
                        }
                    }
                    for (int j = 1; j < 8; j++) { //assicurarsi degli indici
                        while (j >= start && j < start + length) {
                            AUXkey.setXY(i, j);
                            if (board.get(AUXkey) != null) {
                                AUXkey.setY(j-1);
                                if (board.get(AUXkey) != null){
                                    return false;
                                }
                                AUXkey.setY(j + 2);
                                if (board.get(AUXkey) != null){
                                    return false;
                                }
                                AUXkey.setXY(i - 1,j-1);
                                if (board.get(AUXkey) != null){
                                    return false;
                                }
                                AUXkey.setX(i + 2);
                                if (board.get(AUXkey) != null){
                                    return false;
                                }
                            }
                        }
                    }
                }
                return true;
            }
            case 4 -> {
                for (int i = 0; i < 9; i++) {
                    switch (i) {
                        case 0 -> {
                            start = 4;
                            length = 2;
                        }
                        case 1, 7 -> {
                            start = 3;
                            length = 3;
                        }
                        case 2, 6 -> {
                            start = 2;
                            length = 5;
                        }
                        case 3 -> {
                            start = 0;
                            length = 8;
                        }
                        case 4 -> {
                            start = 0;
                            length = 9;
                        }
                        case 5 -> {
                            start = 1;
                            length = 8;
                        }
                        case 8 -> {
                            start = 3;
                            length = 2;
                        }
                    }
                    for (int j = 1; j < 8; j++) { //assicurarsi degli indici
                        while (j >= start && j < start + length) {
                            AUXkey.setXY(i, j);
                            if (board.get(AUXkey) != null) {
                                AUXkey.setY(j - 1);
                                if (board.get(AUXkey) != null) {
                                    return false;
                                }
                                AUXkey.setY(j + 2);
                                if (board.get(AUXkey) != null) {
                                    return false;
                                }
                                AUXkey.setXY(i - 1, j - 1);
                                if (board.get(AUXkey) != null) {
                                    return false;
                                }
                                AUXkey.setX(i + 2);
                                if (board.get(AUXkey) != null) {
                                    return false;
                                }
                            }
                        }
                    }
                }
                return true;
            }
        }
    }

    // //refreshes the state of the cards once the board gets filled/refilled
    public void updatePickablesAtFirst(){
        Coordinates AUXkey = new Coordinates(0,0);
        switch (numOfPlayers){
            case 2 ->{
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
            case 3 ->{
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
            case 4 ->{
                for (int i = 0; i < 9; i++) {
                    AUXkey.setX(i);
                    switch (i) {
                        case 0 -> {
                            AUXkey.setY(4);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(5);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 1 , 7 -> {
                            AUXkey.setY(3);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                            AUXkey.setY(5);
                            this.board.get(AUXkey).setState(Card.State.PICKABLE);
                        }
                        case 2, 6  -> {
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
}