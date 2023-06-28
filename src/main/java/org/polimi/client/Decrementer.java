package org.polimi.client;

public class Decrementer implements Runnable {
    private RMIClient client;

    public Decrementer(RMIClient client) {
        this.client = client;
    }


    /**
     * Overrides the run method of the Thread class.
     * Decrements the countDown value of the associated client continuously until interrupted.
     * Sleeps for 1000 milliseconds (1 second) between each decrement.
     * If interrupted, it prints a message indicating the interrupted exception.
     */
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
