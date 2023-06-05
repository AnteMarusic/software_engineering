package org.polimi.servernetwork.controller;

import java.util.ArrayList;
import java.util.List;

public class Decrementer implements Runnable {
    private final UsernameIssuer usernameIssuer;
    public Decrementer(UsernameIssuer usernameIssuer) {
        this.usernameIssuer = usernameIssuer;
    }
    @Override
    public void run () {
        while (true) {
            this.usernameIssuer.getActiveClientHandlers().forEach(ClientHandler::decreaseCountDown);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("interrupted exception in decrementer");
            }

        }
    }

}
