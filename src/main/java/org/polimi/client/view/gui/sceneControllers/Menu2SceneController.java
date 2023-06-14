package org.polimi.client.view.gui.sceneControllers;

import javafx.event.ActionEvent;
import org.polimi.client.GuiClientController;

import java.io.IOException;
import java.rmi.RemoteException;

public class Menu2SceneController {
    public void privateGame(){

    }

    public void randomOf2(ActionEvent a){
        try {
            GuiClientController.getNotified("RandomGameOf2");
            SceneController.getInstance().switchScene(a, "login_scene");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void randomOf3(){

    }

    public void randomOf4(){

    }
}
