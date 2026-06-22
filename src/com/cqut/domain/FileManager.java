package com.cqut.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;

public class FileManager {
    public static final Gson gson=new  GsonBuilder().setPrettyPrinting().create();

    public static void saveUser(ArrayList<User> users, String filePath) {
        try (FileWriter fw=new FileWriter(filePath)) {
            fw.write(gson.toJson(users));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<User> loadUser(String filePath) {
        try (FileReader fr = new FileReader(filePath)) {
            return gson.fromJson(fr, new TypeToken<ArrayList<User>>(){}.getType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}