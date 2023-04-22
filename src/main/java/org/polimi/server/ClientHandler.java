package org.polimi.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private String clientUsername;

    public ClientHandler (Socket socket) {

        try {
            this.socket = socket;
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //I expect that when a user wants to connect to the server the first thing that he sends is
            //his name.
            this.clientUsername = reader.readLine();
            clientHandlers.add(this);
            broadcastMessage ("SERVER: " + clientUsername + " has connected to the chat");
        } catch (IOException IOe) {
            removeClientHandler();
            System.out.println("exception in clientHandler");
            IOe.printStackTrace();
        }
    }

    @Override
    public void run() {
        String messageFromClient;
        while (socket.isConnected()) {
            try {
                messageFromClient = reader.readLine();

                if (messageFromClient != null) {
                    broadcastMessage (messageFromClient);
                }
                else {
                    removeClientHandler();
                }
            } catch (IOException IOe) {
                closeEverything (socket, reader, writer);
                //break statement is needed otherwise I check socket.isConnected when socket is closed, causing an exception
                break;
            }
        }
        System.out.println("exception in run method of Client Handler");
        //removeClientHandler();
    }

    public void broadcastMessage (String messageToSend) {
        for (ClientHandler c : clientHandlers) {
            try {
                //sends message to everyone connected to the chat besides the client that sent it
                if (!c.clientUsername.equals(clientUsername)) {
                    c.writer.write(messageToSend);
                    //carriage return character marks the end of the message
                    c.writer.newLine();
                    c.writer.flush();
                }
            } catch (IOException IOe) {
                System.out.println("exception in broadcastMessage");
                removeClientHandler();
            }
        }
    }

    private void closeEverything (Socket socket, BufferedReader reader, BufferedWriter writer) {
        //removeClientHandler();
        try {
            if (socket != null) {
                socket.close();
            }

            if (reader != null) {
                reader.close();
            }

            if (writer != null) {
                writer.close();
            }
        } catch (IOException IOe) {
            IOe.printStackTrace();
            System.out.println("exception in closeEverything");
        }
    }

    public void removeClientHandler () {
        clientHandlers.remove(this);
        broadcastMessage(  "SERVER: " + clientUsername + " has left the chat");
        closeEverything(socket, reader, writer);
    }


}
