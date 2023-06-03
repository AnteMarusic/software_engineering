package org.polimi.client;

import org.polimi.client.RMIClient;
import org.polimi.client.SocketClient;
import org.polimi.messages.Message;
import org.polimi.messages.UsernameStatus;
import org.polimi.servernetwork.server.RMIinterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientStarter {
    public static void main(String[] args) throws IOException, NotBoundException {
        Scanner scanner = new Scanner(System.in);
        int input;
        boolean bool;
        RMIinterface server= null;
        System.out.println("choose rmi or socket");
        System.out.println("(1) rmi");
        System.out.println("(2) scoket");
        System.out.println("type 1 or 2");
        input = scanner.nextInt();
        if(input == 1){
            RMIClient rmiClient = new RMIClient(1099);
            do {
                bool = rmiClient.startConnection();
            } while (!bool);
            rmiClient.login();
            while (rmiClient.ifConnected()) {
                // messagefromserver potrebbe essere null
                if (rmiClient.messagesToRead()) {
                    Message messageFromServer=rmiClient.popMessageRMI();
                    System.out.println("questo dal server " + messageFromServer);
                    Message message = rmiClient.handleMessage(messageFromServer);
                    if (message != null)
                        rmiClient.sendMessage(message);
                }
            }
        }
        else if(input==2){
            SocketClient socket = new SocketClient(8181);
        }
    }
}
