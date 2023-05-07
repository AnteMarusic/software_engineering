package org.polimi.client;

import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.util.List;
import java.util.Map;

public class CLI {
    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_PINK = "\u001B[35m"; //PINK
    public static final String ANSI_GREEN = "\u001B[32m"; //GREEN
    public static final String ANSI_WHITE = "\u001B[37m"; //WHITE
    public static final String ANSI_ORANGE = "\u001B[31m"; //ORANGE
    public static final String ANSI_CYAN = "\u001B[36m"; //CYAN
    public static final String ANSI_BLUE = "\u001B[34m"; //BLUE

    private ClientBoard clientBoard;
    private ClientBookshelf[] clientBookshelf;
    private int numOfPlayers;
    private String[] players;
    private final int me; //my player index
    private int lastPlayerInserted;
    private List<Card> chosenCards;

    public CLI(String[] players, int me, int numOfPlayers){
        this.players = players;
        this.me = me;
        this.numOfPlayers = numOfPlayers;
        this.clientBookshelf = new ClientBookshelf[numOfPlayers];
        for(int i=0 ; i<numOfPlayers ; i++){
            this.clientBookshelf[i] = new ClientBookshelf();
        }
    }
    public CLI() {
        clientBoard = null;
        clientBookshelf = null;
        players = null;
        lastPlayerInserted = 0;
        me = 0;
        chosenCards = null;
    }
    public void printRoutine(){
        System.out.println("My username: "+players[me]);
        this.clientBoard.printMap();
        this.clientBookshelf[me].printMyBookshelf();
        this.clientBookshelf[me].print();
    }

    public void setChosenCards (List<Card> chosenCards) {
        this.chosenCards = chosenCards;
    }

    public List<Card> getChosenCards() {
        return chosenCards;
    }

    public int getChosenCardsSize() {
        return this.chosenCards.size();
    }

    public void setClientBoard(Map<Coordinates, Card> clientBoard){
        this.clientBoard = new ClientBoard(clientBoard, this.numOfPlayers);
    }


    public int getMaxInsertable() {
        return clientBookshelf[me].getMaxInsertable();
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void addNewPlayer (String newPlayer) {
        this.players[lastPlayerInserted + 1] = newPlayer;
        lastPlayerInserted ++;
    }

    public Card boardSeeCardAtCoordinates (Coordinates coordinates) {
        return clientBoard.seeCardAtCoordinates(coordinates);
    }

    public boolean isCardPickable (Coordinates coordinates) {
        return clientBoard.seeCardAtCoordinates(coordinates) != null && clientBoard.seeCardAtCoordinates(coordinates).getState() == Card.State.PICKABLE;
    }

    public void insert (int col) {
        this.clientBookshelf[me].insert(this.chosenCards, col);
    }
    public int getInsertable (int col) {
        return clientBookshelf[me].getInsertable(col);
    }

    public void removeCards (List<Coordinates> toRemove) {
        Coordinates temp;
        Card card;
        int i = 0;
        while (toRemove.size() > 0) {
            temp = toRemove.get(i);
            card = this.clientBoard.removeCardAtCoordinates(temp);
            this.chosenCards.add(card);
            i ++;
        }
    }
}
