package org.polimi.servernetwork.server;

import org.polimi.servernetwork.controller.Decrementer;
import org.polimi.servernetwork.controller.GameCodeIssuer;
import org.polimi.servernetwork.controller.LobbyController;
import org.polimi.servernetwork.controller.UsernameIssuer;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerStarter {
    private static final int socketPort = 8181;
    private static final int rmiPort = 1099;
    public static void main (String[] args) throws IOException {
        GameCodeIssuer gameCodeIssuer = new GameCodeIssuer();
        UsernameIssuer usernameIssuer = new UsernameIssuer();
        LobbyController lobby = new LobbyController(gameCodeIssuer, usernameIssuer);
        SocketServer serverSocket = new SocketServer(socketPort, gameCodeIssuer, usernameIssuer, lobby);
        //start server runs on a different thread than gameCodeIssuer and usernameIssuer and lobby
        serverSocket.startServer();

        try {
            Registry registry = LocateRegistry.createRegistry(rmiPort);
            Decrementer decrementer = new Decrementer(usernameIssuer);
            RMIServer rmiServer = new RMIServer(gameCodeIssuer, usernameIssuer, lobby);
            registry.bind("server", rmiServer);
            new Thread(decrementer).start();
        } catch (IOException | AlreadyBoundException e) {
            System.out.println("errore");
        }
        System.out.println("RMI server is up");
    }

}