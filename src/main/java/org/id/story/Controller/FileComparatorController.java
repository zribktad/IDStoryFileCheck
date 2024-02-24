package org.id.story.Controller;

import org.id.story.DataServices.CsvIDReader;
import org.id.story.DataServices.CsvIterator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileComparatorController {

    public void startComparison(String sourceFilePath, List<String> endFiles) {

        System.out.println("Comparing files...");
        try{
            CsvIterator sourceIterator = new CsvIterator(sourceFilePath);
            List<CsvIDReader> endReaders = initEndReaders(endFiles);
            ProcessSource(sourceIterator, endReaders);
            sourceIterator.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void ProcessSource(CsvIterator sourceIterator, List<CsvIDReader> endReaders) {
    }

    private List<CsvIDReader> initEndReaders(List<String> endFiles) throws IOException {
        List<CsvIDReader> endReaders = new ArrayList<>();
        for (String outputFile : endFiles) {
            CsvIDReader endLineReader = new CsvIDReader(outputFile);
            endReaders.add(endLineReader);
        }
        return endReaders;
    }


}
