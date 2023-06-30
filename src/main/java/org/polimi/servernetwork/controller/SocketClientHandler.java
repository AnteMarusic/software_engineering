package org.polimi.servernetwork.controller;

import org.polimi.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientHandler extends ClientHandler implements Runnable{
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    public SocketClientHandler(Socket socket, UsernameIssuer usernameIssuer, GameCodeIssuer gameCodeIssuer, LobbyController lobbyController) throws IOException {
        super(usernameIssuer, gameCodeIssuer, lobbyController);
        this.socket = socket;
        input = new ObjectInputStream(socket.getInputStream());
        output = new ObjectOutputStream(socket.getOutputStream());
        isLogged = false;
    }
    @Override
    public void run() {
        Message messageFromClient = null;
        while (socket != null && socket.isConnected()) {
            try {
                messageFromClient = (Message) input.readObject();
                // posso scartare tutti i messaggi di un client che è gia connesso alla partita se non è il suo turno

                if (messageFromClient != null) {
                    System.out.println(messageFromClient);
                    if (isLogged) {
                        onMessage(messageFromClient);
                    }
                    else {
                       login(messageFromClient);
                    }
                } else {
                    //message == null. we suppose that this happens because you disconnected
                    System.out.println("(SocketClientHandler) received a null message from client " + username);
                    disconnect();
                }
            } catch (IOException e) {
                System.out.println("(SocketClientHandler) client " + username + " disconnected");
                disconnect();
                closeEverything();

            } catch (ClassNotFoundException e) {
                System.out.println("(SocketClientHandler) exception class not found in ClientHandler run, " +
                        "we disconnected the client " + username + "that sent the message");
                disconnect();
            }
        }
        if (socket != null) {
            System.out.println("(SocketClientHandler) client " + username + "disconnected");
            disconnect();
        }

    }

    private void login (Message message) {
        InternalComunication internalComunication = usernameIssuer.login(message.getUsername());
        if(internalComunication == InternalComunication.OK) {
            usernameIssuer.setClientHandler(this, message.getUsername());
            this.username = message.getUsername();
            sendMessage(new Message(this.username, MessageType.CHOOSE_GAME_MODE ));
            isLogged = true;
        }
        if(internalComunication == InternalComunication.ALREADY_TAKEN_USERNAME) {
            sendMessage(new ErrorMessage(this.username, ErrorType.ALREADY_TAKEN_USERNAME));
            isLogged = false;
        }
        //to test
        if(internalComunication == InternalComunication.RECONNECTION){
            this.username = message.getUsername();
            int gameId = usernameIssuer.getGameID(message.getUsername());
            GameController gameController = gameCodeIssuer.getGameController(gameId);
            usernameIssuer.setClientHandler(this, message.getUsername());
            setGameController(gameController);
            usernameIssuer.setConnect(this.getUsername());
            gameController.reconnect(this);
            isLogged = true;
        }
    }

    protected void closeEverything() {
        System.out.println("(SocketClientHandler) closeEverything");
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }

            if (input != null) {
                input.close();
            }

            if (output != null) {
                output.close();
            }
        } catch (IOException IOe) {
            IOe.printStackTrace();
            System.out.println("(SocketClientHandler) exception in closeEverything");
        }
    }
    public void sendMessage (Message message) {
        try{
            System.out.println("(SocketClientHandler username: " + this.username + ") send message: " + message);
            output.writeObject(message);
            output.flush();
            output.reset();
        } catch(IOException IOe) {
            IOe.printStackTrace();
            closeEverything();
        }
    }
    public void reconnection(){
    }

}
