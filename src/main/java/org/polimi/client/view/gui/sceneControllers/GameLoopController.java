package org.polimi.client.view.gui.sceneControllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.polimi.client.ClientBoard;
import org.polimi.client.ClientBookshelf;
import org.polimi.servernetwork.controller.GameController;
import org.polimi.servernetwork.model.Bookshelf;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;
import org.polimi.servernetwork.model.goal.PersonalGoal;
import org.polimi.servernetwork.model.goal.shared_goal.AbstractSharedGoal;
import org.polimi.servernetwork.model.goal.shared_goal.SharedGoal1;

import java.awt.*;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameLoopController {
    //gridpane è 450x450, ogni cella è 50x50 pixel
    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView personalGoalView;
    @FXML
    private ImageView sharedGoal1View;

    @FXML
    private ImageView sharedGoal2View;


    @FXML
    private Node ciao;

    @FXML
    private Image image;

    @FXML
    private Image sharedGoal1Image;

    @FXML
    private Image sharedGoal2Image;


    //usare 25x25 per le tiles nella bookshelf
    @FXML
    private GridPane bookshelfGridPane;

    private ClientBoard board;

    private List<ClientBookshelf> bookshelves;
    private LinkedList<Coordinates> chosenCoordinates;

    public void GameController(int personalGoalIndex, int sharedGoal1Index, int SharedGoal2Index){
        this.chosenCoordinates = new LinkedList<>();
        this.bookshelves = new ArrayList<>();
    }

    @FXML
    public void initialize(){
        board = SceneController.getInstance().getBoard();
        bookshelves = SceneController.getInstance().getBookshelves();

        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                Card card = board.seeCardAtCoordinates(new Coordinates(i,j));
                if(card!=null) {
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
                    ImageView imageView = new ImageView();
                    insertInGridPane(imageView, 50, 50, gridPane, i, j);
                    imageView.setOnMouseClicked((MouseEvent event) -> {
                        if(card.getState() == Card.State.PICKABLE){
                            //prima di pescare le carte, fare una copia della board, nella copia della board prendere le carte via via, cosìcche
                            // se si vuole cambiare scelta basta ricaricare la board originale, farne di nuovo una copia, e procedere a prendeere
                            //le carte ...
                            //nella copia della board rimuovere veramente le carte così si aggiornano i PICKABLE
                            //scelte le carte, inviare le coordinate al server, in modo tale che sia il server a modificare la board orginale,
                            //e riordinarle.

                            /*imageView.setImage(new Image("/images/17_MyShelfie_BGA/scoring_tokens/scoring_back_EMPTY.jpg"));
                            ImageView imageView2 = new ImageView();
                            insertInGridPane(imageView2, 25, 25, bookshelfGridPane, 0, 0);*/
                        }
                    });
                }

            }
        }

        //inizializzazione dei common goals
        System.out.println(SceneController.getInstance().getSharedGoal1Index());
        System.out.println(SceneController.getInstance().getSharedGoal2Index());
        sharedGoal1Image = new Image("/images/17_MyShelfie_BGA/common_goal_cards/"+SceneController.getInstance().getSharedGoal1Index() +".jpg");
        sharedGoal2Image = new Image("/images/17_MyShelfie_BGA/common_goal_cards/"+SceneController.getInstance().getSharedGoal2Index() +".jpg");
        sharedGoal1View = new ImageView(sharedGoal1Image);
        sharedGoal2View = new ImageView(sharedGoal2Image);
    }
    private void insertInGridPane(ImageView imageView, int width, int height, GridPane gridpane, int x, int y){
        imageView.setImage(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        Pane pane = new Pane();
        pane.getChildren().add(imageView);
        gridpane.add(pane, x, y);
    }


    public void col0(){

    }

    public void col1(){

    }

    public void col2(){

    }

    public void col3(){

    }

    public void col4(){

    }


}
