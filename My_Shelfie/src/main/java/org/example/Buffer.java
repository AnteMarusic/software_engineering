package org.example;

import java.util.Arrays;

public class Buffer {
    private static final int DIM = 3;
    private int bufferDim;
    private Card[] buffer;

    public Buffer (Card c1, Card c2, Card c3) {
        this.buffer = new Card[DIM];
        this.bufferDim = DIM;
        this.buffer[0] = c1;
        this.buffer[1] = c2;
        this.buffer[2] = c3;
    }

    public Buffer (Card c1, Card c2) {
        this.buffer = new Card[DIM - 1];
        this.bufferDim = DIM - 1;
        this.buffer[0] = c1;
        this.buffer[1] = c2;
    }

    public Buffer (Card c1) {
        this.buffer = new Card[DIM - 2];
        this.bufferDim = DIM - 2;
        this.buffer[0] = c1;
    }

    /*
     * @requires 0 <= a.length <= 2 && \forall (int i; i < a.length; !\exists (int j; j > i; a[i] == a[j]))
     *           && a.length == getBuffer.length
     * @ensures
     */
    public void order (int ... a) {
        Card[] tempBuffer = new Card[a.length];
        for (int i = 0; i < a.length; i ++) {
            tempBuffer[i] = this.buffer[a[i]];
        }
        this.buffer = tempBuffer;
    }

    public Card[] getBuffer () {
        Card[] tempBuffer = new Card[bufferDim];
        System.arraycopy(this.buffer, 0, tempBuffer, 0, bufferDim);
        return tempBuffer;
    }

    @Override
    public String toString() {
        return "Buffer{" +
                "bufferDim=" + bufferDim +
                ", buffer=" + Arrays.toString(buffer) +
                '}';
    }
}
