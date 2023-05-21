package org.polimi.servernetwork.server;

import org.polimi.servernetwork.controller.GameCodeIssuer;
import org.polimi.servernetwork.controller.LobbyController;
import org.polimi.servernetwork.controller.UsernameIssuer;

import java.io.IOException;

public class Server{
    private int socketPort;
    private int rmiPort;
    private final LobbyController lobby;
    private final GameCodeIssuer gameCodeIssuer;
    private final UsernameIssuer usernameIssuer;
    public Server(int socketPort, int rmiPort) {
        this.socketPort= socketPort;
        this.rmiPort= rmiPort;
        gameCodeIssuer = new GameCodeIssuer();
        usernameIssuer = new UsernameIssuer();
        lobby = new LobbyController(gameCodeIssuer, usernameIssuer);
        startServers();
    }

    /**
     * the server starts accepting incoming connections. For every new connections it creates a clientHandler
     * to grant disconnection resilience the server should access the class that issues new usernames and if there is an
     * username matching and this username is in a game the player is automatically inserted in the game and
     * skips the lobby part. Given that it makes sense that the username should be unique
     */


    //non so se quando chiamo questi metodi il thread che è in ascolto per le nuove connessioni deve
    //interrompersi

    private void startServers() {
        SocketServer serverSocket = new SocketServer(this, socketPort);
        serverSocket.startServer();
        RMIServer rmiServer = new RMIServer(this, rmiPort);
        rmiServer.startServer();
    }
    public UsernameIssuer getUsernameIssuer() {
        return usernameIssuer;
    }

    public GameCodeIssuer getGameCodeIssuer() {
        return gameCodeIssuer;
    }

    public LobbyController getLobby() {
        return lobby;
    }


    public static void main (String[] args) throws IOException {
        int socketPort = 8181;
        int rmiPort= 1099;
        Server server = new Server(socketPort, rmiPort);
    }
}