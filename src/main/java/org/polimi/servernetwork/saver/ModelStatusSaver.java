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

/**
 * the JsonObject representing the whole model is called whole through the code
 */
public class ModelStatusSaver {
    //to do: move the cursor of the reader back to the beginning of the file
    private static final int ROW = 6;
    private static final int COL = 5;

    private File file;
    private static String path = "/src/main/resources/json/";
    private int gameCode;
    private FileWriter writer;
    private FileReader reader;

    /**
     * creates a json file with the game id as the name. Initializes the readers and writers
     * @param gameCode game code of the game to be saved
     * @throws IOException in case the file creation fails
     */
    public ModelStatusSaver(int gameCode) throws IOException {
        this.gameCode = gameCode;
        String filePath = new File("").getAbsolutePath();
        System.out.println(filePath.concat(path + gameCode + ".json"));
        this.file = new File(filePath.concat(path + gameCode + ".json"));
        this.file.createNewFile();
        this.writer = new FileWriter(filePath.concat(path + gameCode + ".json"));
        this.reader = new FileReader(filePath.concat(path + gameCode + ".json"));
    }

    /**
     * creates a new writer for the file
     * @throws IOException in case the creation of the writer fails
     */
    private void openWriter () throws IOException {
        this.writer = new FileWriter(this.file);
    }
    /**
     * creates a new reader for the file
     * @throws IOException in case the creation of the reader fails
     */
    private void openReader () throws IOException {
        this.reader = new FileReader(this.file);
    }
    /**
     * creates the whole object and its attribute, setting them to null. In the end writes them on the file
     */
    private void initializeFile () {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("board", null);
        jsonObject.put("bookshelf", null);
        try {
            writer.write(jsonObject.toJSONString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("(ModelStatusSaver initializeFile) can't write the default values in the file");
        }
    }
    /**
     * method that prints the content of whole object
     * @param whole JSONObject coming from the file
     */
    private void printWholeJsonObject(JSONObject whole) {
        Object obj;
        obj = whole.get("bookshelf");
        System.out.println ("(ModelStatusSaver printJsonObject) bookshelf:  " + bookshelf_fromJsonToString(obj));
        obj = whole.get("board");
        System.out.println ("(ModelStatusSaver printJsonObject) board:  " + board_fromJsonToString(obj));
    }
    /**
     * transform a string representation of the state into the corresponding state
     * @param s string to be transformed
     * @return state result of the transformation
     */
    private Card.State toState (String s) {
        return switch (s) {
            case "PICKABLE" -> Card.State.PICKABLE;
            case "NOT_PICKABLE" -> Card.State.NOT_PICKABLE;
            case "IN_BAG" -> Card.State.IN_BAG;
            case "IN_BOOKSHELF" -> Card.State.IN_BOOKSHELF;
            default -> throw new IllegalArgumentException();
        };
    }
    /**
     * transform a string representation of the color into the corresponding color
     * @param c string to be transformed
     * @return color result of the transformation
     */
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
    /**
     * transforms an object of type Coordinates into a JsonObject
     * @param c object to be transformed
     * @return the JsonObject result of the transformation
     */
    private JSONObject toJson (Coordinates c) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("row", c.getRow());
        jsonObject.put("col", c.getCol());
        return jsonObject;
    }
    /**
     * transforms an object of type Card into a JsonObject
     * @param c object to be transformed
     * @return the JsonObject result of the transformation
     */
    private JSONObject toJson (Card c) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("color", c.getColor().toString());
        jsonObject.put("state", c.getState().toString());
        return jsonObject;
    }
    /**
     * gets a map representing a board and transforms it in a JSONArray
     * @param board map to be transformed
     * @return JSONArray result of the transformation
     */
    private JSONArray toJson (Map<Coordinates, Card> board) {
        String json;
        JSONArray jsonArray = new JSONArray();
        board.keySet().forEach((key) -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("coordinates", toJson(key));
            jsonObject.put("card", toJson(board.get(key)));
            jsonArray.add(jsonObject);
        });
        return jsonArray;
    }

    /**
     * gets a json object formatted in the way specified in toJson and returns a string version of it
     * @param json object to be transformed
     * @return transformed string
     */
    private String board_fromJsonToString(Object json) {
        if (json != null) {
            JSONArray jsonArray = (JSONArray) json;
            return jsonArray.toJSONString();
        }
        else {
            return null;
        }
    }

    /**
     * inverse transformation of the one specified in the method toJson
     * @param json object coming from the json file to be transformed
     * @return a map that contains the cards of the board
     */
    public Map<Coordinates, Card> board_fromJsonToMap(Object json) {
        Map<Coordinates, Card> board = new HashMap<>();
        Long row, col;
        int intRow, intCol;
        JSONArray jsonArray = (JSONArray) json;
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            JSONObject coordinates = (JSONObject) jsonObject.get("coordinates");
            JSONObject card = (JSONObject) jsonObject.get("card");
            row = (long) coordinates.get("row");
            col = (long) coordinates.get("col");
            intRow = row.intValue();
            intCol = col.intValue();
            board.put(new Coordinates(intRow, intCol), new Card(toColor((String) card.get("color")), toState(((String) card.get("state"))), 0));
        }
        return board;
    }
    /**
     * gets a card matrix representing a bookshelf and transforms it in a JSONArray
     * @param bookshelf card matrix to be transformed
     * @return JSONArray result of the transformation
     */
    private JSONArray toJson (Card[][] bookshelf) {
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
        return jsonArray;
    }
    /**
     * inverse transformation of the one specified in the method toJson
     * @param json object coming from the json file to be transformed
     * @return card matrix result of the transformation
     */
    private Card[][] bookshelf_fromJsonToCardMatrix(Object json) {
        Card[][] bookshelf = new Card[6][5];
        Long row, col;
        int intRow, intCol;
        JSONArray jsonArray = (JSONArray) json;
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            JSONObject coordinates = (JSONObject) jsonObject.get("coordinates");
            JSONObject card = (JSONObject) jsonObject.get("card");
            row = (long) coordinates.get("row");
            col = (long) coordinates.get("col");
            intRow = row.intValue();
            intCol = col.intValue();
            bookshelf[intRow][intCol] = new Card(toColor((String) card.get("color")), toState(((String) card.get("state"))), 0);
        }
        return bookshelf;
    }
    /**
     * gets a json object formatted in the way specified in toJson and returns a string version of it
     * @param json object to be transformed
     * @return transformed string
     */
    private String bookshelf_fromJsonToString(Object json) {
        if (json != null) {
            JSONArray jsonArray = (JSONArray) json;
            return jsonArray.toJSONString();
        }
        else {
            return null;
        }
    }
    /**
     * save method for board
     * it changes the field board of the JsonObject representing the model (whole) with the JsonObject representing the new
     * board
     * @param board
     */
    public void save(Map<Coordinates, Card> board) {
        try{
            JSONParser jsonParser = new JSONParser();
            openReader();
            Object obj = jsonParser.parse(reader);
            reader.close();
            JSONObject whole = (JSONObject) obj;
            System.out.println("(ModelStatusSaver save board) print the json object representing the whole model before save     ");
            printWholeJsonObject(whole);
            whole.remove("board");
            whole.put("board", toJson(board));
            openWriter();
            writer.write(whole.toJSONString());
            writer.close();
            //debug
            openReader();
            obj = jsonParser.parse (reader);
            whole = (JSONObject) obj;
            System.out.println("(ModelStatusSaver save board) print the json object representing the whole model after save    ");
            printWholeJsonObject(whole);
        } catch(IOException | ParseException e) {
            System.out.println("(ModelStatusSaver save board) error: can't properly save the board");
        }
    }
    /**
     * save method for bookshelf
     * it changes the field bookshelf of the JsonObject representing the model with the JsonObject representing the new
     * bookshelf
     * @param bookshelf
     */
    public void save (Card[][] bookshelf) {
        try{
            JSONParser jsonParser = new JSONParser();
            openReader();
            Object obj = jsonParser.parse(reader); //parse the json object that represents the model
            reader.close();
            JSONObject whole = (JSONObject) obj;
            System.out.println("(ModelStatusSaver save bookshelf) print the json object representing the whole model before save    ");
            printWholeJsonObject(whole);
            whole.remove("bookshelf");
            whole.put("bookshelf", toJson(bookshelf));
            openWriter();
            writer.write(whole.toJSONString());
            writer.close();
            //debug
            openReader();
            obj = jsonParser.parse (reader);
            whole = (JSONObject) obj;
            System.out.println("(ModelStatusSaver save bookshelf) print the json object representing the whole model after save    ");
            printWholeJsonObject(whole);
        } catch(IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    /**
     * method that gets the board from the file
     * @return the map representing the board
     */
    public Map<Coordinates, Card> getBoard () {
        JSONParser jsonParser = new JSONParser();
        Map<Coordinates, Card> board;
        try {
            openReader();
            Object wholeObject = jsonParser.parse(reader);
            reader.close();
            JSONObject whole = (JSONObject) wholeObject;
            if (whole.get("board") != null) {
                board = board_fromJsonToMap(whole.get("board"));
                return board;
            }
            else return null;
        } catch (IOException | ParseException e) {
            System.out.println("(ModelStatusSaver getBoard) error: impossible to get board");
            return null;
        }
    }
    /**
     * method that gets the bookshelf from the file
     * @return the card matrix representing the bookshelf
     */
    public Card[][] getBookshelf () {
        JSONParser jsonParser = new JSONParser();
        Card[][] bookshelf;
        try {
            openReader();
            Object wholeObject = jsonParser.parse(reader);
            reader.close();
            JSONObject whole = (JSONObject) wholeObject;
            if (whole.get("bookshelf") != null) {
                bookshelf = bookshelf_fromJsonToCardMatrix(whole.get("bookshelf"));
                return bookshelf;
            }
            else return null;
        } catch (IOException | ParseException e) {
            System.out.println("(ModelStatusSaver getBookshelf) error: impossible to get bookshelf");
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        ModelStatusSaver modelStatusSaver = new ModelStatusSaver(123);
        modelStatusSaver.initializeFile();
        Board board = new Board(4);
        Bookshelf bookshelf = new Bookshelf();
        modelStatusSaver.save(bookshelf.getGrid());
        modelStatusSaver.save(board.getGrid());
        Card[][] bookshelfFromFile = modelStatusSaver.getBookshelf();
        for (int i = 0; i < ROW; i ++) {
            for (int j = 0; j < COL; j ++) {
                if (bookshelfFromFile[i][j] == null) {
                    System.out.print("N");
                }
                else {
                    System.out.print(bookshelfFromFile[i][j].convertColorToChar());
                }
            }
            System.out.println(" ");
        }
        /*
        modelStatusSaver.board_fromJsonToMap().forEach((key, value) -> System.out.println(key + " " + value));
        Card[][] b = modelStatusSaver.bookshelf_fromJsonToCardMatrix();
        for (Card[] cards : b) {
            for (Card card : cards) {
                System.out.print(card + " ");
            }
            System.out.println();
        }

         */
    }
}
