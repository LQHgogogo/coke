package com.cqut.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class FileManager {
    public static final Gson gson=new  GsonBuilder().setPrettyPrinting().create();

    public static void saveUser(User[] user, String filePath) {
        try (FileWriter fw=new FileWriter(filePath)) {
            fw.write(gson.toJson(user));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static User[] loadUser(String filePath) {
        try (FileReader fr = new FileReader(filePath)) {
            return gson.fromJson(fr, User[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}