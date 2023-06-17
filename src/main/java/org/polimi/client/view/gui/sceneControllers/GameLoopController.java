package org.polimi.client.view.gui.sceneControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GameLoopController {
    @FXML
    private GridPane gridPane;

    @FXML
    private Node ciao;

    @FXML
    public void initialize(){
        Image image = new Image("Gatti1_1.png");
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                ImageView imageView = new ImageView();
                imageView.setImage(image);
                Pane pane = new Pane();
                pane.getChildren().add(imageView);
                gridPane.add(pane, i, j);
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
