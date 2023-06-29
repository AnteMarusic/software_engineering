package org.polimi.client;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.polimi.client.view.gui.sceneControllers.GameLoopSceneController;
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
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.polimi.GameRules.boardRowColInBound;

public class GuiClientController implements ClientControllerInterface{
    private static Client client;
    private static String username;

    public static List<Object> messagges;

    private static boolean rmi;

    private static int numOfPlayers;

    private static volatile boolean receivedgamemodemess;

    private static boolean startgame;

    private static boolean chosencards;
    private static GameLoopSceneController gameLoopSceneController;
    private static Lock lock;
    private static Condition flagCondition;


    public GuiClientController(Client client, boolean rmi) {
        GuiClientController.client = client;
        messagges = new LinkedList<>();
        GuiClientController.rmi =rmi;
        receivedgamemodemess=false;
        startgame=false;
        chosencards = false;
        lock = new ReentrantLock();
        flagCondition = lock.newCondition();
    }


    /**
     * Method that locks a thread and let it waits until a certain condition is
     * fulfilled.
     * @throws InterruptedException if the thread is interrupted while waiting
     */
    public void waitForFlag() throws InterruptedException {
        lock.lock();
        try {
            while (!chosencards) {
                flagCondition.await();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Methods that restores the initial state of the lock, it is going to be
     * called just after the sleeping thread is unlocked.
     */
    public void reset() {
        lock.lock();
        try {
            chosencards = false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Method that let us deal with specific internal notification, used to synchronize
     * procedures that need notifies from a thread to another one.
     * @param notificationType The type of notification.
     * @return Returns a boolean value based on the notification type and the performed actions.
     * @throws RemoteException if a remote communication error occurs.
     */
    public static boolean getNotified(String notificationType) throws RemoteException {
        if(rmi){
            switch(notificationType){
                case "username" ->{
                    username = (String) messagges.get(0);
                    SceneController.getInstance().setMyUsername(username);
                    try {
                        return ((RMIClient) client).loginGui(username);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "RandomGameOf2" -> {
                    while (!receivedgamemodemess) {
                        Thread.onSpinWait();
                    }
                    ((RMIClient) client).getServer().onMessage(new ChosenGameModeMessage(username, GameMode.JOIN_RANDOM_GAME_2_PLAYER, -1));
                    numOfPlayers=2;
                }
                case "RandomGameOf3" -> {
                    while (!receivedgamemodemess) {
                        Thread.onSpinWait();

                    }
                    ((RMIClient) client).getServer().onMessage(new ChosenGameModeMessage(username, GameMode.JOIN_RANDOM_GAME_3_PLAYER, -1));
                    numOfPlayers=3;
                }
                case "RandomGameOf4" -> {
                    while (!receivedgamemodemess) {
                        Thread.onSpinWait();
                    }
                    ((RMIClient) client).getServer().onMessage(new ChosenGameModeMessage(username, GameMode.JOIN_RANDOM_GAME_4_PLAYER, -1));
                    numOfPlayers=4;
                }
                case "startgame"->{
                    return startgame;
                }
                case "chosencards"-> {
                    lock.lock();
                    try {
                        chosencards = true;
                        flagCondition.signal(); // Signal the waiting thread
                    } finally {
                        lock.unlock();
                    }
                }
                default ->{
                    return false;
                }

            }
        }else {
            switch (notificationType) {
                case "username" -> {
                    username = (String) messagges.get(0);
                    SceneController.getInstance().setMyUsername(username);
                    client.setUsername(username);
                    SocketClient.locksocket.lock();
                    try {
                        ((SocketClient) client).setWaitForusername(true);
                        SocketClient.flagCondition.signal();
                    } finally {
                        SocketClient.locksocket.unlock();
                    }
                    return true;
                }

                case "RandomGameOf2" -> {
                    while (!receivedgamemodemess) {
                        Thread.onSpinWait();
                    }
                    ((SocketClient) client).sendMessage(new ChosenGameModeMessage(username, GameMode.JOIN_RANDOM_GAME_2_PLAYER, -1));
                    numOfPlayers=2;
                }
                case "RandomGameOf3" -> {
                    while (!receivedgamemodemess) {
                        Thread.onSpinWait();

                    }
                    ((SocketClient) client).sendMessage(new ChosenGameModeMessage(username, GameMode.JOIN_RANDOM_GAME_3_PLAYER, -1));
                    numOfPlayers=3;
                }
                case "RandomGameOf4" -> {
                    while (!receivedgamemodemess) {
                        Thread.onSpinWait();
                    }
                    ((RMIClient) client).sendMessage(new ChosenGameModeMessage(username, GameMode.JOIN_RANDOM_GAME_4_PLAYER, -1));
                    numOfPlayers=4;
                }
                case "startgame"->{
                    return startgame;
                }
                case "chosencards"-> {
                    lock.lock();
                    try {
                        chosencards = true;
                        flagCondition.signal();
                    } finally {
                        lock.unlock();
                    }
                }
                default ->{
                    return false;
                }
            }
        }
        return false;
    }


    @Override
    public void setUsername(String username) {
        GuiClientController.username = username;
    }

    /**Override method of upper class "Client" used to deal with messages received from
     * the server
     *
     * @param message The message to be handled.
     * @return Returns a response message or null depending on the message type.
     * @throws IOException if an I/O error occurs during message handling.

     */
    @Override
    public Message handleMessage(Message message) throws IOException {
        switch (message.getMessageType()) {
            case CHOOSE_GAME_MODE -> {
                receivedgamemodemess=true;
            }
            case START_GAME_MESSAGE -> {
                return null;
            }
            case MODEL_STATUS_ALL -> {
                ModelStatusAllMessage m = (ModelStatusAllMessage) message;
                Map<Coordinates, Card> board = m.getBoard();
                List<Card[][]> bookshelves = m.getBookshelves();
                int sharedGoal1 = m.getSharedGoal1();
                int sharedGoal2 = m.getSharedGoal2();
                int personalGoal = m.getPersonalGoalIndex();
                Coordinates[] personalGoalCoordinates = m.getPersonalGoalCoordinates();
                Card.Color[] personalGoalColors = m.getPersonalGoalColors();
                List <String> usernames = m.getUsernames();
                int currentPlayer = m.getCurrentPlayer();
                modelAllMessage(board, bookshelves, sharedGoal1, sharedGoal2, personalGoalCoordinates, personalGoalColors, usernames, personalGoal,currentPlayer);
                String ref = "/scenesfxml/game_loop.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(ref));
                Parent root = loader.load();
                gameLoopSceneController = loader.getController();
                gameLoopSceneController.gameLoopInit();
                SceneController.getInstance().setGameLoopController(gameLoopSceneController);
                SceneController.getInstance().setMyTurn(usernames.get(currentPlayer).equals(username));
                startgame=true;
                Stage stage = SceneController.getInstance().getStage();
                Platform.runLater(() -> {
                    try {
                        SceneController.getInstance().switchScene3(stage, "game_loop",root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                return null;
            }
            case CARD_TO_REMOVE -> {
                CardToRemoveMessage m = (CardToRemoveMessage) message;
                SceneController.getInstance().setOtherPlayerChosenCards(m.getCards());
                removeOtherPlayerCards(m.getCoordinates());
                return null;
            }
            case CHOSEN_COLUMN_REPLY -> {
                ChosenColumnMessage m = (ChosenColumnMessage) message;
                insertInOtherPlayerBookshelf(m.getColumn());
                return null;
            }
            case CHOOSE_CARDS_REQUEST -> {
                SceneController.getInstance().setMyTurn(true);
                try {
                    this.waitForFlag();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.reset();
                return chooseCards();
            }
            case CHOOSE_COLUMN_REQUEST -> {
                SceneController.getInstance().setChosenCards(null);
                return chooseColumn();
            }
            case NOTIFY_NEXT_PLAYER -> {
                SceneController.getInstance().setMyTurn(false);
                NotifyNextPlayerMessage m = (NotifyNextPlayerMessage) message;
                SceneController.getInstance().setCurrentPlayer(m.getNextPlayer());
                return null;
            }

            case BOARDMESSAGE -> {
                BoardMessage m = (BoardMessage) message;
                Map<Coordinates, Card> board = m.getBoard();
                ClientBoard clientBoard = new ClientBoard(board, numOfPlayers);
                SceneController.getInstance().setBoard(clientBoard);
            }
            case RANKING_MESSAGE -> {
                RankingMessage m = (RankingMessage) message;
                Map<String,Integer> ranking = m.getRanking();
                SceneController.getInstance().setRanking(ranking);
                String ref = "/scenesfxml/final_scene.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(ref));
                Parent root = loader.load();
                Stage stage = SceneController.getInstance().getStage();
                Platform.runLater(() -> {
                    try {
                        SceneController.getInstance().switchScene3(stage, "final_scene",root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            case WAITING_FOR_YOUR_TURN ->{
                gameLoopSceneController.reconnect();
            }

            case CURRENT_SCORE -> {
                CurrentScore m = (CurrentScore) message;
                SceneController.getInstance().setMyScore(m.getCurrentScore());
            }
        }
        return null;
    }

    @Override
    public Message chooseUsername() {
        return null;
    }

    /**
     * Allows the player to choose cards and returns a message containing the chosen cards.
     *
     * @return A message containing the player's chosen cards.
     */
    @Override
    public Message chooseCards(){
        return new ChosenCardsMessage(username,SceneController.getInstance().getChosenCardsCoords(), SceneController.getInstance().getChosenCards());
    }

    /**
     * Method that take cards chosen by another players as parameters and update client model
     * consequentially
     * @param toRemove The list of coordinates for the cards to be removed.
     *
     */
    public void removeOtherPlayerCards(List<Coordinates> toRemove){
        Coordinates temp;
        ClientBoard board= SceneController.getInstance().getBoard();
        Coordinates[] AdjacentCoordinates = new Coordinates[4];
        int j = 0;
        while (j < toRemove.size()) {
            temp = toRemove.get(j);
            board.removeCardAtCoordinates(temp);
            AdjacentCoordinates[0] = new Coordinates(temp.getRow(), temp.getCol() + 1);
            AdjacentCoordinates[1] = new Coordinates(temp.getRow() + 1, temp.getCol());
            AdjacentCoordinates[2] = new Coordinates(temp.getRow(), temp.getCol() - 1);
            AdjacentCoordinates[3] = new Coordinates(temp.getRow() - 1, temp.getCol());
            for (int i = 0; i < 4; i++) {
                if (boardRowColInBound(AdjacentCoordinates[i].getRow(), AdjacentCoordinates[i].getCol(), numOfPlayers) && board.seeCardAtCoordinates(AdjacentCoordinates[i]) != null) {
                    board.setToPickable(AdjacentCoordinates[i]);
                }
            }
            j ++;
        }
    }
    public void insertInOtherPlayerBookshelf (int col){
        SceneController.getInstance().getBookshelves().get(SceneController.getInstance().getCurrentPlayer()).insert(SceneController.getInstance().getOtherPlayerChosenCards(), col);
    }

    @Override
    public void alreadyTakenUsername() {

    }

    @Override
    public Message chooseGameMode() {
        return null;
    }


    @Override
    public LinkedList<Coordinates> orderChosenCards(List<Coordinates> chosenCoordinates) {
        return null;
    }

    @Override
    public Message chooseColumn() {
        return new ChosenColumnMessage(username, SceneController.getInstance().getChosencol());
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


    /**
     * Updates the model with the received game state information.
     *
     * @param board                 The map of coordinates to cards representing the game board.
     * @param bookshelves           The list of bookshelves for each player.
     * @param sharedGoal1           The value of the first shared goal.
     * @param sharedGoal2           The value of the second shared goal.
     * @param personalGoalCoordinates The array of coordinates for the personal goal cards.
     * @param personalGoalColors    The array of colors for the personal goal cards.
     * @param usernames             The list of usernames for each player.
     * @param personalGoal          The index of the personal goal card.
     * @param currPlayer            The index of the current player.
     */
    @Override
    public void modelAllMessage(Map<Coordinates, Card> board, List<Card[][]> bookshelves, int sharedGoal1, int sharedGoal2, Coordinates[] personalGoalCoordinates, Card.Color[] personalGoalColors, List<String> usernames, int personalGoal,int currPlayer) {
        List <ClientBookshelf> l = new ArrayList<>(bookshelves.size());
        for(int i=0 ; i<usernames.size() ; i++) {
            l.add (new ClientBookshelf(bookshelves.get(i)));
        }
        ClientBoard clientBoard = new ClientBoard(board, numOfPlayers );
        SceneController.getInstance().setPlayers(usernames);
        SceneController.getInstance().setBookshelves(l);
        SceneController.getInstance().setCurrentPlayer(SceneController.getInstance().getPlayers().get(currPlayer));
        SceneController.getInstance().setBoard(clientBoard);
        SceneController.getInstance().setPersonalGoal(personalGoalCoordinates, personalGoalColors);
        SceneController.getInstance().setSharedGoal1(sharedGoal1);
        SceneController.getInstance().setSharedGoal2(sharedGoal2);
        SceneController.getInstance().setPersonalGoalIndex(personalGoal);
    }

    @Override
    public void disconnect() {

    }

}
