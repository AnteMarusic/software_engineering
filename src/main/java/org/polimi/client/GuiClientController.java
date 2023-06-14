package org.polimi.client;

import org.polimi.messages.Message;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GuiClientController implements ClientControllerInterface{
    private final Client client;
    private String username;

    public GuiClientController(Client client) {
        this.client = client;
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
