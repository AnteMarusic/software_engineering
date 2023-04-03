package org.example;

public class Card {
    private State state;
    private final Color color;

    public Card(Color color, State state) {
        this.setState(state);
        this.color = color;
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
        NON_PICKABLE,
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

    @Override
    public String toString() {
        return "Card{" +
                "state=" + state +
                ", color=" + color +
                '}';
    }
}