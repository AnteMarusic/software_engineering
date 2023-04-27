package org.polimi.client;

import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.util.HashMap;

public class ClientBookshelf {
    private static final int COL = 5;
    private static final int ROW = 6;
    final private HashMap<Coordinates, Card> grid;
    final private int[] index;
    private int maxInsertable;

    public ClientBookshelf() {
        this.grid = new HashMap<>(COL*ROW);
        this.maxInsertable = 3;
        this.index = new int[COL];
        for (int i = 0; i < COL; i ++) {
            this.index[i] = 0;
        }
    }

    public int getInsertable (int col) {
        return ROW - this.index[col];
    }

    public void print() {
        for (int i = ROW - 1; i >= 0; i --) {
            for (int j = 0; j < COL; j ++) {
                if (this.grid.get(new Coordinates(i, j)) == null) {
                    System.out.print("N");
                }
                else {
                    System.out.print(this.grid.get(new Coordinates(i, j)).convertColorToChar());
                }
            }
            System.out.println(" ");
        }
    }

    public void printMyBookshelf(){
        for (int i = ROW - 1; i >= 0; i --) {
            for(int k=0 ; k<ROW ; k++) {
                System.out.print("+—+ ");
            }
            System.out.println();
            for (int j = 0; j < COL; j ++) {
                if (this.grid.get(new Coordinates(i, j)) == null) {
                    System.out.print("|N| ");
                }
                else {
                    System.out.print("|" + this.grid.get(new Coordinates(i, j)).convertColorToChar() + "| ");
                }
            }
            System.out.println();
        }
        for(int k=0 ; k<ROW ; k++) {
            System.out.print("+—+ ");
        }
    }
}
