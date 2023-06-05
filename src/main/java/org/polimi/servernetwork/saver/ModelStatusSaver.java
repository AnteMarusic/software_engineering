package org.polimi.servernetwork.saver;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.polimi.servernetwork.model.Board;
import org.polimi.servernetwork.model.Bookshelf;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ModelStatusSaver {
    /*
    //to do: move the cursor of the reader back to the beginning of the file
    private static final int ROW = 6;
    private static final int COL = 5;

    private File file;
    private static String path = "/src/main/resources/";
    private int gameCode;
    private FileWriter writer;
    private FileReader reader;


    public ModelStatusSaver(int gameCode) throws IOException {
        this.gameCode = gameCode;
        String filePath = new File("").getAbsolutePath();
        System.out.println(filePath.concat(path + gameCode + ".json"));
        this.file = new File(filePath.concat(path + gameCode + ".json"));
        this.file.createNewFile();
        this.writer = new FileWriter(filePath.concat(path + gameCode + ".json"));
        this.reader = new FileReader(filePath.concat(path + gameCode + ".json"));
    }

    private void openWriter () throws IOException {
        this.writer = new FileWriter(this.file);
    }

    private void openReader () throws IOException {
        this.reader = new FileReader(this.file);
    }
    private JSONObject toJson (Coordinates c) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("row", c.getRow());
        jsonObject.put("col", c.getCol());
        return jsonObject;
    }

    private JSONObject toJson (Card c) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("color", c.getColor().toString());
        jsonObject.put("state", c.getState().toString());
        return jsonObject;
    }

    private String toJson (Map<Coordinates, Card> board) {
        String json;
        JSONArray jsonArray = new JSONArray();
        board.keySet().forEach((key) -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("coordinates", toJson(key));
            jsonObject.put("card", toJson(board.get(key)));
            jsonArray.add(jsonObject);
        });
        return json = jsonArray.toJSONString();
    }

    private String toJson (Card[][] bookshelf) {
        String json;
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("coordinates", toJson(new Coordinates(i, j)));
                if (bookshelf[i][j] == null)
                    jsonObject.put("card", null);
                else
                    jsonObject.put("card", toJson(bookshelf[i][j]));
                jsonArray.add(jsonObject);
            }
        }
        return json = jsonArray.toJSONString();
    }

    public void save(Map<Coordinates, Card> board) {
        try{
            JSONParser jsonParser = new JSONParser();
            openReader();
            Object obj = jsonParser.parse(reader);
            reader.close();
            JSONObject jsonObject = (JSONObject) obj;
            System.out.println("save board");
            System.out.println(jsonObject.toJSONString());
            jsonObject.remove("board");
            jsonObject.put("board", toJson(board));
            openWriter();
            writer.write(jsonObject.toJSONString());
            writer.close();
        } catch(IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void save (Card[][] bookshelf) {
        try{
            JSONParser jsonParser = new JSONParser();
            openReader();
            Object obj = jsonParser.parse(reader);
            reader.close();
            JSONObject jsonObject = (JSONObject) obj;
            System.out.println("save bookshelf");
            System.out.println(jsonObject.toJSONString());
            jsonObject.remove("bookshelf");
            jsonObject.put("bookshelf", toJson(bookshelf));
            openWriter();
            writer.write(jsonObject.toJSONString());
            writer.close();
        } catch(IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void initializeFile () {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("board", "");
        jsonObject.put("bookshelf", "");
        try {
            writer.write(jsonObject.toJSONString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Card.State toState (String s) {
        return switch (s) {
            case "PICKABLE" -> Card.State.PICKABLE;
            case "NOT_PICKABLE" -> Card.State.NOT_PICKABLE;
            case "IN_BAG" -> Card.State.IN_BAG;
            case "IN_BOOKSHELF" -> Card.State.IN_BOOKSHELF;
            default -> throw new IllegalArgumentException();
        };
    }

    private Card.Color toColor (String c) {
        return switch (c) {
            case "BLUE" -> Card.Color.BLUE;
            case "GREEN" -> Card.Color.GREEN;
            case "CYAN" -> Card.Color.CYAN;
            case "WHITE" -> Card.Color.WHITE;
            case "ORANGE" -> Card.Color.ORANGE;
            case "PINK" -> Card.Color.PINK;
            default -> throw new IllegalArgumentException();
        };
    }

    public Map<Coordinates, Card> board () {
        JSONParser jsonParser = new JSONParser();
        Object obj;
        Map<Coordinates, Card> board = new HashMap<>();
        Long row, col;
        int intRow, intCol;
        try {
            openReader();
            obj = jsonParser.parse(reader);
            reader.close();
            JSONArray jsonArray = (JSONArray) obj;
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                JSONObject coordinates = (JSONObject) jsonObject.get("coordinates");
                JSONObject card = (JSONObject) jsonObject.get("card");
                row = (long) coordinates.get("row");
                col = (long) coordinates.get("col");
                intRow = row.intValue();
                intCol = col.intValue();
                board.put(new Coordinates(intRow, intCol), new Card(toColor((String) card.get("color")), toState(((String) card.get("state")))));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return board;
    }

    public Card[][] bookshelf () {
        JSONParser jsonParser = new JSONParser();
        Object obj;
        Card[][] bookshelf = new Card[6][5];
        Long row, col;
        int intRow, intCol;
        try {
            openReader();
            obj = jsonParser.parse(reader);
            reader.close();
            JSONArray jsonArray = (JSONArray) obj;
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                JSONObject coordinates = (JSONObject) jsonObject.get("coordinates");
                JSONObject card = (JSONObject) jsonObject.get("card");
                row = (long) coordinates.get("row");
                col = (long) coordinates.get("col");
                intRow = row.intValue();
                intCol = col.intValue();
                bookshelf[intRow][intCol] = new Card(toColor((String) card.get("color")), toState(((String) card.get("state"))));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return bookshelf;
    }


    public static void main(String[] args) throws IOException {
        ModelStatusSaver modelStatusSaver = new ModelStatusSaver(123);
        modelStatusSaver.initializeFile();
        Board board = new Board(4);
        Bookshelf bookshelf = new Bookshelf();
        modelStatusSaver.save(bookshelf.getGrid());
        modelStatusSaver.save(board.getGrid());
        modelStatusSaver.board().forEach((key, value) -> System.out.println(key + " " + value));
        Card[][] b = modelStatusSaver.bookshelf();
        for (Card[] cards : b) {
            for (Card card : cards) {
                System.out.print(card + " ");
            }
            System.out.println();
        }
    }

     */
}
