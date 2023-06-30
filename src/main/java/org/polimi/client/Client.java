package org.polimi.client;

import org.polimi.messages.Message;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public abstract class Client {
    public String username;
    private static String serverIp;
    private int port;

    Client(int port) throws RemoteException {
        super();
        this.port = port;
    }
    public static void setServerIp (String ip) {
        serverIp = ip;
    }
    public String getServerIp () {
        return serverIp;
    }
    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPort(){
        return this.port;
    }
    abstract void sendMessage(Message message) throws Exception;


}
