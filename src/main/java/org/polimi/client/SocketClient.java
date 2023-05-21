package org.polimi.client;

import org.polimi.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClient extends Client{
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ClientController clientController;
    public SocketClient(int port) {
        super(port);
        try{
            this.socket = new Socket("localhost",port);
        }
        catch(IOException e){
            System.out.println("exception in client constructor method 1");
        }
        this.input = null;
        this.output = null;
        try {
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException IOe) {
            System.out.println("exception in client constructor method 2");
            handleDisconnection();
        }
        createClientController();
        startListeningToMessages();
        Message message = clientController.chooseUsername();
        this.username=message.getUsername();
        sendMessage(message);
    }
    private void createClientController() {
        this.clientController = new ClientController(this);
    }
    public void sendMessage(Message message) {
        try {
            if (socket.isConnected()) {
                output.writeObject(message);
                output.flush();
                output.reset();
            } else {
                handleDisconnection();
            }
        } catch (IOException IOe) {
            System.out.println("exception in sendMessage");
            handleDisconnection();
        }
    }

    /**
     * create a new Thread that listen to incoming messages. When a message arrives
     * it forwards it to the client controller with the method handleMessage(Message)
     * that returns a message or null. If a message is returned the method sends it to the server.
     */
    public void startListeningToMessages() {
        new Thread(() -> {
            Object message;
            Message toSend;
            try {
                while (socket.isConnected()) {
                    message = input.readObject();
                    System.out.println(message);
                    if (!(message instanceof Message)) {
                        handleProtocolDisruption();
                    } else {
                        toSend = clientController.handleMessage((Message) message);
                        if (toSend != null) {
                            sendMessage(toSend);
                        }
                    }
                }
            } catch (IOException IOe) {
                System.out.println("exception in listenMessage");
                handleDisconnection();
            } catch (ClassNotFoundException e) {
                handleProtocolDisruption();
            }
        }).start();
    }
    private void closeEverything() {
        System.out.println("closeEverything");
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }

            if (input != null) {
                input.close();
                input = null;
            }

            if (output != null) {
                output.close();
                output = null;
            }
        } catch (IOException IOe) {
            IOe.printStackTrace();
            System.out.println("exception in closeEverything client side");
        }
    }
    public void handleDisconnection() {
        closeEverything();
        clientController.handleDisconnection();
    }
    public void handleProtocolDisruption() {
        System.out.println("someone sent something that isn't a message");
    }


    public static void main(String[] args) {
        int port = 8181;
        SocketClient socket = new SocketClient(port);
    }
}

