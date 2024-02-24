package org.id.story;

import org.id.story.Controller.FileComparatorController;
import org.id.story.Controller.FileComparatorController.*;

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
            mainLoop(reader, interval);
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

    private static void mainLoop(BufferedReader reader, long interval) throws IOException, InterruptedException {
        String userInput;
        FileComparatorController controller = new FileComparatorController();
        while (true) {
            controller.startComparison();
            if (reader.ready()) {
                userInput = reader.readLine();
                if (userInput.equalsIgnoreCase("stop")) {
                    break;
                }
            }
            Thread.sleep(interval);
        }
    }
}