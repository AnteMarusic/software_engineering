package org.polimi.PersonalGoal;

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
    private Coordinates[] coord;

    public PersonalGoal() {
        file = new File("/Users/ante/Documents/UNI/Progetto_sw/personal_goal.json");
        coord = new Coordinates[DIM];
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
        JSONArray coordinatesList = (JSONArray) goal1.get("goal1");
        System.out.println(coordinatesList);
        JSONObject coordinates;
        for (int i = 0; i < DIM; i ++) {
            coordinates = (JSONObject) coordinatesList.get(i);
            System.out.println(coordinates);
            Long x = (long) coordinates.get("col");
            Long y = (long) coordinates.get("row");
            int col = x.intValue();
            int row = y.intValue();
            this.coord[i] = new Coordinates(row, col);
        }
    }

    public void print() {
        Arrays.stream(coord).forEach(System.out::println);
    }

    public int scoreAchieved() {
    }
}
