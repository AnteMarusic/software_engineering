package org.polimi.client;

import javafx.application.Application;
import org.polimi.client.RMIClient;
import org.polimi.client.SocketClient;
import org.polimi.client.view.gui.Gui;
import org.polimi.messages.Message;
import org.polimi.messages.RMIAvailability;
import org.polimi.messages.UsernameStatus;
import org.polimi.servernetwork.server.RMIinterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Access point to the client application, it starts with a first choice through the command
 * line interface for choosing in which view mode you are going to play.
 * If you choose to play with the CLI, it asks for connection mode (RMI or Socket), otherwise it launches
 * the first GUI scene
 */

public class ClientStarter {
    private static final int rmiPort = 1099;
    private static final int socketPort = 8181;
    private static final int NUMBER_OF_TRIES = 1000;
    public static void main(String[] args) throws IOException, NotBoundException {
        Scanner scanner = new Scanner(System.in);
        int input;
        boolean bool;
        boolean firstWhile = true;
        boolean secondWhile = true;
        int numberOfTries = NUMBER_OF_TRIES;

        if (args.length == 0) {
            System.out.println("invalid parameters");
            return;
        }
        System.out.println("ip: " + args[0]);
        Client.setServerIp(args[0]);
        while (firstWhile) {
            System.out.println("choose cli or gui");
            System.out.println("(1) cli");
            System.out.println("(2) gui");
            System.out.println("type 1 or 2");
            if (scanner.hasNextInt()) {
                // If the next input is an integer
                input = scanner.nextInt();
                if (input == 1) {
                    firstWhile = false;
                    while (secondWhile) {
                        System.out.println("choose rmi or socket");
                        System.out.println("(1) rmi");
                        System.out.println("(2) socket");
                        if (scanner.hasNextInt()) {
                            input = scanner.nextInt();
                            if (input == 1) {
                                secondWhile = false;
                                RMIClient rmiClient = new RMIClient(rmiPort, false);
                                do {
                                    bool = rmiClient.startConnection();
                                    numberOfTries --;
                                    if (bool) {
                                        break;
                                    }
                                } while (numberOfTries > 0);
                                if (!bool) {
                                    System.out.println("RMI connection failed, restart the client to retry");
                                    break;
                                }
                                rmiClient.login();
                            } else if (input == 2) {
                                secondWhile = false;
                                SocketClient socket = new SocketClient(socketPort, false);
                                bool = socket.connect();
                                if (!bool) {
                                    System.out.println("Socket connection failed, restart the client to retry");
                                    break;
                                }
                            } else {
                                System.out.println("Invalid input. please enter 1 or 2.");
                                scanner.nextLine();
                            }
                        } else {
                            // If the next input is not an integer
                            System.out.println("Invalid input. Please enter an integer.");
                            scanner.next();
                        }
                    }
                }
                else if (input == 2) {
                    firstWhile = false;
                    Application.launch(Gui.class);
                }
                else {
                    System.out.println("Invalid input. please enter 1 or 2.");
                    scanner.nextLine();
                }
            }
            else {
                // If the next input is not an integer
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine();
            }
        }

    }

     /*while (rmiClient.ifConnected()) {
                // messagefromserver potrebbe essere null
                RMIAvailability status = RMIAvailability.NOT_AVAILABLE;
                try{
                    status = rmiClient.getServer().messagesAvailable(rmiClient.getUsername());
                }
                catch ( RemoteException e){
                    // gestire la disconnessione del server
                }
                //System.out.println(status);
                if(status == RMIAvailability.AVAILABLE){
                    try{
                        messageFromServer=rmiClient.getServer().getMessage(rmiClient.getUsername());
                    }
                    catch ( RemoteException e){
                        // gestire la disconnessione del server
                    }
                    message = rmiClient.handleMessage(messageFromServer);
                    if (message != null)
                        rmiClient.sendMessage(message);
                }

            }*/
}
