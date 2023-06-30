package org.polimi.messages;

import org.jetbrains.annotations.NotNull;
import org.polimi.GameRules;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.io.*;
import java.nio.Buffer;
import java.util.Map;

public class BoardMessage extends Message implements Serializable {
    private final Map<Coordinates, Card> board;

    public BoardMessage (Map<Coordinates, Card> board){
        super("server", MessageType.BOARDMESSAGE);
        this.board = board;
    }

    public Map<Coordinates, Card> getBoard() {
        return board;
    }
    @Override
    public String toString() {
        return mapToString(board);
    }

    public static String mapToString(Map<?, ?> map) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey())
                    .append(" = ")
                    .append(entry.getValue())
                    .append(", ");
        }

        // Remove the trailing comma and space
        if (stringBuilder.length() > 2) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        return stringBuilder.toString();
    }

}
