package org.polimi.servernetwork.controller;

public class DecrementerGameController implements Runnable{
    private final GameController gameController;
    public DecrementerGameController(GameController gameController) {
        this.gameController = gameController;
    }
    @Override
    public void run () {
        while (true) {
            this.gameController.decreaseCountDown();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("interrupted exception in DecrementerGameController");
            }

        }
    }
}
