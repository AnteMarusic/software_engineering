package org.polimi.client;

import org.polimi.messages.Message;

public abstract class Client {
    public String username;
    private int port;

    Client(int port){
        this.port = port;
    }
    public String getUsername(){
        return this.username;
    }
    public int getPort(){
        return this.port;
    }
    abstract void sendMessage(Message message) throws Exception;
}
