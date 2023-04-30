package org.polimi.client;

import org.polimi.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final String username;
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public Client(String username, Socket socket) {
        this.username = username;
        this.socket = socket;
        this.input = null;
        this.output = null;
        try {
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException IOe) {
            System.out.println("exception in client constructor method");
            handleDisconnection();
        }
    }

    public static void main(String[] args) {
        Socket socket = null;
        //ask for username and server IP
        do {
            try {
                socket = new Socket("localHost", 2222);
            } catch (IOException e) {
                System.out.println("unable to connect");
            }
        } while (socket == null);

        Client client = new Client ("tempUsername", socket);
        client.startListeningToMessages();
        client.startSendingMessages();
    }

    public void startSendingMessages() {
        Scanner scanner = new Scanner (System.in);
        Message message;
        while(socket != null && socket.isConnected()) {
            System.out.println("waiting for a new message...");
            message = new TextMessage(this.username, scanner.nextLine());
            sendMessage(message);
        }
        handleDisconnection();
    }

    public void sendMessage (Message message) {
        try {
            if (socket.isConnected()){
                output.writeObject(message);
                output.flush();
                output.reset();
            }
            else {
                handleDisconnection();
            }
        }catch(IOException IOe) {
            System.out.println("exception in sendMessage");
            handleDisconnection();
        }
    }

    public void startListeningToMessages () {
        new Thread (() -> {
            Object message;
            try {
                while (socket.isConnected()) {
                    message = input.readObject();
                    if (!(message instanceof Message)) {
                        handleProtocolDisruption();
                    }
                    else{
                        handleMessage((Message)message);
                    }
                }
            } catch(IOException IOe) {
                System.out.println("exception in listenMessage");
                handleDisconnection();
            } catch (ClassNotFoundException e) {
                handleProtocolDisruption();
            }
        }). start();
    }

    private void closeEverything () {
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
    }

    public void handleProtocolDisruption() {
        System.out.println("someone sent something that isn't a message");
    }

    public void handleMessage(Message message) {
        System.out.println(message);
    }
}

