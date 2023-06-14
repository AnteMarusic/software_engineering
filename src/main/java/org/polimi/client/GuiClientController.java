package org.polimi.client;

import javafx.scene.Scene;
import org.polimi.messages.*;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GuiClientController implements ClientControllerInterface{
    private static Client client;
    private static String username;

    public static List<Object> messagges;

    public GuiClientController(Client client) {
        this.client = client;
        this.messagges = new LinkedList<Object>();
    }


    //viene chiamato ogni volta che il controller di una scena vuole passare parametri a questa classe
    public static boolean getNotified(String notificationType) throws RemoteException {
        switch(notificationType){
            case "username" ->{
                username = (String) messagges.get(0);
                try {
                    return ((RMIClient) client).loginGui(username);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            case "RandomGameof2" -> {
                ((RMIClient) client).getServer().onMessage(new ChosenGameModeMessage(username, GameMode.JOIN_RANDOM_GAME_2_PLAYER, -1));
            }
        }
        return false;
    }


    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Message handleMessage(Message message) {
        return null;
    }

    @Override
    public Message chooseUsername() {
        return null;
    }



    @Override
    public void alreadyTakenUsername() {

    }

    @Override
    public Message chooseGameMode() {
        System.out.println("arrivati a choose game mode");
        return null;
    }

    @Override
    public Message chooseCards() {
        return null;
    }

    @Override
    public LinkedList<Coordinates> orderChosenCards(List<Coordinates> chosenCoordinates) {
        return null;
    }

    @Override
    public Message chooseColumn() {
        return null;
    }

    @Override
    public void errorMessage() {

    }

    @Override
    public void newPlayerJoinedLobby(String newPlayer) {

    }

    @Override
    public void loginSuccessful() {

    }

    @Override
    public void reconnectionSuccessful() {

    }

    @Override
    public void modelAllMessage(Map<Coordinates, Card> board, List<Card[][]> bookshelves, int sharedGoal1, int sharedGoal2, Coordinates[] personalGoalCoordinates, Card.Color[] personalGoalColors, List<String> usernames) {

    }

    @Override
    public void disconnect() {

    }
}
