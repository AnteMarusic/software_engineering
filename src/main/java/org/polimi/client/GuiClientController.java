package org.polimi.client;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import org.polimi.client.view.gui.sceneControllers.LobbySceneController;
import org.polimi.client.view.gui.sceneControllers.SceneController;
import org.polimi.messages.*;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GuiClientController implements ClientControllerInterface{
    private static Client client;
    private static String username;

    public static List<Object> messagges;

    private static boolean rmi;

    private static int numOfPlayers;

    private static boolean receivedgamemodemess;

    private static boolean startgame;


    public GuiClientController(Client client, boolean rmi) {
        this.client = client;
        this.messagges = new LinkedList<Object>();
        this.rmi=rmi;
        this.receivedgamemodemess=false;
        this.startgame=false;
    }


    //viene chiamato ogni volta che il controller di una scena vuole passare parametri a questa classe
    public static boolean getNotified(String notificationType) throws RemoteException {
        if(rmi){
            switch(notificationType){
                case "username" ->{
                    username = (String) messagges.get(0);
                    try {
                        return ((RMIClient) client).loginGui(username);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "RandomGameOf2" -> {
                    System.out.println("(GuiController) inviato al server messaggio di join prima del while");
                    while(!receivedgamemodemess){

                    }
                    System.out.println("(GuiController) inviato al server messaggio di join prima");
                    ((RMIClient) client).getServer().onMessage(new ChosenGameModeMessage(username, GameMode.JOIN_RANDOM_GAME_2_PLAYER, -1));
                    numOfPlayers=2;
                    System.out.println("(GuiController) inviato al server messaggio di join dopo");
                }
                case "RandomGameOf3" -> {
                    while(!receivedgamemodemess){

                    }
                    ((RMIClient) client).getServer().onMessage(new ChosenGameModeMessage(username, GameMode.JOIN_RANDOM_GAME_3_PLAYER, -1));
                    numOfPlayers=3;
                }
                case "RandomGameOf4" -> {
                    while(!receivedgamemodemess){

                    }
                    ((RMIClient) client).getServer().onMessage(new ChosenGameModeMessage(username, GameMode.JOIN_RANDOM_GAME_4_PLAYER, -1));
                    numOfPlayers=4;
                }
                case "startgame"->{
                    if(!startgame)
                        return false;
                    return true;
                }
                default ->{
                    return false;
                }

        }}
        return false;
    }


    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Message handleMessage(Message message) throws IOException {
        switch (message.getMessageType()) {
            case CHOOSE_GAME_MODE -> {
                System.out.println("setto a true");
                receivedgamemodemess=true;
            }
            case START_GAME_MESSAGE -> {
                System.out.println("ricevuto messaggio start game");
                return null;
            }

            //this message is sent
            //if the server recognises that this client is reconnecting, so it has to send the whole model status,
            //otherwise, it is sent at the beginning of the match
            case MODEL_STATUS_ALL -> {
                //in case of status all message the client doesn't have to send any message
                ModelStatusAllMessage m = (ModelStatusAllMessage) message;
                Map<Coordinates, Card> board = m.getBoard();
                List<Card[][]> bookshelves = m.getBookshelves();
                int sharedGoal1 = m.getSharedGoal1();
                int sharedGoal2 = m.getSharedGoal2();
                Coordinates[] personalGoalCoordinates = m.getPersonalGoalCoordinates();
                Card.Color[] personalGoalColors = m.getPersonalGoalColors();
                List <String> usernames = m.getUsernames();
                modelAllMessage(board, bookshelves, sharedGoal1, sharedGoal2, personalGoalCoordinates, personalGoalColors, usernames);
                startgame=true;
                return null;
            }

            /*
            // nuovo messaggio aggiunto
            case BOARDMESSAGE -> {
                BoardMessage m = (BoardMessage) message;
                Map<Coordinates, Card> board = m.getBoard();
                newBoardRefill(board);
                cli.printRoutine();
            }

            case CARD_TO_REMOVE -> {
                CardToRemoveMessage m = (CardToRemoveMessage) message;
                cli.removeOtherPlayerCards(m.getCoordinates());
                cli.printRoutine();
                return null;
            }
            case CHOSEN_COLUMN_REPLY -> {
                ChosenColumnMessage m = (ChosenColumnMessage) message;
                cli.insertInOtherPlayerBookshelf(m.getColumn());
                cli.printRoutine();
                return null;
            }

            //first message that is sent when is your turn
            case CHOOSE_CARDS_REQUEST -> {
                return chooseCards();
            }

            //message that should be received subsequently to the choice and the sorting of the cards.
            case CHOOSE_COLUMN_REQUEST -> {
                return chooseColumn();
            }

            //message received when is not your turn and the server notifies you of the next client playing
            case NOTIFY_NEXT_PLAYER -> {
                NotifyNextPlayerMessage m = (NotifyNextPlayerMessage) message;
                System.out.println(m.getNextPlayer() + " is now playing");
                cli.setCurrentPlayer(m.getNextPlayer());
                return null;
            }
            case ALREADYTAKENGAMECODEMESSAGE -> {
                cli.alreadyTakenGameCode();
                return null;
            }
            case AREALONE -> {
                cli.youAreAlone();
            }
            case DISCONNECTION_ALLERT -> {
                DisconnectionAlert m = (DisconnectionAlert) message;
                cli.disconnectionAlert(m.getUsernameDisconnected());
            }
            case NOTIFY_GOAL_COMPLETION -> {
            }
            case NOTIFY_GAME_END -> {
            }
            case RANKING_MESSAGE -> {
            }*/
        }
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
        List <ClientBookshelf> l = new ArrayList<>(bookshelves.size());
        for(int i=0 ; i<usernames.size() ; i++) {
            l.add (new ClientBookshelf(bookshelves.get(i)));
        }
        ClientBoard clientBoard = new ClientBoard(board, numOfPlayers );
        SceneController.getInstance().setPlayers(usernames);
        SceneController.getInstance().setBookshelves(l);
        SceneController.getInstance().setBoard(clientBoard);
        SceneController.getInstance().setPersonalGoal(personalGoalCoordinates, personalGoalColors);
        SceneController.getInstance().setSharedGoal1(sharedGoal1);
        SceneController.getInstance().setSharedGoal2(sharedGoal2);
    }

    @Override
    public void disconnect() {

    }

}
