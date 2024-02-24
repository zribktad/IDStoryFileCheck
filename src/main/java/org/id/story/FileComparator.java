package org.id.story;

import org.id.story.Controller.FileComparatorController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class FileComparator {
    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        long interval = 1000;

        try {
            while (true) {
                //Code
                System.out.println("Comparing files...");
                if (reader.ready()) {
                    userInput = reader.readLine();
                    if (userInput.equalsIgnoreCase("stop")) {
                        break;
                    }
                }
                Thread.sleep(interval);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}