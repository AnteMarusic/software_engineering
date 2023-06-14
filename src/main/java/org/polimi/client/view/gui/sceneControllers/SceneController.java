package org.polimi.client.view.gui.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.polimi.client.GuiClientController;
import org.polimi.client.view.gui.Gui;

import java.io.IOException;

public class SceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static SceneController instance;


    public static SceneController getInstance() {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }
    public void switchScene(ActionEvent event, String sceneName) throws IOException {
        String ref ="/scenesfxml/"+ sceneName +".fxml";
        root= FXMLLoader.load(getClass().getResource(ref));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        if(sceneName.equals("login_scene")){
            scene = new Scene(root,693, 200);
            stage.setResizable(false);
        }
        else
            scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
