package org.polimi.client.view;

import org.polimi.client.ClientBoard;
import org.polimi.client.ClientBookshelf;
import org.polimi.client.ClientPersonalGoal;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.awt.*;
import java.util.*;
import java.util.List;

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

    private List<ClientBookshelf> bookshelves;
    private int me; //my player index
    private int currentPlayer;
    private int lastPlayerInserted;
    private List<Card> chosenCards;
    private List<Card> otherPlayerChosenCards;
    private List<Card> orderedChosenCards;
    
    private String sharedGoal1;
    private String sharedGoal2;
    //to modify (has to print a mini bookshelf)
    private ClientPersonalGoal personalGoal;


    public Cli() {
        board = null;
        bookshelves = new ArrayList<ClientBookshelf>();
        players = new ArrayList<String>();
        lastPlayerInserted = 0;
        me = 0;
        chosenCards = new ArrayList<>();
        otherPlayerChosenCards = new ArrayList<>();
        orderedChosenCards = new ArrayList<>();
    }

    public void setPersonalGoal (Coordinates[] coordinates, Card.Color[] colors) {
        this.personalGoal = new ClientPersonalGoal(coordinates, colors);
    }
    public void setOrderedChosenCards(List<Card> orderedChosenCards) {
        this.orderedChosenCards = orderedChosenCards;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public int getInsertable (int col) {
        return bookshelves.get(me).getInsertable(col);
    }

    public void setPlayers(List<String> players) {
        this.players = players;
        this.numOfPlayers = players.size();
        this.me = this.players.indexOf(myUsername);
    }

    public void createBookshelf(String name, Card[][] grid){
        ClientBookshelf bookshelf = new ClientBookshelf();
        bookshelf.setGrid(grid);
    }

    public void setBookshelves (List<ClientBookshelf> bookshelves) {
        this.bookshelves = bookshelves;
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

    public int getMyIndex() {
        return me;
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
        return bookshelves.get(me).getMaxInsertable();
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }
    public void setCurrentPlayer(int currentPlayerInt, String currentPlayer){
        this.currentPlayer = currentPlayerInt;
    }
    public void setIntCurrentPlayer(int currentPlayer){
        this.currentPlayer = currentPlayer;
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

    public void insertInBookshelf (int col) {
        this.bookshelves.get(me).insert(this.chosenCards, col);
    }
    public void insertInOtherPlayerBookshelf (int col){
        this.bookshelves.get(currentPlayer).insert(this.otherPlayerChosenCards, col);
        this.otherPlayerChosenCards.clear();
    }

    public void removeCards (List<Coordinates> toRemove){
        Coordinates temp;
        Card card;
        Coordinates[] AdjacentCoordinates = new Coordinates[4];
        int j = 0;
        while (j < toRemove.size()) {
            temp = toRemove.get(j);
            card = this.board.removeCardAtCoordinates(temp);
            this.chosenCards.add(card);
            AdjacentCoordinates[0] = new Coordinates(temp.getRow(), temp.getCol() + 1);
            AdjacentCoordinates[1] = new Coordinates(temp.getRow() + 1, temp.getCol());
            AdjacentCoordinates[2] = new Coordinates(temp.getRow(), temp.getCol() - 1);
            AdjacentCoordinates[3] = new Coordinates(temp.getRow() - 1, temp.getCol());
            for (int i = 0; i < 4; i++) {
                if (boardRowColInBound(AdjacentCoordinates[i].getRow(), AdjacentCoordinates[i].getCol(), numOfPlayers) && board.seeCardAtCoordinates(AdjacentCoordinates[i]) != null) {
                    this.board.setToPickable(AdjacentCoordinates[i]);
                }
            }
            j ++;
        }
    }
    public boolean ifCurrentPlayer(){
        return me == currentPlayer;
    }
    public void removeOtherPlayerCards(List<Coordinates> toRemove){
        Coordinates temp;
        Card card;
        Coordinates[] AdjacentCoordinates = new Coordinates[4];
        int j = 0;
        otherPlayerChosenCards.clear();
        while (j < toRemove.size()) {
            temp = toRemove.get(j);
            card = this.board.removeCardAtCoordinates(temp);
            this.otherPlayerChosenCards.add(card);
            AdjacentCoordinates[0] = new Coordinates(temp.getRow(), temp.getCol() + 1);
            AdjacentCoordinates[1] = new Coordinates(temp.getRow() + 1, temp.getCol());
            AdjacentCoordinates[2] = new Coordinates(temp.getRow(), temp.getCol() - 1);
            AdjacentCoordinates[3] = new Coordinates(temp.getRow() - 1, temp.getCol());
            for (int i = 0; i < 4; i++) {
                if (boardRowColInBound(AdjacentCoordinates[i].getRow(), AdjacentCoordinates[i].getCol(), numOfPlayers) && board.seeCardAtCoordinates(AdjacentCoordinates[i]) != null) {
                    this.board.setToPickable(AdjacentCoordinates[i]);
                }
            }
            j ++;
        }
    }
    public void printRoutine(){
        //delete all?
        System.out.print("\033[H\033[2J");
        System.out.flush();
        //me++;
        System.out.println("My username: "+ players.get(me));
        this.board.printBoard();
        System.out.println("My bookshelf:");
        this.bookshelves.get(me).printMyBookshelf();
        for(int i=0 ; i<players.size() ; i++){
            if(i!=me){
                System.out.println(players.get(i)+ "'s bookshelf");
                this.bookshelves.get(i).print();
                for(int j=0 ; j<2 ; j++){
                    System.out.println();
                }
            }
        }
        //System.out.println("chosenCards = " + chosenCards);
        System.out.print("chosenCards = ");
        printCardBuffer(chosenCards);



        //System.out.println("orderedChosenCards = " + orderedChosenCards);
        System.out.print("orderedChosenCards = ");
        printCardBuffer(orderedChosenCards);
        System.out.println("shared goal 1: " + this.sharedGoal1);
        System.out.println("shared goal 2: " + this.sharedGoal2);
        System.out.println("my personal goal:");
        personalGoal.print();
    }

    private void printCardBuffer (List <Card> buffer) {
        System.out.print("[ ");
        for (Card c : buffer) {
            if (c == null) {
                System.out.print("NULL ");
            }
            else {
                switch(c.getColor()) {
                    case WHITE -> {
                        System.out.print(ANSI_WHITE + "□ " + ANSI_RESET);
                    }
                    case BLUE -> {
                        System.out.print(ANSI_BLUE + "□ " + ANSI_RESET);
                    }
                    case ORANGE -> {
                        System.out.print(ANSI_ORANGE + "□ " + ANSI_RESET);
                    }
                    case PINK -> {
                        System.out.print(ANSI_PINK + "□ " + ANSI_RESET);
                    }
                    case CYAN -> {
                        System.out.print(ANSI_CYAN + "□ " + ANSI_RESET);
                    }
                    case GREEN -> {
                        System.out.print(ANSI_GREEN + "□ " + ANSI_RESET);
                    }
                }
            }
        }
        System.out.println("]");
    }

    public void printLobby(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("PLAYERS CONNECTED: ");
        for(int i=0 ; i<players.size() ; i++){
            if(!Objects.equals(players.get(i), myUsername)){
                System.out.println(i+": "+players.get(i));
            }
        }
    }

    public void askForUsername(){
        System.out.println("choose your username. Keep in mind that it has to be unique");
        System.out.println("in case you are reconnecting you should use the username you used to enter the game you disconnected from");
    }

    public void alreadyTakenUsername(){
        System.out.println("the username you choose is not available at the moment, choose another one");
    }

    public void chooseGameMode(){
        System.out.println("you can play these game modes, choose one...");
        System.out.println("(1) play with your friends");
        System.out.println("(2) play with randoms in a game of two");
        System.out.println("(3) play with randoms in a game of three");
        System.out.println("(4) play with randoms in a game of four");
        System.out.println("type 1, 2, 3 or 4");
    }

    public void joinOrCreateGame(){
        System.out.println("(1) to create game");
        System.out.println("(2) to join game");
    }

    public void invalid(){
        System.out.println("Invalid input");
    }

    public void insertGameCode(){
        System.out.println("type the code of the game, the friend that created the game should have it");
    }

    public void numberOfCards(){
        System.out.println("how many cards do you want to pick?");
    }

    public void moreThan3Cards(){
        System.out.println("you can pick at most three cards");
    }

    public void lessThan1Card(){
        System.out.println("you have to pick at least one card");
    }

    public void typeRow(){
        System.out.println("Type row number (0 to 8)");
    }

    public void typeCol(){
        System.out.println("Type col number (0 to 8)");
    }

    public void notInBoundError(){
        System.out.println("coordinates not in bound");
    }

    public void notValidCard(){
        System.out.println("the card has already been taken! please choose another one");
    }

    public void clearChosenCard(){
        chosenCards.clear();
    }
    public void chooseNumberOfPlayer(){
        System.out.println("how many players do you want in your game?");
    }
    public void chooseGameCode(){
        System.out.println("choose game code, tha number must be bigger then 1000 and smaller then 2000");
    }
    public void waitForTheOtherPlayer(){
        System.out.println("you are waiting for the other player");
    }
    public void alreadyTakenGameCode(){
        System.out.println("this game code is already user in another game");
    }

    public void disconnect () {
        System.out.println("an error occurred, you disconnected from the server");
    }
    public void youAreAlone() {
        System.out.println("you are alone in the game, timer started, if nobody enters in 60 seconds you are the winner");
    }
    public void disconnectionAlert(String username){
        System.out.println(username + " left the game");
    }
    public void printGameCode(int gameCode){
        System.out.println("your private game code is: " + gameCode + "share it with your friends to play with them");
    }
    public void printRanking(Map<String,Integer> ranking){
        System.out.println("the game has ended: the final ranking is: " + ranking.toString());
    }
    public void printCurrentScore(int currentScore){
        System.out.println("your current points are: " + currentScore);
    }
    public void printAchieveMessage(int index, int points){
        if(currentPlayer == me){
            System.out.println("you achieved the shared goal: " + index + "obtaining: " + points+ "points");
        }
        else{
            System.out.println("a player has achieved the shared goal number: " + index + "obtaining: " + points+ "points");
        }

    }
    public void printWinnerMessage(){
        System.out.println("Congratulations, you are the winner");
    }
}
