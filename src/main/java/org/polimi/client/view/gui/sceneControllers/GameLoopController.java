package org.polimi.client.view.gui.sceneControllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.polimi.GameRules;
import org.polimi.client.ClientBoard;
import org.polimi.client.ClientBookshelf;
import org.polimi.client.GuiClientController;
import org.polimi.messages.Message;
import org.polimi.servernetwork.controller.GameController;
import org.polimi.servernetwork.model.Bookshelf;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;
import org.polimi.servernetwork.model.goal.PersonalGoal;
import org.polimi.servernetwork.model.goal.shared_goal.AbstractSharedGoal;
import org.polimi.servernetwork.model.goal.shared_goal.SharedGoal1;

import java.awt.*;
import java.awt.print.Book;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameLoopController {
    //gridpane è 450x450, ogni cella è 50x50 pixel
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


    //usare 25x25 per le tiles nella bookshel
    @FXML
    private GridPane bookshelfGridPane;
    private int choosenCardsDim=0;
    private int myIndex;


    private ClientBoard board;

    private List<ClientBookshelf> bookshelves;
    private LinkedList<Coordinates> chosenCoordinates;

    private boolean yourTurn;
    //prima instanziare gameloopcontroller (viene chiamato subito initialize), poi settare a true il myturn, poi refreshare
    public void gameLoopInit(){
        this.chosenCoordinates = new LinkedList<>();
        this.bookshelves = new ArrayList<>();
        this.myIndex = SceneController.getInstance().getMyIndex();
    }

    @FXML
    public void initialize(){
        initializeScene();
    }
    private void initializeScene(){
        yourTurn = SceneController.getInstance().getMyTurn();
        board = SceneController.getInstance().getBoard();
        bookshelves = SceneController.getInstance().getBookshelves();
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                Card card = board.seeCardAtCoordinates(new Coordinates(i,j));
                if(card!=null) {
                    loadTileImage(card);
                    ImageView imageView = new ImageView();
                    insertInGridPane(imageView, 50, 50, gridPane, j, i);
                    int row = i;
                    int col = j;
                    if(yourTurn){
                        System.out.println("é il mio turno");
                        imageView.setOnMouseClicked((MouseEvent event) -> {
                        if(choosenCardsDim<=2) {
                            if(card.getState() == Card.State.PICKABLE) {
                                switch(choosenCardsDim){
                                    case 0 ->{
                                        loadTileImage(card);
                                        ImageView imageViewcurr = new ImageView();
                                        setDragHandlers(imageViewcurr);
                                        insertInGridPane(imageViewcurr, 50, 50, choosenCardsPane, choosenCardsDim , 0);
                                        chosenCoordinates.add(new Coordinates(row,col));
                                        System.out.println("fatto chosencoordinates .add, prima di dim++");
                                        choosenCardsDim++;
                                    }
                                    case 1 ->  {
                                        if(GameRules.areCoordinatesAligned(chosenCoordinates.get(0), new Coordinates(row, col))){
                                            loadTileImage(card);
                                            ImageView imageViewcurr = new ImageView();
                                            setDragHandlers(imageViewcurr);
                                            insertInGridPane(imageViewcurr, 50, 50, choosenCardsPane, choosenCardsDim, 0);
                                            chosenCoordinates.add(new Coordinates(row,col));
                                            choosenCardsDim++;
                                        }else{
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle("Information");
                                            alert.setHeaderText(null);
                                            alert.setContentText("Cards are not aligned");
                                            // Display the Alert
                                            alert.showAndWait();
                                        }
                                    }
                                    case 2 ->{
                                        if(GameRules.areCoordinatesAligned(chosenCoordinates.get(0), chosenCoordinates.get(1) , new Coordinates(row, col))){
                                            loadTileImage(card);
                                            ImageView imageViewcurr = new ImageView();
                                            setDragHandlers(imageViewcurr);
                                            insertInGridPane(imageViewcurr, 50, 50, choosenCardsPane, choosenCardsDim, 0);
                                            chosenCoordinates.add(new Coordinates(row,col));
                                            choosenCardsDim++;
                                        }else{
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle("Information");
                                            alert.setHeaderText(null);
                                            alert.setContentText("Cards are not aligned");
                                            // Display the Alert
                                            alert.showAndWait();
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
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information");
                            alert.setHeaderText(null);
                            alert.setContentText("You cannot choose more than 3 cards per turn");
                            // Display the Alert
                            alert.showAndWait();
                        }
                    });
                    }
                    else{
                        System.out.println("non é il mio turnno");
                        imageView.setOnMouseClicked(null);
                    }
                }
                else{
                    Node imageViewtoremove = retrieveImageView(gridPane,i ,j);
                    if(imageViewtoremove!=null) {
                        imageViewtoremove.setDisable(true);
                        imageViewtoremove.setVisible(false);
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

    private void insertInGridPane(ImageView imageView, int width, int height, GridPane gridpane, int x, int y){
        imageView.setImage(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        Pane pane = new Pane();
        pane.getChildren().add(imageView);
        gridpane.add(pane, x, y);
    }
    private Node retrieveImageView(GridPane gridPane, int j, int i){
         return  gridPane.getChildren().stream()
                .filter(child -> GridPane.getRowIndex(child) == i && GridPane.getColumnIndex(child) == j)
                .findFirst()
                .orElse(null);
    }
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


    public void col0() throws RemoteException {
        if(bookshelves.get(myIndex).getInsertable(0) >= choosenCardsDim){
            List<Card> list = new LinkedList<Card>();
            for(int i=0 ; i<choosenCardsDim ; i++){
                list.add(board.seeCardAtCoordinates(chosenCoordinates.get(i)));
                System.out.println("dentro col0, nel for");
            }
            bookshelves.get(myIndex).insert(list, 0);
            for(int i = 0; i<5; i++){
                for(int j= 0; j<6; j++){
                    Card card = bookshelves.get(myIndex).seeCardAtCoordinates(new Coordinates(j,i));
                    if(card!=null) {
                        loadTileImage(card);
                        ImageView imageView3 = new ImageView();
                        insertInGridPane(imageView3, 25, 25, bookshelfGridPane, i, j);
                    }
                }
            }
            GuiClientController.getNotified("chosencards");
            SceneController.getInstance().setChosencol(0);
            SceneController.getInstance().setChosenCards(chosenCoordinates);
        }
    }

    public void col1(){

    }

    public void col2(){

    }

    public void col3(){

    }

    public void col4(){

    }
    private void setDragHandlers(ImageView imageView) {
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


}
