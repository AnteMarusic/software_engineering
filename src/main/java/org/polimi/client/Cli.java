package org.polimi.client;

import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.util.*;

import static org.polimi.GameRules.boardRowColInBound;

public class Cli {
    public static final String ANSI_RESET = "\u001B[0m";

    public static final String ANSI_PINK = "\u001B[35m"; //PINK
    public static final String ANSI_GREEN = "\u001B[32m"; //GREEN
    public static final String ANSI_WHITE = "\u001B[37m"; //WHITE
    public static final String ANSI_ORANGE = "\u001B[31m"; //ORANGE
    public static final String ANSI_CYAN = "\u001B[36m"; //CYAN
    public static final String ANSI_BLUE = "\u001B[34m"; //BLUE

    private ClientBoard board;
    private int numOfPlayers;
    private List<String> players;
    private String myUsername;

    private Map<String, ClientBookshelf> bookshelvesMap;
    private int me; //my player index
    private int lastPlayerInserted;
    private List<Card> chosenCards;
    
    private String sharedGoal1;
    private String sharedGoal2;
    //to modify (has to print a mini bookshelf)
    private String personalGoal;

    public Cli() {
        board = null;
        bookshelvesMap = new HashMap<String, ClientBookshelf>();
        players = new ArrayList<String>();
        lastPlayerInserted = 0;
        me = 0;
        chosenCards = null;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public int getInsertable (int col) {
        return bookshelvesMap.get(players.get(me)).getInsertable(col);
    }

    public void setPlayers(List<String> players) {
        this.players = players;
        this.numOfPlayers = players.size();
        for(int i=0 ; i<players.size() ; i++){
            this.bookshelvesMap.put(players.get(i) , new ClientBookshelf());
        }
    }

    public void setBookshelves(List<Card[][]> bookshelves){
        // chiedere ad Anto problema di ordinamento/legare string a propria bookshelf
    }
    public void setSharedGoal1(int i) {
        switch (i) {
            case 0 -> {sharedGoal1 = "Six groups each containing at least 2 tiles of the same type (not necessarily in the depicted shape). The tiles of one group can be different from those of another group.";}
            case 1 -> {sharedGoal1 = "Four groups each containing at least 4 tiles of the same type (not necessarily in the depicted shape). The tiles of one group can be different from those of another group.";}
            case 2 -> {sharedGoal1 = "Four tiles of the same type in the four corners of the bookshelf. ";}
            case 3 -> {sharedGoal1 = "Two groups each containing 4 tiles of the same type in a 2x2 square. The tiles of one square can be different from those of the other square.";}
            case 4 -> {sharedGoal1 = "Three columns each formed by 6 tiles of maximum three different types. One column can show the same or a different combination of another column.";}
            case 5 -> {sharedGoal1 = "Eight tiles of the same type. There’s no restriction about the position of these tiles.";}
            case 6 -> {sharedGoal1 = "Five tiles of the same type forming a diagonal. ";}
            case 7 -> {sharedGoal1 = "Four lines each formed by 5 tiles of maximum three different types. One line can show the same or a different combination of another line.";}
            case 8 -> {sharedGoal1 = "Two columns each formed by 6 different types of tiles.";}
            case 9 -> {sharedGoal1 = "Two lines each formed by 5 different types of tiles. One line can show the same or a different combination of the other line.";}
            case 10 -> {sharedGoal1 = "Five tiles of the same type forming an X.";}
            case 11 -> {sharedGoal1 = "Five columns of increasing or decreasing height. Starting from the first column on the left or on the right, each next column must be made of exactly one more tile. Tiles can be of any type. ";}
            default -> {sharedGoal1 = "error";}
        }
    }

    public void setSharedGoal2(int i) {
        switch (i) {
            case 0 -> {sharedGoal2 = "Six groups each containing at least 2 tiles of the same type (not necessarily in the depicted shape). The tiles of one group can be different from those of another group.";}
            case 1 -> {sharedGoal2 = "Four groups each containing at least 4 tiles of the same type (not necessarily in the depicted shape). The tiles of one group can be different from those of another group.";}
            case 2 -> {sharedGoal2 = "Four tiles of the same type in the four corners of the bookshelf. ";}
            case 3 -> {sharedGoal2 = "Two groups each containing 4 tiles of the same type in a 2x2 square. The tiles of one square can be different from those of the other square.";}
            case 4 -> {sharedGoal2 = "Three columns each formed by 6 tiles of maximum three different types. One column can show the same or a different combination of another column.";}
            case 5 -> {sharedGoal2 = "Eight tiles of the same type. There’s no restriction about the position of these tiles.";}
            case 6 -> {sharedGoal2 = "Five tiles of the same type forming a diagonal. ";}
            case 7 -> {sharedGoal2 = "Four lines each formed by 5 tiles of maximum three different types. One line can show the same or a different combination of another line.";}
            case 8 -> {sharedGoal2 = "Two columns each formed by 6 different types of tiles.";}
            case 9 -> {sharedGoal2 = "Two lines each formed by 5 different types of tiles. One line can show the same or a different combination of the other line.";}
            case 10 -> {sharedGoal2 = "Five tiles of the same type forming an X.";}
            case 11 -> {sharedGoal2 = "Five columns of increasing or decreasing height. Starting from the first column on the left or on the right, each next column must be made of exactly one more tile. Tiles can be of any type. ";}
            default -> {sharedGoal2 = "error";}
        }
    }

    public void setPersonalGoal(int i) {
        switch (i) {
            case 0 -> {
                personalGoal = "shared goal 1";}
            case 1 -> {
                personalGoal = "shared goal 2";}
            case 2 -> {
                personalGoal = "shared goal 3";}
            case 3 -> {
                personalGoal = "shared goal 4";}
            case 4 -> {
                personalGoal = "shared goal 5";}
            case 5 -> {
                personalGoal = "shared goal 6";}
            case 6 -> {
                personalGoal = "shared goal 7";}
            case 7 -> {
                personalGoal = "shared goal 8";}
            case 8 -> {
                personalGoal = "shared goal 9";}
            case 9 -> {
                personalGoal = "shared goal 10";}
            case 10 -> {
                personalGoal = "shared goal 11";}
            case 11 -> {
                personalGoal = "shared goal 12";}
            default -> {
                personalGoal = "error";}
        }
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

    public void setBoard(Map<Coordinates, Card> board){
        this.board = new ClientBoard(board, this.numOfPlayers);
    }


    public int getMaxInsertable() {
        return bookshelvesMap.get(me).getMaxInsertable();
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }
    
    public void addNewPlayer (String newPlayer) {
        this.players.set(lastPlayerInserted+1, newPlayer);
        lastPlayerInserted ++;
    }

    public Card boardSeeCardAtCoordinates (Coordinates coordinates) {
        return board.seeCardAtCoordinates(coordinates);
    }

    public boolean isCardPickable (Coordinates coordinates) {
        return board.seeCardAtCoordinates(coordinates) != null &&
                board.seeCardAtCoordinates(coordinates).getState() == Card.State.PICKABLE;
    }

    public void insert (int col) {
        this.bookshelvesMap.get(me).insert(this.chosenCards, col);
    }

    public void removeCards (List<Coordinates> toRemove){
        Coordinates temp;
        Card card;
        Coordinates[] AdjacentCoordinates = new Coordinates[4];
        int i = 0;
        while (toRemove.size() > 0) {
            temp = toRemove.get(i);
            card = this.board.removeCardAtCoordinates(temp);
            this.chosenCards.add(card);
            AdjacentCoordinates[0] = new Coordinates(temp.getRow(), temp.getCol() + 1);
            AdjacentCoordinates[1] = new Coordinates(temp.getRow() + 1, temp.getCol());
            AdjacentCoordinates[2] = new Coordinates(temp.getRow(), temp.getCol() - 1);
            AdjacentCoordinates[3] = new Coordinates(temp.getRow() - 1, temp.getCol());
            for (int j = 0; j < 4; j++) {
                if (boardRowColInBound(AdjacentCoordinates[j].getRow(), AdjacentCoordinates[j].getCol(), numOfPlayers) && board.seeCardAtCoordinates(AdjacentCoordinates[i]) != null) {
                    this.board.setToPickable(AdjacentCoordinates[j]);
                }
            }
            i++;
        }
    }

    public void printRoutine(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("My username: "+players.get(me));
        this.board.printMap();
        this.bookshelvesMap.get(me).printMyBookshelf();
        for(int i=0 ; i<players.size() ; i++){
            if(i!=me){
                System.out.println(players.get(i)+ "'s bookshelf");
                this.bookshelvesMap.get(players.get(i)).print();
                for(int j=0 ; j<2 ; j++){
                    System.out.println();
                }
            }
        }
        System.out.println("personal goal: " + this.personalGoal);
        System.out.println("shared goal 1: " + this.sharedGoal1);
        System.out.println("shared goal 2: " + this.sharedGoal2);
    }

    public void printLobby(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("PLAYERS CONNECTED: ");
        for(int i=0 ; i<players.size() ; i++){
            if(players.get(i)!=myUsername){
                System.out.println(i+": "+players.get(i));
            }
        }
    }

    public String askForUsername(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("INSERIRE USERNAME");
        return scanner.nextLine();
    }

    public void usernameValid(){
        System.out.println("USERNAME VALIDO");
    }

    public int getGameMode(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }




}
