package org.polimi.client.view.gui.sceneControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.polimi.client.ClientBookshelf;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.util.List;

public class BookshelvesViewController {
    @FXML
    private Image image;

    @FXML
    private Label user1;

    @FXML
    private Label user2;

    @FXML
    private Label user3;

    @FXML
    private ImageView shelfie1;

    @FXML
    private ImageView shelfie2;

    @FXML
    private ImageView shelfie3;

    @FXML
    private GridPane bookshelfGridPane1;

    @FXML
    private GridPane bookshelfGridPane2;

    @FXML
    private GridPane bookshelfGridPane3;


    public void initialize(){
        initScene();
    }

    public void refreshScene(){
        initScene();
    }

    private void initScene(){
        int me = SceneController.getInstance().getMyIndex();
        List<ClientBookshelf> bookshelves = SceneController.getInstance().getBookshelves();
        List<String> players = SceneController.getInstance().getPlayers();
        int player1=-1;
        int player2=-1;
        int player3=-1;
        switch(players.size()){
            case 2 -> {
                shelfie1.setVisible(false);
                shelfie3.setVisible(false);
                for(int i=0 ; i<players.size() ; i++){
                    if(i!=me){
                        user2.setText(players.get(i));
                        player2 = i;
                    }
                }

                for(int i = 0; i<5; i++){
                    for(int j= 0; j<6; j++){
                        Card card = bookshelves.get(player2).seeCardAtCoordinates(new Coordinates(j,i));
                        if(card!=null) {
                            loadTileImage(card);
                            ImageView imageView3 = new ImageView();
                            insertInGridPane(imageView3, 25, 25, bookshelfGridPane2, i, j);
                        }
                    }
                }
            }

            case 3 -> {
                shelfie2.setVisible(false);
                for(int i=0 ; i<players.size() ; i++){
                    if(i!=me){
                        if(player1 == -1){
                            user1.setText(players.get(i));
                            player1 = i;
                        }else{
                            user3.setText(players.get(i));
                            player3 = i;
                        }
                    }
                }
                for(int i = 0; i<5; i++){
                    for(int j= 0; j<6; j++){
                        Card card = bookshelves.get(player1).seeCardAtCoordinates(new Coordinates(j,i));
                        if(card!=null) {
                            loadTileImage(card);
                            ImageView imageView3 = new ImageView();
                            insertInGridPane(imageView3, 25, 25, bookshelfGridPane1, i, j);
                        }
                    }
                }
                for(int i = 0; i<5; i++){
                    for(int j= 0; j<6; j++){
                        Card card = bookshelves.get(player3).seeCardAtCoordinates(new Coordinates(j,i));
                        if(card!=null) {
                            loadTileImage(card);
                            ImageView imageView3 = new ImageView();
                            insertInGridPane(imageView3, 25, 25, bookshelfGridPane3, i, j);
                        }
                    }
                }
            }

            case 4 -> {
                for(int i=0 ; i<players.size() ; i++){
                    if(i!=me){
                        if(player1 == -1){
                            user1.setText(players.get(i));
                            player1 = i;
                        }else if(player2 == -1){
                            user2.setText(players.get(i));
                            player2 = i;
                        }else{
                            user3.setText(players.get(i));
                            player3 = i;
                        }
                    }
                }
                for(int i = 0; i<5; i++){
                    for(int j= 0; j<6; j++){
                        Card card = bookshelves.get(player1).seeCardAtCoordinates(new Coordinates(j,i));
                        if(card!=null) {
                            loadTileImage(card);
                            ImageView imageView3 = new ImageView();
                            insertInGridPane(imageView3, 25, 25, bookshelfGridPane1, i, j);
                        }
                    }
                }
                for(int i = 0; i<5; i++){
                    for(int j= 0; j<6; j++){
                        Card card = bookshelves.get(player2).seeCardAtCoordinates(new Coordinates(j,i));
                        if(card!=null) {
                            loadTileImage(card);
                            ImageView imageView3 = new ImageView();
                            insertInGridPane(imageView3, 25, 25, bookshelfGridPane2, i, j);
                        }
                    }
                }
                for(int i = 0; i<5; i++){
                    for(int j= 0; j<6; j++){
                        Card card = bookshelves.get(player3).seeCardAtCoordinates(new Coordinates(j,i));
                        if(card!=null) {
                            loadTileImage(card);
                            ImageView imageView3 = new ImageView();
                            insertInGridPane(imageView3, 25, 25, bookshelfGridPane3, i, j);
                        }
                    }
                }
            }
        }
    }

    private void insertInGridPane(ImageView imageView, int width, int height, GridPane gridp, int x, int y){
        imageView.setImage(this.image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        Pane pane = new Pane();
        pane.getChildren().add(imageView);
        /*if(gridp.getChildren()
                .stream()
                .noneMatch(child -> GridPane.getRowIndex(child) == y && GridPane.getColumnIndex(child) == x))*/
        gridp.add(pane, x, y);
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
}
