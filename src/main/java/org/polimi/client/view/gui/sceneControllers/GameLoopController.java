package org.polimi.client.view.gui.sceneControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.polimi.client.ClientBoard;
import org.polimi.servernetwork.controller.GameController;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.awt.*;

public class GameLoopController {
    @FXML
    private GridPane gridPane;

    @FXML
    private Node ciao;

    @FXML
    private Image image;

    private ClientBoard board;


    public void GameController(){
    }

    @FXML
    public void initialize(){
        board = SceneController.getInstance().getBoard();
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
                                case 0 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Gatti1.1.png");
                                case 1 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Gatti1.2.png");
                                case 2 -> image = new Image("/images/17_MyShelfie_BGA/item_tiles/Gatti1.3.png");
                            }
                        }
                    }
                    ImageView imageView = new ImageView();
                    imageView.setImage(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    Pane pane = new Pane();
                    pane.getChildren().add(imageView);
                    gridPane.add(pane, i, j);
                }

            }
        }
    }


    public void choosedCard(){

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
