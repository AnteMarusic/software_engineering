package org.polimi.client.view.gui.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.polimi.client.GuiClientController;

import java.io.IOException;

public class LobbySceneController {
    @FXML
    Button button;
    @FXML
    public void initialize() {
    }
    public void onBtnClick(ActionEvent event) throws IOException {
        while(!GuiClientController.getNotified("startgame")){
        }
        SceneController.getInstance().switchScene(event, "game_loop");
    }

}
