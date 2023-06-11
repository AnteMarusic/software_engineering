package org.polimi.servernetwork.server;

import org.polimi.client.RMICallback;

import java.rmi.RemoteException;

public class Pinger implements Runnable{
    private String username;
    private RMICallback client;
    private RMIServer server;

    public Pinger (RMICallback client, RMIServer server, String username) {
        this.client = client;
        //riferimento a server mi sa che è necessario perché se il client si disconnette va rimosso dalla mappa del server
        this.server = server;
        this.username = username;
    }

    public void run () {
        while (true) {
            try {
                Thread.sleep(1000);
                client.ping();
            } catch (InterruptedException e) {
                System.out.println("interrupted exception in pinger");
            } catch (RemoteException e) {
                server.disconnect(this.username);
            }
        }
    }
}
