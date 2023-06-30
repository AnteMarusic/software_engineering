package org.polimi.servernetwork.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameListFileAccessorSingleton {
    private static GameListFileAccessorSingleton instance;
    private static final Object lock = new Object();
    private final File file;
    private static String path;
    private static String folderPath;

    /**
     * Private constructor to prevent direct instantiation
     *
     */
    private GameListFileAccessorSingleton() {
        instance = this;

        System.out.println("(GameListFileAccessorSingleton) file path: " + path);
        this.file = new File (path);
        if (file.exists()) {
            System.out.println("(GameListFileAccessorSingleton) gameListFile found");
            //il server si era disconnesso e deve ricaricare i salvataggi vecchi (potrebbe pure essere che il file è vuoto e quindi non deve fare niente)
            //non devo scrivere codice qua
        } else {
            System.out.println("(GameListFileAccessorSingleton) gameListFile not found, creating it");
            try {
                this.file.createNewFile();
                initializeFile();
            } catch (IOException e) {
                System.out.println("(GameListFileAccessorSingleton) exception in file creation");
                e.printStackTrace();
            }
        }
    }

    public static void setFolderPath(String arg) {
        folderPath = arg;
        path = arg+"gameList.json";
    }

    private void initializeFile () {
        System.out.println("(GameListFileAccessorSingleton initializeFile) file initialization");
        try (FileWriter writer = new FileWriter(this.file)) {
            JSONArray whole = new JSONArray();
            writer.write(whole.toJSONString());
            writer.flush();
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("(GameListFileAccessorSingleton initializeFile) error in file initialization");
        }
    }

    public static GameListFileAccessorSingleton getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new GameListFileAccessorSingleton();
                }
            }
        }
        return instance;
    }

    public boolean isEmpty () {
        try (FileReader reader = new FileReader(file)) {
            JSONParser jsonParser = new JSONParser();
            Object wholeObject = jsonParser.parse(reader);
            JSONArray whole = (JSONArray) wholeObject;
            if (whole.isEmpty()) {
                System.out.println("(GameListFileAccessorSingleton isEmpty) empty file");
                return true;
            }
            else {
                System.out.println("(GameListFileAccessorSingleton isEmpty) not empty file");
                return false;
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("(GameListFileAccessorSingleton isEmpty) reading of the file failed");
            //metto a true così almeno non ricarico niente
            return true;
        }
    }
    private JSONArray getWhole() {
        try (FileReader reader = new FileReader(file)) {
            JSONParser jsonParser = new JSONParser();
            Object wholeObject = jsonParser.parse(reader);
            return (JSONArray) wholeObject;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("(GameListFileAccessorSingleton readFile) reading of the file failed");
            return null;
        }
    }

    public synchronized Map<Integer, List<String>> getGameIdsAndPlayers () {
        Map map = new HashMap<Integer, List<String>>();
        JSONArray whole = this.getWhole();
        for (int i = 0; i < whole.size(); i++) {
            JSONObject obj = (JSONObject) whole.get(i);
            Long temp = (long) obj.get("gameCode");
            int gameCode = temp.intValue();
            List<String> usernames = new LinkedList<>();
            JSONArray usernamesArray = (JSONArray) obj.get("playersNames");
            for (int j = 0; j < usernamesArray.size(); j ++) {
                usernames.add((String) usernamesArray.get(j));
            }
            map.put(gameCode, usernames);
        }
        return map;
    }

    public synchronized void addGameIdWithPlayers (int gameCode, List<String> usernames) {
        try (FileReader reader = new FileReader(file)) {
            JSONParser jsonParser = new JSONParser();
            Object wholeObject = jsonParser.parse(reader);
            JSONArray whole = (JSONArray) wholeObject;
            JSONArray usernamesArray = new JSONArray();
            for (int i = 0; i < usernames.size(); i++) {
                usernamesArray.add(usernames.get(i));
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("gameCode", gameCode);
            jsonObject.put("playersNames", usernamesArray);
            whole.add(jsonObject);
            try (FileWriter writer = new FileWriter(this.file)) {
                writer.write(whole.toJSONString());
                writer.flush();
            } catch(IOException e) {
                e.printStackTrace();
                System.out.println("(GameListFileAccessorSingleton addGameWithPlayers) exception in file writing");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("(GameListFileAccessorSingleton readFile) reading of the file failed");
        }
    }

    public synchronized void removeGameIdWithPlayers (int gameCode) {
        boolean flag = false;
        try (FileReader reader = new FileReader(file)) {
            JSONParser jsonParser = new JSONParser();
            Object wholeObject = jsonParser.parse(reader);
            JSONArray whole = (JSONArray) wholeObject;
            System.out.println("(GameListFileAccessorSingleton removeGameIdWithPlayers) trying to remove " + gameCode);
            for (int i = 0; i < whole.size(); i++) {
                JSONObject obj = (JSONObject) whole.get(i);
                Long temp = (long) obj.get("gameCode");
                int gameCodeInFile = temp.intValue();
                if (gameCodeInFile == gameCode) {
                    System.out.println("(GameListFileAccessorSingleton removeGameIdWithPlayers) removed");
                    flag = true;
                    whole.remove(i);
                    try (FileWriter writer = new FileWriter(this.file)) {
                        writer.write(whole.toJSONString());
                        writer.flush();
                    } catch(IOException e) {
                        e.printStackTrace();
                        System.out.println("(GameListFileAccessorSingleton removeGameIdWithPlayers) exception in file writing");
                    }
                    break;
                }
            }
            if (!flag) {
                System.out.println("(GameListFileAccessorSingleton removeGameIdWithPlayers) element " + gameCode + " not found.");
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("(GameListFileAccessorSingleton removeGameIdWithPlayers) file not found");
        }catch (IOException | ParseException e) {
            System.out.println("(GameListFileAccessorSingleton removeGameIdWithPlayers) impossible to read file");
        }
    }
}
