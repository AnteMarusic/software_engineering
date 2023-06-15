package org.polimi.servernetwork.server;

import org.polimi.servernetwork.controller.*;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class SocketServer implements Runnable{
    private final int port;
    private ServerSocket serverSocket;
    private final LobbyController lobby;
    private final GameCodeIssuer gameCodeIssuer;
    private final UsernameIssuer usernameIssuer;

    public SocketServer(int port, GameCodeIssuer gameCodeIssuer, UsernameIssuer usernameIssuer, LobbyController lobby) {
        this.port = port;
        this.lobby=lobby;
        this.gameCodeIssuer=gameCodeIssuer;
        this.usernameIssuer=usernameIssuer;
        startServer();
    }

    //start server runs on a different thread than gameCodeIssuer and usernameIssuer and lobby
    public void startServer ()  {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            System.out.println("Socket server is up");
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("a client has connected to the socketServer");
                ClientHandler clientHandler =new SocketClientHandler(socket, usernameIssuer, gameCodeIssuer, lobby);
                // creare il pinger
                Thread thread = new Thread((Runnable) clientHandler);
                thread.start();
            }
        } catch (IOException IOe) {
            IOe.printStackTrace();
            System.out.println("exception in startServer of SocketServer");
        }
    }

}
