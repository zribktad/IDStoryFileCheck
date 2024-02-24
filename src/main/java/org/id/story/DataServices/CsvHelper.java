package org.id.story.DataServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CsvHelper {

    static Map<String, Integer> getColumnIndexesFromLine(String headerLine) {
        String[] columns = headerLine.split(",");
        Map<String, Integer> columnIndexes = new HashMap<>();
        for (int i = 0; i < columns.length; i++) {
            columnIndexes.put(columns[i], i);
        }
        return columnIndexes;
    }


    static Map<String, String> getLineInMap(String currentLine, Map<String, Integer> columnIndexes) throws RuntimeException {

        String[] values = currentLine.split(",");
        Map<String, String> row = new HashMap<>();
        for (Map.Entry<String, Integer> entry : columnIndexes.entrySet()) {
            String columnName = entry.getKey();
            Integer index = entry.getValue();
            if (index < values.length) {
                row.put(columnName, values[index]);
            } else {
                throw new RuntimeException("Not equal number of elements in line: " + currentLine);
            }
        }

        return row;
    }

    static String getValueFromKey(String currentLine, Map<String, Integer> columnIndexes, String key) throws RuntimeException {

        String[] values = currentLine.split(",");
        Integer index = columnIndexes.get(key);
        if (index >= values.length) {
            throw new RuntimeException("Number of column for key not found " + currentLine);
        }
        return values[index];

    }
    static String getPathFromResources(String filePath) {
        return CsvHelper.class.getClassLoader().getResource(filePath).getPath();
    }
}

