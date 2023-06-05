package org.polimi.client.view.gui.sceneControllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LoginSceneController {
    @FXML
    private TextField nicknameField;

    @FXML
    private Button joinBtn;

    @FXML
    public void initialize() {
    }
    @FXML
    private void onJoinBtnClick(ActionEvent event) throws IOException {
        joinBtn.setDisable(true);
        String nickname = nicknameField.getText();
        System.out.println(nickname+" bruh");
        SceneController.getInstance().switchScene(event, "menu2_scene");
    }
}

