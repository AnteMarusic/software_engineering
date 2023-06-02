package org.polimi.client;

import org.polimi.servernetwork.server.RMIinterface;

import java.rmi.RemoteException;

public class Pinger implements Runnable{
    private final String username;
    private final RMIinterface server;
    private final RMIClient client;

    public Pinger (String username, RMIinterface server, RMIClient client) {
        this.username = username;
        this.server = server;
        this.client = client;
    }

    public void run () {
        while (true) {
            try {
                Thread.sleep(1000);
                server.ping(username);
            } catch (InterruptedException e) {
                System.out.println("interrupted exception in pinger");
            } catch (RemoteException e) {
                client.disconnect();
            }
        }
    }
}
