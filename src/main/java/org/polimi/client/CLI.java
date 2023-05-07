package org.polimi.client;

import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.net.CookieStore;
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
    
    private String sharedGoal1;
    private String sharedGoal2;
    //to modify (has to print a mini bookshelf)
    private String personalGoal1;

    

    public void setSharedGoal1(int i) {
        switch (i) {
            case 0 -> {sharedGoal1 = "shared goal 1";}
            case 1 -> {sharedGoal1 = "shared goal 2";}
            case 2 -> {sharedGoal1 = "shared goal 3";}
            case 3 -> {sharedGoal1 = "shared goal 4";}
            case 4 -> {sharedGoal1 = "shared goal 5";}
            case 5 -> {sharedGoal1 = "shared goal 6";}
            case 6 -> {sharedGoal1 = "shared goal 7";}
            case 7 -> {sharedGoal1 = "shared goal 8";}
            case 8 -> {sharedGoal1 = "shared goal 9";}
            case 9 -> {sharedGoal1 = "shared goal 10";}
            case 10 -> {sharedGoal1 = "shared goal 11";}
            case 11 -> {sharedGoal1 = "shared goal 12";}
            default -> {sharedGoal1 = "error";}
        }
    }

    public void setSharedGoal2(int i) {
        switch (i) {
            case 0 -> {sharedGoal2 = "shared goal 1";}
            case 1 -> {sharedGoal2 = "shared goal 2";}
            case 2 -> {sharedGoal2 = "shared goal 3";}
            case 3 -> {sharedGoal2 = "shared goal 4";}
            case 4 -> {sharedGoal2 = "shared goal 5";}
            case 5 -> {sharedGoal2 = "shared goal 6";}
            case 6 -> {sharedGoal2 = "shared goal 7";}
            case 7 -> {sharedGoal2 = "shared goal 8";}
            case 8 -> {sharedGoal2 = "shared goal 9";}
            case 9 -> {sharedGoal2 = "shared goal 10";}
            case 10 -> {sharedGoal2 = "shared goal 11";}
            case 11 -> {sharedGoal2 = "shared goal 12";}
            default -> {sharedGoal2 = "error";}
        }
    }

    public void setPersonalGoal(int i) {
        switch (i) {
            case 0 -> {personalGoal1 = "shared goal 1";}
            case 1 -> {personalGoal1 = "shared goal 2";}
            case 2 -> {personalGoal1 = "shared goal 3";}
            case 3 -> {personalGoal1 = "shared goal 4";}
            case 4 -> {personalGoal1 = "shared goal 5";}
            case 5 -> {personalGoal1 = "shared goal 6";}
            case 6 -> {personalGoal1 = "shared goal 7";}
            case 7 -> {personalGoal1 = "shared goal 8";}
            case 8 -> {personalGoal1 = "shared goal 9";}
            case 9 -> {personalGoal1 = "shared goal 10";}
            case 10 -> {personalGoal1 = "shared goal 11";}
            case 11 -> {personalGoal1 = "shared goal 12";}
            default -> {personalGoal1 = "error";}
        }
    }

    public void setPlayers(String[] players) {
        this.players = players;
        this.numOfPlayers = players.length;
    }

    public CLI(String[] players, int me, int numOfPlayers){
        this.players = players;
        this.me = me;
        this.numOfPlayers = numOfPlayers;
        this.clientBookshelf = new ClientBookshelf[numOfPlayers];
        for(int i=0 ; i<numOfPlayers ; i++){
            this.clientBookshelf[i] = new ClientBookshelf();
        }
    }

    public void setClientBookshelf (Map<Coordinates, Card> bookshelf) {
        clientBookshelf[me].setGrid(bookshelf);
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

    public void printRoutine(){
        System.out.println("My username: "+players[me]);
        this.clientBoard.printMap();
        this.clientBookshelf[me].printMyBookshelf();
        this.clientBookshelf[me].print();
        System.out.println("personal goal: " + this.personalGoal1);
        System.out.println("shared goal 1: " + this.sharedGoal1);
        System.out.println("shared goal 2: " + this.sharedGoal2);
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
