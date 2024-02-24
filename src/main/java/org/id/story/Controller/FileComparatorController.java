package org.id.story.Controller;

import org.id.story.DataServices.CsvIDReader;
import org.id.story.DataServices.CsvIterator;
import org.id.story.DataServices.MapComparerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        while (sourceIterator.hasNext()) {
            Map<String, String> sourceRow = sourceIterator.next();
            for (CsvIDReader endReader : endReaders) {
                String fileSearch = "FILE" + (endReaders.indexOf(endReader) + 1);
                String isSearch = sourceRow.get(fileSearch);
                if (isSearch == null) {
                    throw new RuntimeException("Config consists different number of files than described in the source file.");
                }
                if ("1".equals(isSearch)) {
                    CompareRow(sourceRow, endReader, fileSearch);
                }
            }
        }
    }

    private void CompareRow(Map<String, String> sourceRow, CsvIDReader endReader, String fileSearch) {
        MapComparerService<String, String> mcs =  new MapComparerService<>();
        String recordID = sourceRow.get("ID");
        Map<String, String> endRow = endReader.getMapRowById(Integer.parseInt(recordID));
        if (endRow == null) {
            System.out.println("ID: "+ recordID +" not found in file: " + fileSearch);
        }
        if (!mcs.compareMaps(endRow,sourceRow)) {
            System.out.println("Values from source and End file are not equal. ID of record is: "
                    + recordID + " and file: " + fileSearch);
        }
    }


    private List<CsvIDReader> initEndReaders(List<String> endFiles) throws IOException {
        List<CsvIDReader> endReaders = new ArrayList<>();

        for (String outputFile : endFiles) {
            endReaders.add(new CsvIDReader(outputFile));


        }
        return endReaders;
    }


}
