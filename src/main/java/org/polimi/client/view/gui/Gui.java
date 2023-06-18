package org.polimi.client.view.gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class Gui extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource("/scenesfxml/game_loop.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("login");
        stage.setScene(scene);
        //stage.setResizable(false);
        //stage.getIcons().add(icon);
        stage.show();
    }

    /*public static void main(String[] args) {
        launch(args);
    }*/
}

