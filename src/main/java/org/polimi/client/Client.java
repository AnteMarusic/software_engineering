package org.polimi.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;

    private String username;

    public Client (Socket socket, String username){
        try {
            this.socket = socket;
            this.username = username;
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException IOe) {
            closeEverything (socket, writer, reader);
            System.out.println("exception in client constructor");
        }
    }

    public void sendMessage () {
        Scanner scanner = new Scanner (System.in);

        try {
            writer.write(username);
            writer.newLine();
            writer.flush();

            while (socket.isConnected()) {
                writer.write(scanner.nextLine());
                writer.newLine();
                writer.flush();
            }
        } catch (IOException IOe) {
            closeEverything (socket, writer, reader);
            System.out.println("exception in sendMessage");
        }
    }

    public void listenMessage () {
        new Thread (new Runnable(){
            @Override
            public void run(){
                try {
                    while (socket.isConnected()) {
                        System.out.println(reader.readLine());
                    }
                } catch(IOException IOe) {
                    closeEverything(socket, writer, reader);
                    System.out.println("exception in listenMessage");
                }
            }

        }). start();
    }

    private void closeEverything (Socket socket, BufferedWriter writer, BufferedReader reader) {
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
            System.out.println("exception in closeEverything client side");
        }
    }

    public static void main(String[] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter your username");
        String username = scanner.nextLine();
        Socket socket = new Socket("localHost", 1234);
        Client client = new Client (socket,username);
        client.listenMessage();
        client.sendMessage();

    }
}

