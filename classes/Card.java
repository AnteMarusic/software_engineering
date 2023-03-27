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
        IN_BUFFER
    }

    public void setState(State state){
        this.state = state;
    }

    @Override
    public String toString() {
        return "Card{" +
                "state=" + state +
                ", color=" + color +
                '}';
    }
}
