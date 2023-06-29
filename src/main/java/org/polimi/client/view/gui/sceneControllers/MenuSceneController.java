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


    /**
     * Handles the event when the "Choose RMI" button is clicked.
     *
     * @param e The action event triggered by the button click.
     * @throws IOException        If an I/O error occurs while switching the scene.
     * @throws NotBoundException  If the remote object is not bound in the registry.
     */
    public void chooseRMI(ActionEvent e) throws IOException, NotBoundException {
        RMIClient rmiClient = new RMIClient(1099, true);
        rmiClient.startConnection();
        SceneController.getInstance().switchScene(e, "login_scene");
    }

    /**
     * Handles the event when the "Choose Socket" button is clicked.
     *
     * @param e The action event triggered by the button click.
     * @throws IOException If an I/O error occurs while switching the scene.
     */
    public void chooseSocket(ActionEvent e) throws IOException {
        SocketClient socket = new SocketClient(8181, true);
        socket.connect();
        SceneController.getInstance().switchScene(e, "login_scene");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
