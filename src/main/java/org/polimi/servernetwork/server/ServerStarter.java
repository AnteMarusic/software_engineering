package org.polimi.servernetwork.server;

import org.polimi.client.Decrementer;
import org.polimi.servernetwork.controller.GameCodeIssuer;
import org.polimi.servernetwork.controller.LobbyController;
import org.polimi.servernetwork.controller.UsernameIssuer;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class starts the RMI server and the socket server
 * in particular it creates gameCodeIssuer, usernameIssuer and lobby controller that are unique objects shared between
 * RMI and socket server. It calls socket server constructor and calls on it start server method
 *
 */
public class ServerStarter {
    private static final int socketPort = 8181;
    private static final int rmiPort = 1099;
    public static void main (String[] args) throws IOException {
        GameCodeIssuer gameCodeIssuer = new GameCodeIssuer();
        UsernameIssuer usernameIssuer = new UsernameIssuer();
        LobbyController lobby = new LobbyController(gameCodeIssuer, usernameIssuer);
        //RMIMessagesHub messagesHub = new RMIMessagesHub();
        SocketServer serverSocket = new SocketServer(socketPort, gameCodeIssuer, usernameIssuer, lobby);
        //start server runs on a different thread than gameCodeIssuer and usernameIssuer and lobby
        serverSocket.startServer();

        try {
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            //Decrementer decrementer = new Decrementer(usernameIssuer);
            RMIServer rmiServer = new RMIServer(gameCodeIssuer, usernameIssuer, lobby);
            registry.bind("server", rmiServer);
            //new Thread(decrementer).start();
            System.out.println("RMI server is up");
        } catch (IOException | AlreadyBoundException e) {
            System.out.println("errore rmi server, l'errore è:");
            System.out.println(e);
        }

    }

}