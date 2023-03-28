package org.example;

import java.util.HashMap;

abstract class Board {
    protected BagOfCards bag;
    protected HashMap<Coordinates, Card> board;
    private final Player firstPlayer ;

    //board constructor
    public Board(Player player, BagOfCards bag){
        this.firstPlayer = player;
        this.bag = bag;
    }

    //getFirstPlayer method, same for every subclass
    public Player getFirstPlayer(){
        return this.firstPlayer;
    }


    //fills board with new card taken from the bag
    abstract void fill(BagOfCards bag);

    //return card in position Coordinate coor
    private Card getCardAtCoordinate(Coordinates coor){
        Card tmp = new Card(board.get(coor).getColor() , board.get(coor).getState());
        this.removeCardAtCoordinate(coor);
        return tmp;
    }

    //remove a card from the board and set the reference to null, and updates adjacent
    private void removeCardAtCoordinate(Coordinates coor){          // la remove fatta ai lati della tabella (quando ci sono 4 giocatori) potrebbe creare casini quindi ho messo altre condizioni nell'if
        int x = coor.getX();                                        // la tabella has crea una matrice 9x9? oppure ciò che non inizializzo non fa parte dell'hashmap?, altrimenti devo controllare di non essere al bordo
        int y = coor.getY();                                        // sia quando siamo 2 giocatiri sia quando siamo 3, e allora il controllo varia in base a quanti siamo, e va implementato in manier diversa in base a quanti giocatori siamo
        Coordinates AUXcoor = new Coordinates(x,y-1);
        if (y-1>0 && board.get(AUXcoor)!= null)
            board.get(AUXcoor).setState(Card.State.PICKABLE);
        AUXcoor.setY(y+1);
        if (y+1<9 && board.get()]!= null)
            board.get(AUXcoor).setState(Card.State.PICKABLE);
        AUXcoor.setXY(x-1, y);
        if (x-1>0 && board.get(AUXcoor)!= null)
            board.get(AUXcoor).setState(Card.State.PICKABLE);
        AUXcoor.setX(x+1);
        if (x+1<9 && board.get(AUXcoor)!= null)
            board.get(AUXcoor).setState(Card.State.PICKABLE);
        board.get(AUXcoor) = null;
    }
    //probably useless, we'll check during class Game implementation
    public void boardProcedure(){
        //....
    }

    //checks whether the board needs to be refreshed/refilled

    abstract boolean cardCheck();

    // //refreshes the state of the cards once the board gets filled/refilled
    abstract void updatePickablesAtFirst();
}

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
                Coordinates key = new Coordinates(i,j);
                while(j>=start && j<start+length){
                    board.put(key, bag.collectCArd());
                    board.get(AUXkey).setState(Card.State.NOT_PICKABLE) // setto di defoult tutte le carte messe nella board a non pickable siccome il metodo dopo mi sistema a pickable quelle prendibili
                }
            }
        }
    }

    private void updatePickablesAtFirst(){
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
    private boolean cardCheck() {
        int start, length;
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
        board.put(key, bag.collectCArd());
        board.get(key).setState(Card.State.PICKABLE);
        Coordinates key = new Coordinates(2,2);
        board.put(key, bag.collectCArd());
        board.get(key).setState(Card.State.PICKABLE);
        Coordinates key = new Coordinates(2,6);
        board.put(key, bag.collectCArd());
        board.get(key).setState(Card.State.PICKABLE);
        Coordinates key = new Coordinates(3,8);
        board.put(key, bag.collectCArd());
        board.get(key).setState(Card.State.PICKABLE);
        Coordinates key = new Coordinates(5,0);
        board.put(key, bag.collectCArd());
        board.get(key).setState(Card.State.PICKABLE);
        Coordinates key = new Coordinates(6,2);
        board.put(key, bag.collectCArd());
        board.get(key).setState(Card.State.PICKABLE);
        Coordinates key = new Coordinates(6,6);
        board.put(key, bag.collectCArd());
        board.get(key).setState(Card.State.PICKABLE);
        Coordinates key = new Coordinates(8,5);
        board.put(key, bag.collectCArd());
        board.get(key).setState(Card.State.PICKABLE);
    }
    private boolean cardCheck() {
        int start, length;
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
                        AUXkey.setXY(i, j-1);                            // ho aggiunto questo AUXkey.set
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
class fourPlayersBoard extends threePlayersBoard(){
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
        board.put(key, bag.collectCArd());
        board.get(key).setState(Card.State.PICKABLE);
        Coordinates key = new Coordinates(1,5);
        board.put(key, bag.collectCArd());
        board.get(key).setState(Card.State.PICKABLE);
        Coordinates key = new Coordinates(3,1);
        board.put(key, bag.collectCArd());
        board.get(key).setState(Card.State.PICKABLE);
        Coordinates key = new Coordinates(4,0);
        board.put(key, bag.collectCArd());
        board.get(key).setState(Card.State.PICKABLE);
        Coordinates key = new Coordinates(4,8);
        board.put(key, bag.collectCArd());
        board.get(key).setState(Card.State.PICKABLE);
        Coordinates key = new Coordinates(5,7);
        board.put(key, bag.collectCArd());
        board.get(key).setState(Card.State.PICKABLE);
        Coordinates key = new Coordinates(7,3);
        board.put(key, bag.collectCArd());
        board.get(key).setState(Card.State.PICKABLE);
        Coordinates key = new Coordinates(8,4);
        board.put(key, bag.collectCArd());
        board.get(key).setState(Card.State.PICKABLE);
    }
private boolean cardCheck() {
        int start, length;
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
                        AUXkey.setXY(i, j-1);                            // ho aggiunto questo AUXkey.set
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