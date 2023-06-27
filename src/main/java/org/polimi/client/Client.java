package org.polimi.client;

import org.polimi.messages.Message;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Scanner;

public abstract class Client {
    public String username;
    private int port;

    Client(int port){
        this.port = port;
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
