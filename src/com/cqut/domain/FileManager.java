package com.cqut.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;

public class FileManager {
    public static final Gson gson=new  GsonBuilder().setPrettyPrinting().create();

    public  static final String USER_DATA_PATH = "userdata.json";

    public static void saveUser(ArrayList<User> users, String filePath) {
        try (FileWriter fw=new FileWriter(filePath)) {
            fw.write(gson.toJson(users));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<User> loadUser(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try (FileReader fr = new FileReader(filePath)) {
            ArrayList<User> result = gson.fromJson(fr, new TypeToken<ArrayList<User>>(){}.getType());
            return result != null ? result : new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}