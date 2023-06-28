package org.polimi.servernetwork.server;

import org.polimi.client.Decrementer;
import org.polimi.servernetwork.controller.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;

/**
 * This class starts the RMI server and the socket server
 * in particular it creates gameCodeIssuer, usernameIssuer and lobby controller that are unique objects shared between
 * RMI and socket server. It calls socket server constructor and RMI server constructor
 * if there aren't saved games it just starts the server
 * if the fileAccessor finds some games it creates a new GameController for each one of them
 * then for each gameController we try to retrieve the game from file. if the operation is successful we keep the gameController,
 * register the gameController in gameCodeIssuer and register each player as disconnected in usernameIssuer;
 * otherwise we discard it
 */

public class ServerStarter {
    private static final int socketPort = 8181;
    private static final int rmiPort = 1099;
    public static void main (String[] args) throws IOException {
        GameCodeIssuer gameCodeIssuer = new GameCodeIssuer();
        UsernameIssuer usernameIssuer = new UsernameIssuer();
        LobbyController lobby = new LobbyController(gameCodeIssuer, usernameIssuer);
        GameListFileAccessorSingleton gameListFileAccessor = GameListFileAccessorSingleton.getInstance();
        if (!gameListFileAccessor.isEmpty()) {
            Map<Integer, List<String>> gameIdWithPlayers = gameListFileAccessor.getGameIdsAndPlayers();
            for (Integer gameId : gameIdWithPlayers.keySet()) {
                try {
                    System.out.println("(ServerStarter) retrieving game " + gameId + " from file");
                    //calling special constructor maybe is better to do the special operations with separate method and not
                    //in the constructor
                    GameController gameController = new GameController(usernameIssuer, gameCodeIssuer, gameId);
                    gameController.retrieveGameFromFile();
                    gameCodeIssuer.createRow(gameId, gameController);
                    List <String> usernames = gameIdWithPlayers.get(gameId);
                    for (String username : usernames) {
                        usernameIssuer.createRow(username);
                        usernameIssuer.mapUsernameToGameCode(username, gameId);
                        usernameIssuer.setDisconnect(username);
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("(ServerStarter) file reading failed for game with code " + gameId);
                    //if the reading operation is not successful I don't create the game and I don't set a line for
                    //the usernames that were in that game
                }
            }

        }
        //RMIMessagesHub messagesHub = new RMIMessagesHub();
        SocketServer serverSocket = new SocketServer(socketPort, gameCodeIssuer, usernameIssuer, lobby);
        RMIServer rmiServer = new RMIServer(rmiPort, gameCodeIssuer, usernameIssuer, lobby);
    }
}

