package org.example;

import java.util.Random;
public class PersonalGoal {

    private Card[][] objective = new Card[6][5];
    private int[][] occupied = new int [6][5];
    private Random rand = new Random ();
    private int x;
    private int y;
    public PersonalGoal() {

        for (Card.Colour c: Card.Colour.values()) {
            x = rand.nextInt(6);
            y = rand.nextInt(5);

            while (occupied[x][y] == 1) {
                x = rand.nextInt(6);
                y = rand.nextInt(5);
            }
            occupied[x][y] = 1;
            objective[x][y] = new Card(c);

        }


    } //inserisce in 6 posizioni random una carta e tutte le altre posizioni le mette a null

    public boolean achieved (BookShelf b) {
        int i, j;
        Card[][] g = BookShelf.getGrid();
        for (i = 0; ) {
            for (j = 0; ) {
                if (objective[i][j] != null && !g.getCard(i, j).equals(objective[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }




}
