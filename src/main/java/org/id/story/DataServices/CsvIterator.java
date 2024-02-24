package org.id.story.DataServices;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.Map;

import static org.id.story.DataServices.CsvHelper.getLineInMap;
import static org.id.story.DataServices.CsvHelper.getPathFromResources;


public class CsvIterator implements Iterator<Map<String, String>>, AutoCloseable {

    private final RandomAccessFile reader;
    private final Map<String, Integer> columnIndexes;
    private String currentLine;
    public CsvIterator(String filePath) throws IOException {

        this.reader = new RandomAccessFile(getPathFromResources(filePath), "r");
        this.currentLine = null;
        this.columnIndexes = CsvHelper.getColumnIndexesFromLine(this.reader.readLine());
    }
    @Override
    public boolean hasNext() {
        try {
            currentLine = reader.readLine();
            return currentLine != null;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<String, String> next() {

        if (currentLine == null) {
            throw new IllegalStateException("Call hasNext() before calling next()");
        }

        try {
            return getLineInMap(currentLine, columnIndexes);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void close() throws IOException {
        reader.close();
    }


}

