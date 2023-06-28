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

    private static boolean receivedgamemodemess;

    private static boolean startgame;

    private static boolean chosencards;
    private static boolean createdgameloop;

    private static Lock lock;
    private static Condition flagCondition;


    public GuiClientController(Client client, boolean rmi) {
        this.client = client;
        this.messagges = new LinkedList<Object>();
        this.rmi=rmi;
        this.receivedgamemodemess=false;
        this.startgame=false;
        this.chosencards = false;
        this.createdgameloop = false;
        this.lock = new ReentrantLock();
        this.flagCondition = lock.newCondition();
    }


    /**
     * Method that locks a thread and let it waits until a certain condition is
     * fullfilled
     * @throws InterruptedException
     */
    public void waitForFlag() throws InterruptedException {
        lock.lock();
        try {
            while (!this.chosencards) {
                flagCondition.await();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Methods that restores the initial state of the lock, it is going to be
     * called just after the sleeping thread is unlocked
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
     * procedures that need notifies from a thread to antoher one
     * @param notificationType
     * @return
     * @throws RemoteException
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
                case "chosencards"-> {
                    lock.lock();
                    try {
                        chosencards = true;
                        flagCondition.signal(); // Signal the waiting thread
                    } finally {
                        lock.unlock();
                    }
                }
                case "createdgameloop" -> {createdgameloop=true;
                System.out.println("settato a true createdgameloop da guiclientcontroleer");}
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
                    System.out.println("sto per unlockare lato guiclinetcontorller");
                    SocketClient.locksocket.lock();
                    try {
                        ((SocketClient) client).setWaitForusername(true);
                        SocketClient.flagCondition.signal();
                    } finally {
                        SocketClient.locksocket.unlock();
                    }
                    System.out.println("unlockato lato guiclinetcontorller");
                    return true;
                }

                case "RandomGameOf2" -> {
                    System.out.println("(GuiController) inviato al server messaggio di join prima del while");
                    while(!receivedgamemodemess){

                    }
                    System.out.println("(GuiController) inviato al server messaggio di join prima");
                    ((SocketClient) client).sendMessage(new ChosenGameModeMessage(username, GameMode.JOIN_RANDOM_GAME_2_PLAYER, -1));
                    numOfPlayers=2;
                    System.out.println("(GuiController) inviato al server messaggio di join dopo");
                }
                case "RandomGameOf3" -> {
                    while(!receivedgamemodemess){

                    }
                    ((SocketClient) client).sendMessage(new ChosenGameModeMessage(username, GameMode.JOIN_RANDOM_GAME_3_PLAYER, -1));
                    numOfPlayers=3;
                }
                case "RandomGameOf4" -> {
                    while(!receivedgamemodemess){

                    }
                    ((RMIClient) client).sendMessage(new ChosenGameModeMessage(username, GameMode.JOIN_RANDOM_GAME_4_PLAYER, -1));
                    numOfPlayers=4;
                }
                case "startgame"->{
                    if(!startgame)
                        return false;
                    return true;
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
                case "createdgameloop" -> {createdgameloop=true;
                    System.out.println("settato a true createdgameloop da guiclientcontroleer");}
                default ->{
                    return false;
                }
            }
        }
        return false;
    }


    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    /**Override method of upper class "Client" used to deal with messages received from
     * the server
     * @param message
     * @return
     * @throws IOException
     */
    @Override
    public Message handleMessage(Message message) throws IOException {
        switch (message.getMessageType()) {
            case CHOOSE_GAME_MODE -> {
                System.out.println("setto a true");
                receivedgamemodemess=true;
            }
            case START_GAME_MESSAGE -> {
                ///cambiare scena in qualche modo
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
                int personalGoal = m.getPersonalGoalIndex();
                Coordinates[] personalGoalCoordinates = m.getPersonalGoalCoordinates();
                Card.Color[] personalGoalColors = m.getPersonalGoalColors();
                List <String> usernames = m.getUsernames();
                int currentPlayer = m.getCurrentPlayer();
                modelAllMessage(board, bookshelves, sharedGoal1, sharedGoal2, personalGoalCoordinates, personalGoalColors, usernames, personalGoal);
                String ref = "/scenesfxml/game_loop.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(ref));
                System.out.println("prima di loader.load");
                Parent root = loader.load();
                System.out.println("dopo di loader.load");
                if(loader.getController()==null){
                    System.out.println("il controller é null");
                }else{
                    System.out.println("il controller non é null");
                }
                GameLoopSceneController gameLoopSceneController = loader.getController();
                gameLoopSceneController.gameLoopInit();
                SceneController.getInstance().setGameLoopController(gameLoopSceneController);
                if(usernames.get(currentPlayer).equals(username)){
                    SceneController.getInstance().setMyTurn(true);
                }else{
                    SceneController.getInstance().setMyTurn(false);
                }
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
                System.out.println("il server mi dice di togliere "+ m.getCards().size() + " carte");
                SceneController.getInstance().setOtherPlayerChosenCards(m.getCards());
                removeOtherPlayerCards(m.getCoordinates());
                return null;
            }
            case CHOSEN_COLUMN_REPLY -> {
                ChosenColumnMessage m = (ChosenColumnMessage) message;
                insertInOtherPlayerBookshelf(m.getColumn());
                return null;
            }

            //first message that is sent when is your turn, best wway should be that upon receiving this message the scene changes
            case CHOOSE_CARDS_REQUEST -> {
                System.out.println("sto per settare a true");
                SceneController.getInstance().setMyTurn(true);
                System.out.println("ho settato a true");
                try {
                    this.waitForFlag();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("dopo try, prima di reset, finito wait del chosencards");
                this.reset();
                return chooseCards();
            }

            //message that should be received subsequently to the choice and the sorting of the cards.
            case CHOOSE_COLUMN_REQUEST -> {
                SceneController.getInstance().setChosenCards(null);
                return chooseColumn();
            }

            //message received when is not your turn and the server notifies you of the next client playing
            case NOTIFY_NEXT_PLAYER -> {
                System.out.println("sto per settare a false");
                SceneController.getInstance().setMyTurn(false);
                System.out.println("ho settato a false");
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
            /*
            // nuovo messaggio aggiunto



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

    /**
     * Method that construct the choosen cards message
     * @return
     */
    @Override
    public Message chooseCards(){
        System.out.println("sto inviando una lista di coordinate di dimensione "+ SceneController.getInstance().getChosenCards().size());
        return new ChosenCardsMessage(username,SceneController.getInstance().getChosenCardsCoords(), SceneController.getInstance().getChosenCards());
    }

    /**
     * Method that take cards choosed by another players as parameters and update client model
     * consequentially
     * @param toRemove
     */
    public void removeOtherPlayerCards(List<Coordinates> toRemove){
        Coordinates temp;
        Card card;
        ClientBoard board= SceneController.getInstance().getBoard();
        Coordinates[] AdjacentCoordinates = new Coordinates[4];
        int j = 0;
        while (j < toRemove.size()) {
            temp = toRemove.get(j);
            board.removeCardAtCoordinates(temp);
            //SceneController.getInstance().getOtherPlayerChosenCards().add(card);
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
        System.out.println("la size delle chosen cards dell'altro client è: "+SceneController.getInstance().getOtherPlayerChosenCards().size());
        SceneController.getInstance().getBookshelves().get(SceneController.getInstance().getCurrentPlayer()).insert(SceneController.getInstance().getOtherPlayerChosenCards(), col);
        //SceneController.getInstance().getOtherPlayerChosenCards().clear();
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
     * Metrhod that take the whole model status ad parameter and update the Client rappresentation of the
     * model
     * @param board
     * @param bookshelves
     * @param sharedGoal1
     * @param sharedGoal2
     * @param personalGoalCoordinates
     * @param personalGoalColors
     * @param usernames
     * @param personalGoal
     */
    @Override
    public void modelAllMessage(Map<Coordinates, Card> board, List<Card[][]> bookshelves, int sharedGoal1, int sharedGoal2, Coordinates[] personalGoalCoordinates, Card.Color[] personalGoalColors, List<String> usernames, int personalGoal) {
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
        SceneController.getInstance().setPersonalGoalIndex(personalGoal);
    }

    @Override
    public void disconnect() {

    }

}
