package org.polimi.client.view.gui.sceneControllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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

public class GameLoopController {
    //gridpane è 450x450, ogni cella è 50x50 pixel

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
    private GridPane choosenCardsPane;


    @FXML
    private Node ciao;

    @FXML
    private Image image;

    @FXML
    private GridPane goalsPane;

    @FXML
    private Label myScore;


    //usare 25x25 per le tiles nella bookshel
    @FXML
    private GridPane bookshelfGridPane;
    private int choosenCardsDim=0;
    private int myIndex;

    private ClientBoard board;

    private List<ClientBookshelf> bookshelves;
    private LinkedList<Coordinates> chosenCoordinates;

    boolean checkLater = false;

    private boolean startTurnShown;
    private boolean yourTurn;
    //prima instanziare gameloopcontroller (viene chiamato subito initialize), poi settare a true il myturn, poi refreshare
    public void gameLoopInit(){
        this.chosenCoordinates = new LinkedList<>();
        this.bookshelves = new ArrayList<>();
        this.myIndex = SceneController.getInstance().getMyIndex();
        this.yourTurn=false;
        this.startTurnShown=false;
    }

    @FXML
    public void initialize(){
        initializeScene();
    }
    private void initializeScene(){
        yourTurn = SceneController.getInstance().getMyTurn();
        board = SceneController.getInstance().getBoard();
        bookshelves = SceneController.getInstance().getBookshelves();
        resetColumnView();
        initDeleteTileButtons();
        choosenCardsDim = 0;
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                Card card = board.seeCardAtCoordinates(new Coordinates(i,j));
                if(card!=null) {
                    loadTileImage(card);
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
                        if(!startTurnShown){
                            showAlert("IT'S YOUR TURN "+ SceneController.getInstance().getUsername());
                            startTurnShown=true;
                        }
                        //System.out.println("my turn è true, col "+ j+ "e row "+i);
                        int maxInsertable = bookshelves.get(myIndex).getMaxInsertable();
                        this.chosenCoordinates.clear();
                        imageView.setOnMouseClicked((MouseEvent event) -> {
                        if(choosenCardsDim<=2) {
                            if(card.getState() == Card.State.PICKABLE) {
                                switch(choosenCardsDim){
                                    case 0 -> {
                                        loadTileImage(card);
                                        //ImageView imageViewcurr = new ImageView();
                                        insertInGridPane(imageView, 50, 50, choosenCardsPane, choosenCardsDim , 0);
                                        chosenCoordinates.add(new Coordinates(row,col));
                                        choosenCardsDim++;
                                        checkColumn();
                                        tile0.setVisible(true);
                                    }
                                    case 1 ->  {
                                        if(maxInsertable<2){
                                            showAlert("You can't choose that many cards, as there's not enough space in your bookshelf");
                                        } else {
                                            if(!GameRules.areCoordinatesAligned(chosenCoordinates.get(0), new Coordinates(row, col)) ) {
                                                this.checkLater = true;
                                            }
                                            loadTileImage(card);
                                            ///ImageView imageViewcurr = new ImageView();
                                            insertInGridPane(imageView, 50, 50, choosenCardsPane, choosenCardsDim, 0);
                                            chosenCoordinates.add(new Coordinates(row,col));
                                            choosenCardsDim++;
                                            checkColumn();
                                            tile0.setVisible(false);
                                            tile1.setVisible(true);
                                            }
                                        }
                                    case 2 ->{
                                        if(maxInsertable<3){
                                           showAlert("You can't choose that many cards, as there's not enough space in your bookshelf");
                                        } else {
                                            if(     this.areCoordinatesAligned(chosenCoordinates.get(0), chosenCoordinates.get(1) , new Coordinates(row, col))){
                                                loadTileImage(card);
                                                //ImageView imageViewcurr = new ImageView();
                                                insertInGridPane(imageView, 50, 50, choosenCardsPane, choosenCardsDim, 0);
                                                chosenCoordinates.add(new Coordinates(row,col));
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
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Information");
                                alert.setHeaderText(null);
                                alert.setContentText("Not pickable card!");
                                // Display the Alert
                                alert.showAndWait();
                            }
                        }else{
                            showAlert("You cannot choose more than 3 cards per turn");
                        }
                    });
                    }
                    else{
                        startTurnShown=false;
                        System.out.println("my turn è false, col "+ j+ "e row "+i);
                        imageView.setOnMouseClicked(null);
                    }
                }
                else{
                    Pane panewithimageView = retrievePane(gridPane,j,i);
                    if(panewithimageView!=null){
                        gridPane.getChildren().remove(panewithimageView);
                        //panewithimageView = retrievePane(gridPane,j,i);
                        /*System.out.println(panewithimageView.getChildren().size()+ " è la size");
                        Node imageViewtoremove = panewithimageView.getChildren().get(0);
                        if(imageViewtoremove!=null) {
                            if(imageViewtoremove instanceof ImageView){
                                System.out.println("si è una imageView");
                            }
                            //imageViewtoremove.setDisable(true);
                            System.out.println("entrato nel if to imageview to remove");
                            imageViewtoremove.setDisable(true);
                            imageViewtoremove.setVisible(false);
                            panewithimageView.getChildren().remove(imageViewtoremove);
                        }*/
                    }
                }

            }
        }

        //inizializzazione dei common goals
        System.out.println(SceneController.getInstance().getSharedGoal1Index());
        System.out.println(SceneController.getInstance().getSharedGoal2Index());
        image = new Image("/images/17_MyShelfie_BGA/common_goal_cards/"+(SceneController.getInstance().getSharedGoal1Index())+".jpg");
        ImageView imageView = new ImageView();
        insertInGridPane(imageView, 94, 62, goalsPane, 1, 0);
        image = new Image("/images/17_MyShelfie_BGA/common_goal_cards/"+(SceneController.getInstance().getSharedGoal2Index())+".jpg");
        ImageView imageView2 = new ImageView();
        insertInGridPane(imageView2, 94, 62, goalsPane, 2, 0);

        //inizializzazione del personal goal
        image = new Image("/images/17_MyShelfie_BGA/personal_goal_cards/Personal_Goals"+(SceneController.getInstance().getPersonalGoalIndex()+1)+".png");
        ImageView imageView4 = new ImageView();
        insertInGridPane(imageView4, 62, 94, goalsPane,0, 0);


    }
    public void refreshScene(){
        initializeScene();
    }

    public void showBookshelves() throws IOException {
        SceneController.getInstance().switchScenePopUp();
    }

    private void insertInGridPane(ImageView imageView, int width, int height, GridPane gridp, int x, int y){
        imageView.setImage(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        Pane pane = new Pane();
        pane.getChildren().add(imageView);
        /*if(gridp.getChildren()
                .stream()
                .noneMatch(child -> GridPane.getRowIndex(child) == y && GridPane.getColumnIndex(child) == x))*/
        gridp.add(pane, x, y);
    }


    private Pane retrievePane(GridPane gridPane, int j, int i){
         return (Pane) gridPane.getChildren().stream()
                .filter(child -> GridPane.getRowIndex(child) == i && GridPane.getColumnIndex(child) == j)
                .findFirst()
                .orElse(null);
    }


    private void showAlert(String alertinfo){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(alertinfo);
        // Display the Alert
        alert.showAndWait();
    }
    public void col0() throws RemoteException {
        if(this.checkLater && choosenCardsDim==2){
            if(!GameRules.areCoordinatesAligned(chosenCoordinates.get(0), chosenCoordinates.get(1))){
                showAlert("Cards are not aligned");
            }
            return;
        }
        switchOffTiles();
        ClientBookshelf myBookshelf = bookshelves.get(myIndex);
        if(choosenCardsDim==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Choose at least one card");
            alert.showAndWait();
        }
        else if(myBookshelf.getInsertable(0) >= choosenCardsDim){
            List<Card> list = new LinkedList<Card>();
            for(int i=0 ; i<choosenCardsDim ; i++){
                list.add(board.seeCardAtCoordinates(chosenCoordinates.get(i)));
                System.out.println("dentro col0, nel for");
            }
            bookshelves.get(myIndex).insert(list, 0);
            for(int i = 0; i<5; i++){
                for(int j= 0; j<6; j++){
                    Card card = myBookshelf.seeCardAtCoordinates(new Coordinates(j,i));
                    if(card!=null) {
                        loadTileImage(card);
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
            choosenCardsPane.getChildren().clear();
        }
        else{
           showAlert("There's no more space in this column");
        }
    }

    public void col1() throws RemoteException{
        if(this.checkLater && choosenCardsDim==2){
            if(!GameRules.areCoordinatesAligned(chosenCoordinates.get(0), chosenCoordinates.get(1))){
                showAlert("Cards are not aligned");
            }
            return;
        }
        switchOffTiles();
        ClientBookshelf myBookshelf = bookshelves.get(myIndex);
        if(choosenCardsDim==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Choose at least one card");
            alert.showAndWait();
        }
        else if(myBookshelf.getInsertable(1) >= choosenCardsDim){
            List<Card> list = new LinkedList<Card>();
            for(int i=0 ; i<choosenCardsDim ; i++){
                list.add(board.seeCardAtCoordinates(chosenCoordinates.get(i)));
                System.out.println("dentro col0, nel for");
            }
            bookshelves.get(myIndex).insert(list, 1);
            for(int i = 0; i<5; i++){
                for(int j= 0; j<6; j++){
                    Card card = myBookshelf.seeCardAtCoordinates(new Coordinates(j,i));
                    if(card!=null) {
                        loadTileImage(card);
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
            choosenCardsPane.getChildren().clear();
        }
        else{
            showAlert("There's no more space in this column");
        }
    }

    public void col2() throws RemoteException{
        if(this.checkLater && choosenCardsDim==2){
            if(!GameRules.areCoordinatesAligned(chosenCoordinates.get(0), chosenCoordinates.get(1))){
                showAlert("Cards are not aligned");
            }
            return;
        }
        switchOffTiles();
        ClientBookshelf myBookshelf = bookshelves.get(myIndex);
        if(choosenCardsDim==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Choose at least one card");
            alert.showAndWait();
        }
        else if(myBookshelf.getInsertable(2) >= choosenCardsDim){
            List<Card> list = new LinkedList<Card>();
            for(int i=0 ; i<choosenCardsDim ; i++){
                list.add(board.seeCardAtCoordinates(chosenCoordinates.get(i)));
                System.out.println("dentro col0, nel for");
            }
            bookshelves.get(myIndex).insert(list, 2);
            for(int i = 0; i<5; i++){
                for(int j= 0; j<6; j++){
                    Card card = myBookshelf.seeCardAtCoordinates(new Coordinates(j,i));
                    if(card!=null) {
                        loadTileImage(card);
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
            choosenCardsPane.getChildren().clear();
        }
        else{
            showAlert("There's no more space in this column");
        }
    }

    public void col3() throws RemoteException{
        if(this.checkLater && choosenCardsDim==2){
            if(!GameRules.areCoordinatesAligned(chosenCoordinates.get(0), chosenCoordinates.get(1))){
                showAlert("Cards are not aligned");
            }
            return;
        }
        switchOffTiles();
        ClientBookshelf myBookshelf = bookshelves.get(myIndex);
        if(choosenCardsDim==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Choose at least one card");
            alert.showAndWait();
        }
        else if(myBookshelf.getInsertable(3) >= choosenCardsDim){
            List<Card> list = new LinkedList<Card>();
            for(int i=0 ; i<choosenCardsDim ; i++){
                list.add(board.seeCardAtCoordinates(chosenCoordinates.get(i)));
                System.out.println("dentro col0, nel for");
            }
            bookshelves.get(myIndex).insert(list, 3);
            for(int i = 0; i<5; i++){
                for(int j= 0; j<6; j++){
                    Card card = myBookshelf.seeCardAtCoordinates(new Coordinates(j,i));
                    if(card!=null) {
                        loadTileImage(card);
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
            choosenCardsPane.getChildren().clear();
        }
        else{
            showAlert("There's no more space in this column");
        }
    }

    public void col4() throws RemoteException{
        if(this.checkLater && choosenCardsDim==2){
            if(!GameRules.areCoordinatesAligned(chosenCoordinates.get(0), chosenCoordinates.get(1))){
                showAlert("Cards are not aligned");
            }
            return;
        }
        switchOffTiles();
        ClientBookshelf myBookshelf = bookshelves.get(myIndex);
        if(choosenCardsDim==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Choose at least one card");
            alert.showAndWait();
        }
        else if(myBookshelf.getInsertable(4) >= choosenCardsDim){
            List<Card> list = new LinkedList<Card>();
            for(int i=0 ; i<choosenCardsDim ; i++){
                list.add(board.seeCardAtCoordinates(chosenCoordinates.get(i)));
                System.out.println("dentro col0, nel for");
            }
            bookshelves.get(myIndex).insert(list, 4);
            for(int i = 0; i<5; i++){
                for(int j= 0; j<6; j++){
                    Card card = myBookshelf.seeCardAtCoordinates(new Coordinates(j,i));
                    if(card!=null) {
                        loadTileImage(card);
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
            choosenCardsPane.getChildren().clear();
        }
        else{
            showAlert("There's no more space in this column");
        }
    }
    public void deleteTile0(){
        Pane panewithimageView = retrievePane(choosenCardsPane,0,0);
        if(panewithimageView!=null) {
            choosenCardsPane.getChildren().remove(panewithimageView);
        }
        gridPane.add(panewithimageView, chosenCoordinates.get(0).getCol(),chosenCoordinates.get(0).getRow());
        chosenCoordinates.remove(0);
        choosenCardsDim--;
        tile0.setVisible(false);
        checkColumn();
    }
    public void deleteTile1(){
        Pane panewithimageView = retrievePane(choosenCardsPane,1,0);
        if(panewithimageView!=null) {
            choosenCardsPane.getChildren().remove(panewithimageView);
        }
        gridPane.add(panewithimageView, chosenCoordinates.get(1).getCol(),chosenCoordinates.get(1).getRow());
        chosenCoordinates.remove(1);
        choosenCardsDim--;
        tile1.setVisible(false);
        tile0.setVisible(true);
        checkColumn();
        this.checkLater = false;
    }
    public void deleteTile2(){
        Pane panewithimageView = retrievePane(choosenCardsPane,2,0);
        if(panewithimageView!=null) {
            choosenCardsPane.getChildren().remove(panewithimageView);
        }
        gridPane.add(panewithimageView, chosenCoordinates.get(2).getCol(),chosenCoordinates.get(2).getRow());
        chosenCoordinates.remove(2);
        choosenCardsDim--;
        tile2.setVisible(false);
        tile1.setVisible(true);
        checkColumn();
        if(GameRules.areCoordinatesAligned(chosenCoordinates.get(0), chosenCoordinates.get(1))){
            this.checkLater = false;
        }else{
            this.checkLater = true;
        }
    }
    /*private void setDragHandlers(ImageView imageView) {
        final ImageView sourceImageView = imageView;

        imageView.setOnDragDetected(event -> {
            Dragboard dragboard = imageView.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putImage(imageView.getImage());
            dragboard.setContent(content);

            event.consume();
        });

        imageView.setOnDragOver(event -> {
            if (event.getGestureSource() != imageView && event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        imageView.setOnDragEntered(event -> {
            if (event.getGestureSource() != imageView && event.getDragboard().hasImage()) {
                imageView.setStyle("-fx-border-color: red; -fx-border-width: 3;");
            }
            event.consume();
        });

        imageView.setOnDragExited(event -> {
            imageView.setStyle("");
            event.consume();
        });


        imageView.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;

            if (dragboard.hasImage()) {
                ImageView targetImageView = (ImageView) event.getSource();

                // Swap the ImageViews in the GridPane
                int sourceRow = GridPane.getRowIndex(sourceImageView);
                int sourceCol = GridPane.getColumnIndex(sourceImageView);
                int targetRow = GridPane.getRowIndex(targetImageView);
                int targetCol = GridPane.getColumnIndex(targetImageView);

                GridPane.setRowIndex(sourceImageView, targetRow);
                GridPane.setColumnIndex(sourceImageView, targetCol);
                GridPane.setRowIndex(targetImageView, sourceRow);
                GridPane.setColumnIndex(targetImageView, sourceCol);

                success = true;
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }
    */
    private void loadTileImage(Card card){
        switch (card.getColor()) {
            case CYAN -> {
                switch(card.getType()){
                    case 0 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Trofei1.1.png");
                    case 1 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Trofei1.2.png");
                    case 2 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Trofei1.3.png");
                }
            }
            case WHITE -> {
                switch(card.getType()){
                    case 0 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Libri1.1.png");
                    case 1 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Libri1.2.png");
                    case 2 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Libri1.3.png");
                }
            }
            case PINK -> {
                switch(card.getType()){
                    case 0 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Piante1.1.png");
                    case 1 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Piante1.2.png");
                    case 2 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Piante1.3.png");
                }
            }
            case ORANGE -> {
                switch(card.getType()){
                    case 0 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Giochi1.1.png");
                    case 1 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Giochi1.2.png");
                    case 2 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Giochi1.3.png");
                }
            }
            case BLUE -> {
                switch(card.getType()){
                    case 0 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Cornici1.1.png");
                    case 1 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Cornici1.2.png");
                    case 2 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Cornici1.3.png");
                }
            }
            case GREEN -> {
                switch(card.getType()){
                    case 0 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Gatti1_1.png");
                    case 1 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Gatti1.2.png");
                    case 2 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Gatti1.3.png");
                }
            }
        }
    }
    private void switchOffTiles(){
        tile0.setVisible(false);
        tile1.setVisible(false);
        tile2.setVisible(false);
    }
    private void checkColumn(){
        if(bookshelves.get(myIndex).getInsertable(0) < choosenCardsDim){
            column0.setVisible(false);
        }else{
            column0.setVisible(true);
        }
        if(bookshelves.get(myIndex).getInsertable(1) < choosenCardsDim){
            column1.setVisible(false);
            }else{
            column1.setVisible(true);
        }
        if(bookshelves.get(myIndex).getInsertable(2) < choosenCardsDim){
            column2.setVisible(false);
        }else{
            column2.setVisible(true);
        }
        if(bookshelves.get(myIndex).getInsertable(3) < choosenCardsDim){
            column3.setVisible(false);
        }else{
            column3.setVisible(true);
        }
        if(bookshelves.get(myIndex).getInsertable(4) < choosenCardsDim){
            column4.setVisible(false);
        }else{
            column4.setVisible(true);
        }
    }

    private void resetColumnView(){
        if(bookshelves.get(myIndex).getInsertable(0)==0){
            column0.setVisible(false);
        }else{
            column0.setVisible(true);
        }

        if(bookshelves.get(myIndex).getInsertable(1)==0){
            column1.setVisible(false);
        }else{
            column1.setVisible(true);
        }

        if(bookshelves.get(myIndex).getInsertable(2)==0){
            column2.setVisible(false);
        }else{
            column2.setVisible(true);
        }

        if(bookshelves.get(myIndex).getInsertable(3)==0){
            column3.setVisible(false);
        }else{
            column3.setVisible(true);
        }

        if(bookshelves.get(myIndex).getInsertable(4)==0){
            column4.setVisible(false);
        }else{
            column4.setVisible(true);
        }
    }

    private void initDeleteTileButtons(){
        tile0.setVisible(false);
        tile1.setVisible(false);
        tile2.setVisible(false);
    }

    private boolean areCoordinatesAligned(Coordinates c1, Coordinates c2, Coordinates c3){
        return (GameRules.areCoordinatesAligned(c1,c2,c3) || GameRules.areCoordinatesAligned(c1,c3,c2) ||
                GameRules.areCoordinatesAligned(c2,c1,c3) || GameRules.areCoordinatesAligned(c2,c3,c1) ||
                GameRules.areCoordinatesAligned(c3,c1,c2) || GameRules.areCoordinatesAligned(c3,c2,c1));
    }

}
