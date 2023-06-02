package org.polimi.servernetwork.controller;

public class Decrementer implements Runnable {
    private final UsernameIssuer usernameIssuer;
    public Decrementer(UsernameIssuer usernameIssuer) {
        this.usernameIssuer = usernameIssuer;
    }
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
