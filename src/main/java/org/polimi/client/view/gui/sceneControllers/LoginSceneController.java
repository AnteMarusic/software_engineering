package org.polimi.client.view.gui.sceneControllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginSceneController {
    @FXML
    private TextField nicknameField;

    @FXML
    private Button joinBtn;

    @FXML
    public void initialize() {
        joinBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onJoinBtnClick);
    }
    @FXML
    private void onJoinBtnClick(Event event) {
        joinBtn.setDisable(true);
        String nickname = nicknameField.getText();
        System.out.println(nickname+" porcamadonna");
    }
}