package org.polimi.servernetwork.server;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class RMIServer {
    private final Server server;
    private final int port;

    RMIServer(Server server, int port) {
        this.server = server;
        this.port = port;
    }
    public void startServer () {
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            RMIImplementation impl= new RMIImplementation(server);
            registry.bind("server", impl);
        } catch (IOException | AlreadyBoundException e) {
            System.out.println("errore");
        }
        System.out.println("RMI server is up");
    }
}
