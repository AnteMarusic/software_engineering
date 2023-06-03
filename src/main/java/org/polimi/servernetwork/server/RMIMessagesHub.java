package org.polimi.servernetwork.server;

import org.polimi.client.RMIClient;
import org.polimi.messages.Message;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

public class RMIMessagesHub {
    private ArrayList<RMIClient> array;
    private static RMIMessagesHub serverInstance;

    public static RMIMessagesHub getInstance() {
        if (serverInstance == null) {
            serverInstance = new RMIMessagesHub();
        }
        return serverInstance;
    }
    public RMIMessagesHub(){
        this.array = new ArrayList<>();
    }

    public void onMessage(Message message){
        RMIClient rmiClient1 = null;
        Optional<RMIClient> rmiClient = Stream.of(array)
                .flatMap(ArrayList::stream)
                .filter(rmiclient -> rmiclient.getUsername().equals(message.getUsername()))
                .findFirst();
        if (rmiClient.isPresent()) {
            rmiClient1 = rmiClient.get();
        } else {
            System.out.println("Optional is empty.");
        }
        rmiClient1.addMessage(message);
    }
    public void addRMIClient(RMIClient rmiclient){
        array.add(rmiclient);
    }
}

