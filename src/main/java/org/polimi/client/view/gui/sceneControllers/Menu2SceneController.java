package org.polimi.client.view.gui.sceneControllers;

import javafx.event.ActionEvent;
import org.polimi.client.GuiClientController;

import java.io.IOException;
import java.rmi.RemoteException;

public class Menu2SceneController {
    public void initialize(){
        if(SceneController.getInstance().isReconnected()){
            SceneController.getInstance().getStage().close();
            SceneController.getInstance().setReconnected(false);
        }
    }
    public void privateGame(){

    }

    /**
     * Handles the event when the "Random Game of 2" button is clicked.
     * It is going to ask for a 2 players public game
     * @param a The action event triggered by the button click.
     */
    public void randomOf2(ActionEvent a){
        try {
            GuiClientController.getNotified("RandomGameOf2");
            SceneController.getInstance().switchScene(a, "lobby_scene");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the event when the "Random Game of 3" button is clicked.
     * It is going to ask for a 3 players public game
     * @param a The action event triggered by the button click.
     */
    public void randomOf3(ActionEvent a){
        try {
            GuiClientController.getNotified("RandomGameOf3");
            SceneController.getInstance().switchScene(a, "lobby_scene");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Handles the event when the "Random Game of 4" button is clicked.
     * It is going to ask for a 4 players public game
     * @param a The action event triggered by the button click.
     */
    public void randomOf4(ActionEvent a){
        try {
            GuiClientController.getNotified("RandomGameOf4");
            SceneController.getInstance().switchScene(a, "lobby_scene");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
