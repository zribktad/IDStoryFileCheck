package org.id.story;

import org.id.story.Config.ConfigReader;
import org.id.story.Controller.FileComparatorController;
import org.id.story.Controller.FileComparatorController.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;


public class FileComparator {
    static ConfigReader configReader;
    public static void main(String[] args) {

        FileComparatorStart();
    }

    private static void FileComparatorStart() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            configReader = new ConfigReader("config.json");

            long interval = configReader.getLongProperty("checkInterval");
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

        String sourceFilePath = configReader.getStringProperty("sourceFile");
        List<String> endFiles = configReader.getListProperty("endFiles");
        FileComparatorController controller = new FileComparatorController();
        while (true) {
            controller.startComparison(sourceFilePath,endFiles);
            if (reader.ready()) {
                String userInput = reader.readLine();
                if (userInput.equalsIgnoreCase("stop")) {
                    break;
                }
            }
            Thread.sleep(interval);
        }
    }
}