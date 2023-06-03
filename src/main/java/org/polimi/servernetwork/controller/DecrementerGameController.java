package org.polimi.servernetwork.controller;

public class DecrementerGameController implements Runnable{
    private final GameController gameController;
    private boolean isRunning;
    public DecrementerGameController(GameController gameController) {
        this.gameController = gameController;
        isRunning = true;
    }
    @Override
    public void run () {
        while (isRunning) {
            this.gameController.decreaseCountDown();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("interrupted exception in DecrementerGameController");
            }
        }
    }

    public void stop(){
        isRunning=false;
    }
}
