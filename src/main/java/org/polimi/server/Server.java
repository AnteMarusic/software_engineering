package org.polimi.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /**
     * the server starts accepting incoming connections. For every new connections it creates a clientHandler
     * to grant disconnection resilience the server should access the class that issues new usernames and if there is an
     * username matching and this username is in a game the player is automatically inserted in the game and
     * skips the lobby part. Given that it makes sense that the username should be unique
     */
    public void startServer () {
        try {
            System.out.println("server up");
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("a client has connected");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException IOe) {
            IOe.printStackTrace();
            System.out.println("exception in startServer");
        }
    }

    public static void main (String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2222);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}