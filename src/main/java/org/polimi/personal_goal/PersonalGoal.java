package org.polimi.personal_goal;

import org.polimi.Card;
import org.polimi.Coordinates;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.lang.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class PersonalGoal {
    private static final int DIM = 6; //number of coordinates that is necessary to describe the personal goal
    private File file;
    private final Coordinates[] coordinates;
    private final Card.Color[] colors;

    public PersonalGoal() {
        String filePath = new File("").getAbsolutePath();
        file = new File(filePath.concat("/src/main/java/org/polimi/personal_goal"));
        System.out.println(file.getName());
        System.out.println(file.getAbsolutePath());
        coordinates = new Coordinates[DIM];
        colors = new Card.Color[DIM];
        readFile();
    }
    
    

    private void readFile() {
        JSONParser jsonParser = new JSONParser();

        Object obj;
        try (FileReader reader = new FileReader("personal_goal.json")) {
            //Read JSON file
            obj = jsonParser.parse(reader);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray goalList = (JSONArray) obj;
        System.out.println(obj);
        JSONObject goal0 = (JSONObject) goalList.get(0);
        System.out.println(goal0);
        JSONObject goal1 = (JSONObject) goalList.get(1);
        System.out.println(goal1);
        JSONArray coordinatesAndColorList = (JSONArray) goal1.get("goal1");
        System.out.println(coordinatesAndColorList);
        JSONObject coordinatesAndColor;
        JSONObject coordinates;
        String color;
        Long x, y;
        for (int i = 0; i < DIM; i ++) {
            coordinatesAndColor = (JSONObject) coordinatesAndColorList.get(i);
            System.out.println(coordinatesAndColor);

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
    }

    public int scoreAchieved(Card[][] grid) {
        int col, row;
        int count = 0;
        for (int i = 0; i < DIM; i ++) {
            col = coordinates[i].getX();
            row = coordinates[i].getY();
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
}
