package org.polimi.servernetwork.model;

import java.io.Serializable;

public class Card implements Serializable {
    private State state;
    private final Color color;

    private int type;

    public Card(Color color, State state,int type) {
        this.setState(state);
        this.color = color;
        this.type=type;
    }
    public Card(Color color, State state) {
        this.setState(state);
        this.color = color;
        this.type=0;
    }

    public enum Color {
        PINK,
        CYAN,
        ORANGE,
        WHITE,
        GREEN,
        BLUE
    }

    public enum State{
        PICKABLE,
        NOT_PICKABLE,
        IN_BAG,
        IN_BOOKSHELF,
    }

    public void setState(State state){
        this.state = state;
    }


    public State getState() {
        return this.state;
    }

    public Color getColor() {
        return this.color;
    }

    public int getType(){return this.type;}

    @Override
    public String toString() {
        return "Card{" +
                "state=" + state +
                ", color=" + color +
                '}';
    }
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card that = (Card) o;
        return state == that.getState() && color == that.getColor();
    }
    public char convertColorToChar() {
        if (this.color.equals(Card.Color.GREEN)) {
            return 'G';
        }
        if (this.color.equals(Card.Color.CYAN)) {
            return 'C';
        }
        if (this.color.equals(Card.Color.BLUE)) {
            return 'B';
        }
        if (this.color.equals(Card.Color.WHITE)) {
            return 'W';
        }
        if (this.color.equals(Card.Color.ORANGE)) {
            return 'O';
        }
        if (this.color.equals(Card.Color.PINK)) {
            return 'P';
        }
        return '?';
    }
}