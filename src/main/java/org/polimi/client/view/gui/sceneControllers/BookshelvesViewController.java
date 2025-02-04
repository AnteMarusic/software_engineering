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
    //shown under shelfie1
    @FXML
    private ImageView shared1Player1;

    @FXML
    private ImageView shared2Player1;
    //shownb under shelfie2
    @FXML
    private ImageView shared1Player2;

    @FXML
    private ImageView shared2Player2;
    //shown under shelfie3
    @FXML
    private ImageView shared1Player3;

    @FXML
    private ImageView shared2Player3;



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

    /**
     * Initializes the scene by setting up the bookshelves and players based on the current state.
     * This method is called when the scene is first loaded.
     */
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
                for(int i=0 ; i<players.size() ; i++){
                    if(i!=me){
                        this.image = loadPointImage(SceneController.getInstance().getLastSharedPoint1(i));
                        shared1Player2 = new ImageView(this.image);

                        this.image = loadPointImage(SceneController.getInstance().getLastSharedPoint2(i));
                        shared2Player2 = new ImageView(this.image);
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
                for(int i=0 ; i<players.size() ; i++){
                    if(i==player1){
                        this.image = loadPointImage(SceneController.getInstance().getLastSharedPoint1(i));
                        shared1Player1 = new ImageView(this.image);

                        this.image = loadPointImage(SceneController.getInstance().getLastSharedPoint2(i));
                        shared2Player1 = new ImageView(this.image);
                    }

                    if(i==player3){
                        this.image = loadPointImage(SceneController.getInstance().getLastSharedPoint1(i));
                        shared1Player3 = new ImageView(this.image);

                        this.image = loadPointImage(SceneController.getInstance().getLastSharedPoint2(i));
                        shared2Player3 = new ImageView(this.image);
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

                for(int i=0 ; i<players.size() ; i++){
                    if(i==player1){
                        this.image = loadPointImage(SceneController.getInstance().getLastSharedPoint1(i));
                        shared1Player1 = new ImageView(this.image);

                        this.image = loadPointImage(SceneController.getInstance().getLastSharedPoint2(i));
                        shared2Player1 = new ImageView(this.image);
                    }

                    if(i==player2){
                        this.image = loadPointImage(SceneController.getInstance().getLastSharedPoint1(i));
                        shared1Player2 = new ImageView(this.image);

                        this.image = loadPointImage(SceneController.getInstance().getLastSharedPoint2(i));
                        shared2Player2 = new ImageView(this.image);
                    }

                    if(i==player3){
                        this.image = loadPointImage(SceneController.getInstance().getLastSharedPoint1(i));
                        shared1Player3 = new ImageView(this.image);

                        this.image = loadPointImage(SceneController.getInstance().getLastSharedPoint2(i));
                        shared2Player3 = new ImageView(this.image);
                    }
                }
            }
        }
    }

    /**
     * Inserts an ImageView into a GridPane at the specified position.
     *
     * @param imageView The ImageView to be inserted.
     * @param width     The desired width of the ImageView.
     * @param height    The desired height of the ImageView.
     * @param gridp     The GridPane to which the ImageView will be added.
     * @param x         The column index where the ImageView will be inserted.
     * @param y         The row index where the ImageView will be inserted.
     */
    private void insertInGridPane(ImageView imageView, int width, int height, GridPane gridp, int x, int y){
        imageView.setImage(this.image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        Pane pane = new Pane();
        pane.getChildren().add(imageView);
        gridp.add(pane, x, y);
    }

    /**
     * Loads the image of a card and sets it as the current image in the scene.
     *
     * @param card The card for which to load the image.
     */
    private void loadTileImage(Card card){
        this.image=GameLoopSceneController.loadTileImage(card);
    }

    private Image loadPointImage(int point){
            if(point==0){
                return null;
            }
            Image imageToReturn;
            imageToReturn = new Image("/images/17_MyShelfie_BGA/scoring_tokens/scoring_"+point+".jpg");
            return imageToReturn;
    }

}
