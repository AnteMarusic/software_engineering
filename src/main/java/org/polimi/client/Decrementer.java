package org.polimi.client;

public class Decrementer implements Runnable {
    private RMIClient client;

    public Decrementer(RMIClient client) {
        this.client = client;
    }

    @Override
    public void run () {
        while (true) {
            this.client.decrementCountDown();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("interrupted exception in decrementer");
            }
        }
    }
}
