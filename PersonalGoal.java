package org.example;

public class PersonalGoal {
    private Card[][] objective;

    public PersonalGoal() {

    } //inserisce in 6 posizioni random una carta e tutte le altre posizioni le mette a null

    public boolean achieved (BookShelf b) {
        int i, j;
        Card[][] g = BookShelf.getGrid();
        for (i = 0; ) {
            for (j = 0; ) {
                if (objective[i][j] != null && !g.getCard(i, j).equals(objective[i][j])) {
                    return false
                }
            }
        }
        return true;
    }


}
