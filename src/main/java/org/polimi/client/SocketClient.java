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
    private final boolean guiMode;
    private ClientControllerInterface clientController;
    public SocketClient(int port, boolean guiMode) {
        super(port);
        this.guiMode = guiMode;
    }

    public boolean connect () {
        try{
            this.socket = new Socket("localhost", this.getPort());
        }
        catch(IOException e){
            return false;
        }
        try {
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException IOe) {
            closeEverything();
            return false;
        }
        if(!guiMode) {
            createCliClientController();
            startListeningToMessages();
            Message message = clientController.chooseUsername();
            this.username = message.getUsername();
            sendMessage(message);
        }
        return true;
    }
    private void createCliClientController() {
        this.clientController = new CliClientController(this);
    }

    private void createGuiClientController() {
        this.clientController = new GuiClientController(this, false);
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
        clientController.disconnect();
    }
    public void handleProtocolDisruption() {
        System.out.println("someone sent something that isn't a message");
    }


    /*public static void main(String[] args) {
        int port = 8181;
        SocketClient socket = new SocketClient(port);
    }*/
}

