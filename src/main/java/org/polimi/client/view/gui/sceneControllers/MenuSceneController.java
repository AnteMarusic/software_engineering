package org.polimi.client.view.gui.sceneControllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.shape.Box;
import org.polimi.client.RMIClient;
import org.polimi.client.SocketClient;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class MenuSceneController implements Initializable {
    boolean bool;
    @FXML
    private Box box;
    @FXML
    private Slider slider;

    private double num=0;
    public void chooseRMI(ActionEvent e) throws IOException, NotBoundException {
        RMIClient rmiClient = new RMIClient(1099, true);
        rmiClient.startConnection();
        SceneController.getInstance().switchScene(e, "login_scene");
    }
    public void chooseSocket(ActionEvent e) throws IOException {
        SocketClient socket = new SocketClient(8181, true);
        socket.connect();
        SceneController.getInstance().switchScene(e, "login_scene");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                num=slider.getValue();
                box.setLayoutX((num*60)%1180);
            }
        });
    }
}
