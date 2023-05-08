package org.polimi.server.model.goal;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.lang.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class PersonalGoal implements Goal{
    private static final String FILENAME = "/src/main/resources/personal_goal.json";
    private static final int DIM = 6; //number of coordinates that is necessary to describe the personal goal
    private File file;
    private final Coordinates[] coordinates;
    private final Card.Color[] colors;
    private int index;

    public PersonalGoal(int index) {
        String filePath = new File("").getAbsolutePath();
        file = new File(filePath.concat(FILENAME));
        coordinates = new Coordinates[DIM];
        colors = new Card.Color[DIM];
        readFileAndPickRandomPersonalGoal(index);
        this.index=index;
    }

    /**
     * for testing purposes
     * @return the array of coordinates
     */
    public Coordinates[] getCoordinates() {
        return coordinates;
    }

    /**
     * for testing purposes
     * @return the array of colors
     */
    public Card.Color[] getColors() {
        return colors;
    }

    private void readFileAndPickRandomPersonalGoal(int index) {
        JSONParser jsonParser = new JSONParser();
        Object obj;

        try (FileReader reader = new FileReader(this.file)) {
            //Read JSON file
            obj = jsonParser.parse(reader);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray goalList = (JSONArray) obj;
        JSONObject goal = (JSONObject) goalList.get(index);
        JSONArray coordinatesAndColorList = (JSONArray) goal.get("goal");
        JSONObject coordinatesAndColor;
        JSONObject coordinates;
        String color;
        Long x, y;
        for (int i = 0; i < DIM; i ++) {
            coordinatesAndColor = (JSONObject) coordinatesAndColorList.get(i);

            coordinates = (JSONObject) coordinatesAndColor.get("coordinates") ;
            x = (long) coordinates.get("col");
            y = (long) coordinates.get("row");
            int col = x.intValue();
            int row = y.intValue();
            this.coordinates[i] = new Coordinates(row, col);

            color = (String) coordinatesAndColor.get("color");
            this.colors[i] = Card.Color.valueOf(color);
        }
    }


    public void print() {
        Arrays.stream(coordinates).forEach(System.out::println);
        Arrays.stream(colors).forEach(System.out::println);
    }

    @Override
    public int getScore(Card[][] grid) {
        int col, row;
        int count = 0;
        for (int i = 0; i < DIM; i ++) {
            col = coordinates[i].getRow();
            row = coordinates[i].getCol();
            if (grid[row][col] != null) {
                if (grid[row][col].getColor() == this.colors[i]) {
                    count ++;
                }
            }
        }
        return calculateScore(count);
    }

    private int calculateScore(int count) {
        switch(count) {
            case 0 -> {return 0;}
            case 1 -> {return 1;}
            case 2 -> {return 2;}
            case 3 -> {return 4;}
            case 4 -> {return 6;}
            case 5 -> {return 9;}
            case 6 -> {return 12;}
        }
        return -1;
    }

    public int getIndex(){
        return index;
    }

}
