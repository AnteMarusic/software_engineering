package org.polimi.client.view.gui.sceneControllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.polimi.client.GuiClientController;
import org.polimi.client.RMIClient;

import java.io.IOException;

public class LoginSceneController {
    @FXML
    private TextField nicknameField;

    @FXML
    private Button joinBtn;

    @FXML
    public void initialize() {
    }

    /**
     * Handles the event when the join button is clicked.
     *
     * @param event The action event triggered by the join button.
     * @throws IOException if an I/O error occurs while switching scenes.
     */
    @FXML
    private void onJoinBtnClick(ActionEvent event) throws IOException {
        joinBtn.setDisable(true);
        GuiClientController.username= nicknameField.getText();
        if(GuiClientController.getNotified("username")){
            SceneController.getInstance().switchScene(event, "menu2_scene");
        }else{
            SceneController.getInstance().switchScene(event, "login_scene");
        }
    }
}

