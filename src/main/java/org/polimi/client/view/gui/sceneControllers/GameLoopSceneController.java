package org.polimi.client.view.gui.sceneControllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;
import org.polimi.GameRules;
import org.polimi.client.ClientBoard;
import org.polimi.client.ClientBookshelf;
import org.polimi.client.GuiClientController;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameLoopSceneController {
    @FXML
    private Image image;

    @FXML
    private Button column0;
    @FXML
    private Button column1;
    @FXML
    private Button column2;
    @FXML
    private Button column3;
    @FXML
    private Button column4;

    @FXML
    private Button tile0;
    @FXML
    private Button tile1;
    @FXML
    private Button tile2;

    @FXML
    private GridPane gridPane;
    @FXML
    private GridPane chosenCardsPane;
    @FXML
    private GridPane goalsPane;
    @FXML
    private GridPane bookshelfGridPane;

    @FXML
    private Label myScore;

    @FXML
    private ImageView imageViewCommon1;
    @FXML
    private ImageView imageViewCommon2;
    @FXML
    private ImageView imageViewPersonal;



    private int choosenCardsDim=0;
    private int myIndex;

    private ClientBoard board;
    private List<ClientBookshelf> bookshelves;
    private ClientBookshelf myBookshelf;
    private LinkedList<Coordinates> chosenCoordinates;

    private LinkedList<Card> chosenCards;

    private boolean checkLater = false;
    private boolean startTurnShown;
    private boolean yourTurn;

    private int myPointScore;

    public void gameLoopInit(){
        this.chosenCoordinates = new LinkedList<>();
        this.bookshelves = new ArrayList<>();
        this.myIndex = SceneController.getInstance().getMyIndex();
        this.chosenCards = new LinkedList<>();
        this.yourTurn=false;
        this.startTurnShown=false;
    }

    /**
     * Javafx initialization method for the game loop scene, it calls 2 inner methods. One that deals
     * with board and bookshelves, the other one with the goals.
     */
    @FXML
    public void initialize(){
        initializeScene();
        initializeGoals();
    }

    /**
     * Method that initializes the whole game loop by adding images to the panes and updates the view of
     * the model whenever the model has changed.
     */
    private void initializeScene(){
        yourTurn = SceneController.getInstance().getMyTurn();
        if(yourTurn){
            if(!startTurnShown){
                showAlert("IT'S YOUR TURN " + SceneController.getInstance().getUsername());
                startTurnShown=true;
            }
        }
        myPointScore = SceneController.getInstance().getMyScore();
        myScore.setText(String.valueOf(myPointScore));
        board = SceneController.getInstance().getBoard();
        bookshelves = SceneController.getInstance().getBookshelves();
        resetColumnView();
        initDeleteTileButtons();
        choosenCardsDim = 0;
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                Card card = board.seeCardAtCoordinates(new Coordinates(i,j));
                if(card!=null) {
                    this.image = loadTileImage(card);
                    ImageView imageView;
                    Pane paneWithImageView = retrievePane(gridPane, j, i);
                    if(paneWithImageView == null){
                        imageView = new ImageView();
                        insertInGridPane(imageView, 50, 50, gridPane, j, i);
                    }else{
                        imageView = (ImageView) paneWithImageView.getChildren().get(0);
                    }
                    int row = i;
                    int col = j;
                    if(yourTurn){
                        int maxInsertable = bookshelves.get(myIndex).getMaxInsertable();
                        this.chosenCoordinates.clear();
                        imageView.setOnMouseClicked((MouseEvent event) -> {
                        if(choosenCardsDim<=2) {
                            if(card.getState() == Card.State.PICKABLE) {
                                switch(choosenCardsDim){
                                    case 0 -> {
                                        this.image = loadTileImage(card);
                                        insertInGridPane(imageView, 50, 50, chosenCardsPane, choosenCardsDim , 0);
                                        chosenCoordinates.add(new Coordinates(row,col));
                                        chosenCards.add(card);
                                        choosenCardsDim++;
                                        checkColumn();
                                        tile0.setVisible(true);
                                    }
                                    case 1 ->  {
                                        System.out.println("this is maxinsertable fater choosing 2 cards"+ maxInsertable);
                                        if(maxInsertable<2){
                                            showAlert("You can't choose that many cards, as there's not enough space in your bookshelf");
                                        } else {
                                            if(!GameRules.areCoordinatesAligned(chosenCoordinates.get(0), new Coordinates(row, col)) ) {
                                                this.checkLater = true;
                                            }
                                            if(areNotAligned(chosenCoordinates.get(0), new Coordinates(row, col))){
                                                showAlert("Cards are not aligned");
                                            }else {
                                                this.image = loadTileImage(card);
                                                insertInGridPane(imageView, 50, 50, chosenCardsPane, choosenCardsDim, 0);
                                                chosenCoordinates.add(new Coordinates(row, col));
                                                chosenCards.add(card);
                                                choosenCardsDim++;
                                                checkColumn();
                                                tile0.setVisible(false);
                                                tile1.setVisible(true);
                                            }
                                            }
                                        }
                                    case 2 ->{
                                        System.out.println("this is maxinsertable after choosing 3 cards"+ maxInsertable);
                                        if(maxInsertable<3){
                                           showAlert("You can't choose that many cards, as there's not enough space in your bookshelf");
                                        } else {
                                            if(     this.areCoordinatesAligned(chosenCoordinates.get(0), chosenCoordinates.get(1) , new Coordinates(row, col))){
                                                this.image = loadTileImage(card);
                                                insertInGridPane(imageView, 50, 50, chosenCardsPane, choosenCardsDim, 0);
                                                chosenCoordinates.add(new Coordinates(row,col));
                                                chosenCards.add(card);
                                                choosenCardsDim++;
                                                checkColumn();
                                                tile1.setVisible(false);
                                                tile2.setVisible(true);
                                            }else{
                                               showAlert("Cards are not aligned");
                                            }
                                        }
                                    }
                                }
                            } else {
                                showAlert("This card is not pickable");
                            }
                        }else{
                            showAlert("You cannot choose more than 3 cards per turn");
                        }
                    });
                    }
                    else{
                        startTurnShown=false;
                        imageView.setOnMouseClicked(null);
                    }
                }
                else{
                    Pane panewithimageView = retrievePane(gridPane,j,i);
                    if(panewithimageView!=null){
                        gridPane.getChildren().remove(panewithimageView);
                    }
                }

            }
        }
    }
    public void refreshScene(){
        initializeScene();
    }

    /**
     * Method that loads the goals' images into the associated panes.
     */
    private void initializeGoals(){
        //inizializzazione dei common goals
        System.out.println(SceneController.getInstance().getSharedGoal1Index());
        System.out.println(SceneController.getInstance().getSharedGoal2Index());
        image = new Image("/images/17_MyShelfie_BGA/common_goal_cards/"+(SceneController.getInstance().getSharedGoal1Index())+".jpg");
        imageViewCommon1 = new ImageView();
        insertInGridPane(imageViewCommon1, 94, 62, goalsPane, 1, 0);
        image = new Image("/images/17_MyShelfie_BGA/common_goal_cards/"+(SceneController.getInstance().getSharedGoal2Index())+".jpg");
        imageViewCommon2 = new ImageView();
        insertInGridPane(imageViewCommon2, 94, 62, goalsPane, 2, 0);

        //inizializzazione del personal goal
        image = new Image("/images/17_MyShelfie_BGA/personal_goal_cards/Personal_Goals"+(SceneController.getInstance().getPersonalGoalIndex()+1)+".png");
        imageViewPersonal = new ImageView();
        insertInGridPane(imageViewPersonal, 62, 94, goalsPane,0, 0);

    }

    /**It launches a new javafx thread and show a new panel showing other players' bookshelf.
     *
     * @throws IOException IOException if an I/O error occurs while switching the scene.
     */
    @FXML
    public void showBookshelves() throws IOException {
        SceneController.getInstance().switchScenePopUp();
    }

    /**
     * Inserts an ImageView into a GridPane at the specified column and row indices. Also gives each pane containing an
     * ImageView an OnMouseEntered effect.
     *
     * @param imageView The ImageView to be inserted.
     * @param width     The desired width of the ImageView.
     * @param height    The desired height of the ImageView.
     * @param gridp     The GridPane in which to insert the ImageView.
     * @param x         The column index where the ImageView should be inserted.
     * @param y         The row index where the ImageView should be inserted.
     */
    private void insertInGridPane(@NotNull ImageView imageView, int width, int height, GridPane gridp, int x, int y) {
        imageView.setImage(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        Pane pane = retrievePane(gridp, x, y);
        if (pane == null) {
            pane = new Pane();

            if (width == 94 || width == 62) {
                Pane finalPane = pane;
                Platform.runLater(() -> {
                    finalPane.setOnMouseEntered(event -> {
                        finalPane.setScaleX(1.5);
                        finalPane.setScaleY(1.5);
                        finalPane.toFront();
                    });

                    finalPane.setOnMouseExited(event -> {
                        finalPane.setScaleX(1.0);
                        finalPane.setScaleY(1.0);
                        finalPane.toBack();
                    });
                });
            }
            pane.getChildren().add(imageView);
            gridp.add(pane, x, y);
        }
        else {
            pane.getChildren().setAll(imageView);
        }
    }

    /**
     * Retrieves a specific Pane from a GridPane based on the specified column and row indices.
     *
     * @param gridPane The GridPane from which to retrieve the Pane.
     * @param j        The column index of the desired Pane.
     * @param i        The row index of the desired Pane.
     * @return The Pane at the specified column and row indices, or {@code null}  if not found.
     */
    private Pane retrievePane(@NotNull GridPane gridPane, int j, int i){
         return (Pane) gridPane.getChildren().stream()
                .filter(child -> GridPane.getRowIndex(child) == i && GridPane.getColumnIndex(child) == j)
                .findFirst()
                .orElse(null);
    }


    /**
     * Method that shows an alert button showing a string which is
     * passed as a parameter.
     * @param alertinfo The string
     */
    @FXML
    private void showAlert(String alertinfo){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText(alertinfo);
            alert.showAndWait();
    }

    /**
     * On action javafx method for inserting your chosen cards into
     * the first column.
     *
     * @throws RemoteException If a remote communication error occurs.
     */
    @FXML
    public void col0() throws Exception {
        if(this.checkLater && choosenCardsDim==2){
            if(!GameRules.areCoordinatesAligned(chosenCoordinates.get(0), chosenCoordinates.get(1))){
                showAlert("Cards are not aligned");
            }
            return;
        }
        switchOffTiles();
        ClientBookshelf myBookshelf = bookshelves.get(myIndex);
        if(choosenCardsDim==0){
            showAlert("Choose at least one card");
        }
        else if(myBookshelf.getInsertable(0) >= choosenCardsDim){
            System.out.println(" col0 insertable"+ myBookshelf.getInsertable(0));
            List<Card> list = new LinkedList<Card>();
            for(int i=0 ; i<choosenCardsDim ; i++){
                list.add(board.seeCardAtCoordinates(chosenCoordinates.get(i)));
            }
            bookshelves.get(myIndex).insert(list, 0);
            for(int i = 0; i<5; i++){
                for(int j= 0; j<6; j++){
                    Card card = myBookshelf.seeCardAtCoordinates(new Coordinates(j,i));
                    if(card!=null) {
                        this.image = loadTileImage(card);
                        ImageView imageView3 = new ImageView();
                        insertInGridPane(imageView3, 25, 25, bookshelfGridPane, i, j);
                    }
                }
            }
            SceneController.getInstance().setChosencol(0);
            SceneController.getInstance().setChosenCardsCoords(chosenCoordinates);
            SceneController.getInstance().setChosenCards(list);
            GuiClientController.getNotified("chosencards");
            for(Coordinates coor: chosenCoordinates){
                Pane panewithimageView = retrievePane(gridPane,coor.getCol(),coor.getRow());
                if(panewithimageView!=null){
                    gridPane.getChildren().remove(panewithimageView);
                }
            }
            chosenCardsPane.getChildren().clear();
            chosenCards.clear();
        }
        else{
           showAlert("There's no more space in this column");
        }
    }

    /**
     * On action javafx method for inserting your chosen cards into
     * the second column.
     * @throws RemoteException If a remote communication error occurs.
     */
    @FXML
    public void col1() throws Exception {
        if(this.checkLater && choosenCardsDim==2){
            if(!GameRules.areCoordinatesAligned(chosenCoordinates.get(0), chosenCoordinates.get(1))){
                showAlert("Cards are not aligned");
            }
            return;
        }
        switchOffTiles();
        ClientBookshelf myBookshelf = bookshelves.get(myIndex);
        if(choosenCardsDim==0){
            showAlert("Choose at least one card");
        }
        else if(myBookshelf.getInsertable(1) >= choosenCardsDim){
            List<Card> list = new LinkedList<Card>();
            for(int i=0 ; i<choosenCardsDim ; i++){
                list.add(board.seeCardAtCoordinates(chosenCoordinates.get(i)));
            }
            bookshelves.get(myIndex).insert(list, 1);
            for(int i = 0; i<5; i++){
                for(int j= 0; j<6; j++){
                    Card card = myBookshelf.seeCardAtCoordinates(new Coordinates(j,i));
                    if(card!=null) {
                        this.image = loadTileImage(card);
                        ImageView imageView3 = new ImageView();
                        insertInGridPane(imageView3, 25, 25, bookshelfGridPane, i, j);
                    }
                }
            }
            this.checkLater = false;
            SceneController.getInstance().setChosencol(1);
            SceneController.getInstance().setChosenCardsCoords(chosenCoordinates);
            SceneController.getInstance().setChosenCards(list);
            GuiClientController.getNotified("chosencards");
            for(Coordinates coor: chosenCoordinates){
                Pane panewithimageView = retrievePane(gridPane,coor.getCol(),coor.getRow());
                if(panewithimageView!=null){
                    gridPane.getChildren().remove(panewithimageView);
                }
            }
            chosenCardsPane.getChildren().clear();
            chosenCards.clear();
        }
        else{
            showAlert("There's no more space in this column");
        }
    }

    /**
     * On action javafx method for inserting your chosen cards into
     * the third column.
     * @throws RemoteException If a remote communication error occurs.
     */
    @FXML
    public void col2() throws Exception {
        if(this.checkLater && choosenCardsDim==2){
            if(!GameRules.areCoordinatesAligned(chosenCoordinates.get(0), chosenCoordinates.get(1))){
                showAlert("Cards are not aligned");
            }
            return;
        }
        switchOffTiles();
        ClientBookshelf myBookshelf = bookshelves.get(myIndex);
        if(choosenCardsDim==0){
            showAlert("Choose at least one card");
        }
        else if(myBookshelf.getInsertable(2) >= choosenCardsDim){
            List<Card> list = new LinkedList<Card>();
            for(int i=0 ; i<choosenCardsDim ; i++){
                list.add(board.seeCardAtCoordinates(chosenCoordinates.get(i)));
            }
            bookshelves.get(myIndex).insert(list, 2);
            for(int i = 0; i<5; i++){
                for(int j= 0; j<6; j++){
                    Card card = myBookshelf.seeCardAtCoordinates(new Coordinates(j,i));
                    if(card!=null) {
                        this.image = loadTileImage(card);
                        ImageView imageView3 = new ImageView();
                        insertInGridPane(imageView3, 25, 25, bookshelfGridPane, i, j);
                    }
                }
            }
            this.checkLater = false;
            SceneController.getInstance().setChosencol(2);
            SceneController.getInstance().setChosenCardsCoords(chosenCoordinates);
            SceneController.getInstance().setChosenCards(list);
            GuiClientController.getNotified("chosencards");
            for(Coordinates coor: chosenCoordinates){
                Pane panewithimageView = retrievePane(gridPane,coor.getCol(),coor.getRow());
                if(panewithimageView!=null){
                    gridPane.getChildren().remove(panewithimageView);
                }
            }
            chosenCardsPane.getChildren().clear();
            chosenCards.clear();
        }
        else{
            showAlert("There's no more space in this column");
        }
    }

    /**
     * On action javafx method for inserting your chosen cards into
     * the fourth column.
     * @throws RemoteException If a remote communication error occurs.
     */
    @FXML
    public void col3() throws Exception {
        if(this.checkLater && choosenCardsDim==2){
            if(!GameRules.areCoordinatesAligned(chosenCoordinates.get(0), chosenCoordinates.get(1))){
                showAlert("Cards are not aligned");
            }
            return;
        }
        switchOffTiles();
        ClientBookshelf myBookshelf = bookshelves.get(myIndex);
        if(choosenCardsDim==0){
            showAlert("Choose at least one card");
        }
        else if(myBookshelf.getInsertable(3) >= choosenCardsDim){
            List<Card> list = new LinkedList<Card>();
            for(int i=0 ; i<choosenCardsDim ; i++){
                list.add(board.seeCardAtCoordinates(chosenCoordinates.get(i)));
            }
            bookshelves.get(myIndex).insert(list, 3);
            for(int i = 0; i<5; i++){
                for(int j= 0; j<6; j++){
                    Card card = myBookshelf.seeCardAtCoordinates(new Coordinates(j,i));
                    if(card!=null) {
                        this.image = loadTileImage(card);
                        ImageView imageView3 = new ImageView();
                        insertInGridPane(imageView3, 25, 25, bookshelfGridPane, i, j);
                    }
                }
            }
            this.checkLater = false;
            SceneController.getInstance().setChosencol(3);
            SceneController.getInstance().setChosenCardsCoords(chosenCoordinates);
            SceneController.getInstance().setChosenCards(list);
            GuiClientController.getNotified("chosencards");
            for(Coordinates coor: chosenCoordinates){
                Pane panewithimageView = retrievePane(gridPane,coor.getCol(),coor.getRow());
                if(panewithimageView!=null){
                    gridPane.getChildren().remove(panewithimageView);
                }
            }
            chosenCardsPane.getChildren().clear();
            chosenCards.clear();
        }
        else{
            showAlert("There's no more space in this column");
        }
    }

    /**
     * On action javafx method for inserting your chosen cards into
     * the fifth column.
     * @throws RemoteException If a remote communication error occurs.
     */
    @FXML
    public void col4() throws Exception {
        if(this.checkLater && choosenCardsDim==2){
            if(!GameRules.areCoordinatesAligned(chosenCoordinates.get(0), chosenCoordinates.get(1))){
                showAlert("Cards are not aligned");
            }
            return;
        }
        switchOffTiles();
        ClientBookshelf myBookshelf = bookshelves.get(myIndex);
        if(choosenCardsDim==0){
            showAlert("Choose at least one card");
        }
        else if(myBookshelf.getInsertable(4) >= choosenCardsDim){
            List<Card> list = new LinkedList<Card>();
            for(int i=0 ; i<choosenCardsDim ; i++){
                list.add(board.seeCardAtCoordinates(chosenCoordinates.get(i)));
            }
            bookshelves.get(myIndex).insert(list, 4);
            for(int i = 0; i<5; i++){
                for(int j= 0; j<6; j++){
                    Card card = myBookshelf.seeCardAtCoordinates(new Coordinates(j,i));
                    if(card!=null) {
                        this.image = loadTileImage(card);
                        ImageView imageView3 = new ImageView();
                        insertInGridPane(imageView3, 25, 25, bookshelfGridPane, i, j);
                    }
                }
            }
            this.checkLater = false;
            SceneController.getInstance().setChosencol(4);
            SceneController.getInstance().setChosenCardsCoords(chosenCoordinates);
            SceneController.getInstance().setChosenCards(list);
            GuiClientController.getNotified("chosencards");
            for(Coordinates coor: chosenCoordinates){
                Pane panewithimageView = retrievePane(gridPane,coor.getCol(),coor.getRow());
                if(panewithimageView!=null){
                    gridPane.getChildren().remove(panewithimageView);
                }
            }
            chosenCardsPane.getChildren().clear();
            chosenCards.clear();
        }
        else{
            showAlert("There's no more space in this column");
        }
    }

    /**
     * On action javafx method for deleting the first card you picked during
     * your turn.
     */
    @FXML
    public void deleteTile0(){
        Pane paneWithImageView = retrievePane(chosenCardsPane,0,0);
        chosenCardsPane.getChildren().remove(paneWithImageView);
        image = loadTileImage(chosenCards.remove(0));
        insertInGridPane((ImageView) paneWithImageView.getChildren().get(0), 50, 50, gridPane, chosenCoordinates.get(0).getCol(),chosenCoordinates.get(0).getRow() );
        chosenCoordinates.remove(0);
        choosenCardsDim--;
        tile0.setVisible(false);
        checkColumn();
    }

    /**
     * On action javafx method for deleting the second card you picked during
     * your turn.
     */
    @FXML
    public void deleteTile1(){
        Pane paneWithImageView = retrievePane(chosenCardsPane,1,0);
        chosenCardsPane.getChildren().remove(paneWithImageView);
        image = loadTileImage(chosenCards.remove(1));
        insertInGridPane((ImageView) paneWithImageView.getChildren().get(0), 50, 50, gridPane, chosenCoordinates.get(1).getCol(),chosenCoordinates.get(1).getRow() );
        chosenCoordinates.remove(1);
        choosenCardsDim--;
        tile1.setVisible(false);
        tile0.setVisible(true);
        checkColumn();
        this.checkLater = false;
    }
    /**
     * On action javafx method for deleting the third card you picked during
     * your turn.
     */
    @FXML
    public void deleteTile2(){
        Pane paneWithImageView = retrievePane(chosenCardsPane,2,0);
        chosenCardsPane.getChildren().remove(paneWithImageView);
        image = loadTileImage(chosenCards.remove(2));
        insertInGridPane((ImageView) paneWithImageView.getChildren().get(0), 50, 50, gridPane, chosenCoordinates.get(2).getCol(),chosenCoordinates.get(2).getRow() );
        chosenCoordinates.remove(2);
        choosenCardsDim--;
        tile2.setVisible(false);
        tile1.setVisible(true);
        checkColumn();
        this.checkLater = !GameRules.areCoordinatesAligned(chosenCoordinates.get(0), chosenCoordinates.get(1));
    }

    /**
     * Loads and returns the corresponding Image for a given Card.
     *
     * @param card The Card for which to load the Image.
     * @return The Image associated with the Card.
     */
    public static Image loadTileImage(Card card){
        Image imageToReturn = null;
        switch (card.getColor()) {
            case CYAN -> {
                switch(card.getType()){
                    case 0 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Trofei1.1.png");
                    case 1 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Trofei1.2.png");
                    case 2 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Trofei1.3.png");
                }
            }
            case WHITE -> {
                switch(card.getType()){
                    case 0 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Libri1.1.png");
                    case 1 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Libri1.2.png");
                    case 2 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Libri1.3.png");
                }
            }
            case PINK -> {
                switch(card.getType()){
                    case 0 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Piante1.1.png");
                    case 1 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Piante1.2.png");
                    case 2 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Piante1.3.png");
                }
            }
            case ORANGE -> {
                switch(card.getType()){
                    case 0 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Giochi1.1.png");
                    case 1 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Giochi1.2.png");
                    case 2 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Giochi1.3.png");
                }
            }
            case BLUE -> {
                switch(card.getType()){
                    case 0 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Cornici1.1.png");
                    case 1 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Cornici1.2.png");
                    case 2 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Cornici1.3.png");
                }
            }
            case GREEN -> {
                switch(card.getType()){
                    case 0 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Gatti1_1.png");
                    case 1 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Gatti1.2.png");
                    case 2 -> imageToReturn = new Image("/images/17_MyShelfie_BGA/item_tiles/Gatti1.3.png");
                }
            }
        }
        return imageToReturn;
    }

    /**
     * Method that sets to non-visible all undo buttons, it is called
     * only when you play a valid insertion into a column.
     */
    private void switchOffTiles(){
        tile0.setVisible(false);
        tile1.setVisible(false);
        tile2.setVisible(false);
    }

    /**It checks if the cardinality of the chosen cards buffer is compatible
     * with the columns, the buttons of non-compatible columns will disappear.
     *
     */
    private void checkColumn(){
        column0.setVisible(bookshelves.get(myIndex).getInsertable(0) >= choosenCardsDim);
        column1.setVisible(bookshelves.get(myIndex).getInsertable(1) >= choosenCardsDim);
        column2.setVisible(bookshelves.get(myIndex).getInsertable(2) >= choosenCardsDim);
        column3.setVisible(bookshelves.get(myIndex).getInsertable(3) >= choosenCardsDim);
        column4.setVisible(bookshelves.get(myIndex).getInsertable(4) >= choosenCardsDim);
    }

    /**
     * Resets the visibility of the undo choice buttons based on the insertable slots in the bookshelf.
     * If a column has insertable slots, the button above it will be visible; otherwise, it will be hidden.
     */
    private void resetColumnView(){
        column0.setVisible(bookshelves.get(myIndex).getInsertable(0) != 0);

        column1.setVisible(bookshelves.get(myIndex).getInsertable(1) != 0);

        column2.setVisible(bookshelves.get(myIndex).getInsertable(2) != 0);

        column3.setVisible(bookshelves.get(myIndex).getInsertable(3) != 0);

        column4.setVisible(bookshelves.get(myIndex).getInsertable(4) != 0);
    }


    /**
     * This method initialize to non-visible the undo choice for already chosen cards.
     */
    private void initDeleteTileButtons(){
        tile0.setVisible(false);
        tile1.setVisible(false);
        tile2.setVisible(false);
    }

    /**
     * Checks if three coordinates are aligned in a straight line.
     *
     * @param c1 The first coordinate.
     * @param c2 The second coordinate.
     * @param c3 The third coordinate.
     * @return {@code true} if the coordinates are aligned, {@code false} otherwise.
     */
    private boolean areCoordinatesAligned(Coordinates c1, Coordinates c2, Coordinates c3){
        return (GameRules.areCoordinatesAligned(c1,c2,c3) || GameRules.areCoordinatesAligned(c1,c3,c2) ||
                GameRules.areCoordinatesAligned(c2,c1,c3) || GameRules.areCoordinatesAligned(c2,c3,c1) ||
                GameRules.areCoordinatesAligned(c3,c1,c2) || GameRules.areCoordinatesAligned(c3,c2,c1));
    }
    public boolean areNotAligned(Coordinates coor1, Coordinates coor2){
        if((coor1.getCol()!=coor2.getCol() && (coor1.getRow()!=coor2.getRow()))){
            return true;
        }else{
            return false;
        }
    }
    /**
     * Refills the client's bookshelf with the cards that were previously stored.
     * This method retrieves the client's bookshelf and inserts the cards into the bookshelf grid.
     * Each non-null card is represented by an ImageView and inserted into the bookshelf grid at the corresponding coordinates.
     * This method is called when a client reconnects to the game.
     */
    public void reconnect(){
        ClientBookshelf myBookshelf = bookshelves.get(myIndex);
        for(int i = 0; i<5; i++){
            for(int j= 0; j<6; j++){
                int row = i;
                int col = j;
                Card card = myBookshelf.seeCardAtCoordinates(new Coordinates(j,i));
                if(card!=null) {
                    this.image = loadTileImage(card);
                    ImageView imageView3 = new ImageView();
                    Platform.runLater(()->{
                        insertInGridPane(imageView3, 25, 25, bookshelfGridPane, row, col);
                    });
                }
            }
        }
    }

    public void setMyPointScore(int myPointScore) {
        this.myPointScore = myPointScore;
    }

    public int getMyPointScore() {
        return myPointScore;
    }
}
