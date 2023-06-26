package org.polimi.client.view.gui.sceneControllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.polimi.client.ClientBoard;
import org.polimi.client.ClientBookshelf;
import org.polimi.client.ClientPersonalGoal;
import org.polimi.client.GuiClientController;
import org.polimi.client.view.gui.Gui;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static SceneController instance;


    private ClientBoard board;
    private int numOfPlayers;
    private List<String> players;
    private String myUsername;

    private List<ClientBookshelf> bookshelves;
    private int me; //my player index
    private int currentPlayer;
    private int lastPlayerInserted;
    private List<Card> chosenCards;

    private List<Coordinates> chosenCardsCoords;
    private List<Card> otherPlayerChosenCards;
    private List<Coordinates> orderedChosenCards;

    private String sharedGoal1;
    private String sharedGoal2;
    //to modify (has to print a mini bookshelf)
    private ClientPersonalGoal personalGoal;

    private int sharedGoal1Index;

    private int sharedGoal2Index;

    private int personalGoalIndex;

    private int chosencol;

    private boolean myTurn;

    private GameLoopController gameLoopController;



    public SceneController(){
        board = null;
        bookshelves = new ArrayList<ClientBookshelf>();
        players = new ArrayList<String>();
        lastPlayerInserted = 0;
        me = 0;
        chosenCards = new ArrayList<>();
        otherPlayerChosenCards = new ArrayList<>();
        orderedChosenCards = new LinkedList<>();

    }
    public static SceneController getInstance() {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
        Platform.runLater(() -> {
            gameLoopController.refreshScene();
        });


    }

    public boolean getMyTurn(){
        return this.myTurn;
    }

    public Stage getStage() {
        return stage;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyUsername(String username){
        this.myUsername = username;
    }

    public int getMyIndex(){
        return this.players.indexOf(myUsername);
    }
    public void switchScene(ActionEvent event, String sceneName) throws IOException {
        String ref = "/scenesfxml/" + sceneName + ".fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ref));
        this.root = loader.load();

        if (sceneName.equals("game_loop")) {
            this.gameLoopController = loader.getController();
            GuiClientController.getNotified("createdgameloop");
        }

        stage = (Stage) ((Node) (event != null ? event.getSource() : null)).getScene().getWindow();

        if (sceneName.equals("login_scene")) {
            scene = new Scene(root, 693, 200);
            stage.setResizable(false);
        } else {
            scene = new Scene(root);
        }

        stage.setScene(scene);
        stage.show();
    }

    public void switchScenePopUp() throws IOException {
        String sceneName = "bookshelves_view_scene";
        String ref = "/scenesfxml/" + sceneName + ".fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ref));

        // Create a new scene from the FXML file
        AnchorPane anchorPane = loader.load();
        Scene popUpScene= new Scene(anchorPane);
        Stage popUpStage = new Stage();

        // Show the new scene without closing the old one
        Platform.runLater(() -> {
            popUpStage.setScene(popUpScene);
            popUpStage.showAndWait();
        });
    }


    public void setPersonalGoalIndex(int personalGoalIndex) {
        this.personalGoalIndex = personalGoalIndex;
    }

    public int getPersonalGoalIndex() {
        return personalGoalIndex;
    }

    public void setChosencol(int chosencol) {
        this.chosencol = chosencol;
    }

    public int getChosencol() {
        return chosencol;
    }

    //lancia null pointer

    public String getUsername() {
        return myUsername;
    }

    public void switchScene3(Stage stage, String sceneName, Parent root) throws IOException {
        this.root = root;

        Scene newScene = null;

        if (sceneName.equals("login_scene")) {
            newScene = new Scene(root, 693, 200);
            stage.setResizable(false);}
        else{
            newScene = new Scene(root);
        }

        // Switch scenes on the JavaFX Application Thread
        Scene finalNewScene = newScene;
        Platform.runLater(() -> {
            stage.setScene(finalNewScene);
            stage.show();
        });
    }
    public void setPlayers(List<String> players) {
        this.players = players;
        this.numOfPlayers = players.size();
        this.me = this.players.indexOf(myUsername);
    }

    public List<Card> getChosenCards() {
        return chosenCards;
    }

    public List<Coordinates> getChosenCardsCoords() {
        return chosenCardsCoords;
    }

    public void setChosenCardsCoords(List<Coordinates> chosenCardsCoords) {
        this.chosenCardsCoords = chosenCardsCoords;
    }

    public void setBookshelves(List<ClientBookshelf> bookshelves) {
        this.bookshelves = bookshelves;
    }

    public void setChosenCards(List<Card> chosenCards) {
        this.chosenCards = chosenCards;
    }
    //setta correttamente
    public void setOtherPlayerChosenCards(List<Card> otherPlayerChosenCards) {
        if(otherPlayerChosenCards.size()<1 || otherPlayerChosenCards.size()>3){
            throw new RuntimeException("something wrong with the size");
        }
        else{
            this.otherPlayerChosenCards = otherPlayerChosenCards;
        }
    }
    public List<Card> getOtherPlayerChosenCards() {
        System.out.println("chiamato getotherplayerchosencards, ");
        for(Card card : otherPlayerChosenCards){
            System.out.println("eccola dell'altro client : "+ card);
        }
        return otherPlayerChosenCards;
    }
    public void setOrderedChosenCards(List<Coordinates> orderedChosenCards) {
        this.orderedChosenCards = orderedChosenCards;
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
        this.sharedGoal1Index = i+1;
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
        this.sharedGoal2Index = i+1;
    }

    public int getSharedGoal1Index() {
        return sharedGoal1Index;
    }

    public int getSharedGoal2Index() {
        return sharedGoal2Index;
    }
    public void setPersonalGoal (Coordinates[] coordinates, Card.Color[] colors) {
        this.personalGoal = new ClientPersonalGoal(coordinates, colors);
    }

    public void setBoard(ClientBoard board) {
        this.board = board;
    }

    public ClientBoard getBoard(){
        return this.board;
    }

    public List<ClientBookshelf> getBookshelves(){
        return this.bookshelves;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = players.indexOf(currentPlayer);
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setGameLoopController(GameLoopController gameLoopController) {
        this.gameLoopController = gameLoopController;
    }
    public List<String> getPlayers() {
        return players;
    }
}
