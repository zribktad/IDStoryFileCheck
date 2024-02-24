package org.id.story.DataServices;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.id.story.DataServices.CsvHelper.*;

public class CsvIDReader {

    private final Map<Integer, Long> idToOffset;
    private final Set<Integer> uniqueIds;
    private final String filePath;
    private final Map<String, Integer> columnIndexes;
    private final RandomAccessFile randomAccessFile;

    public CsvIDReader(String filePath) throws IOException {
        this.idToOffset = new HashMap<>();
        this.uniqueIds = new HashSet<>();
        this.filePath = filePath;
        this.randomAccessFile = new RandomAccessFile(getPathFromResources(filePath), "r");
        this.columnIndexes = getColumnIndexesFromLine(randomAccessFile.readLine());
        loadOffsetID();

    }

   private void loadOffsetID() throws IOException {
        try
        {
            String currentLine;
            long currentOffset = randomAccessFile.getFilePointer();
            while ((currentLine = randomAccessFile.readLine()) != null) {
                Integer id = Integer.parseInt(getValueFromKey(currentLine, columnIndexes, "ID"));
                if (checkUniqueID(currentOffset, id)) continue;
                idToOffset.put(id, currentOffset);
                currentOffset = randomAccessFile.getFilePointer();
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }


    private boolean checkUniqueID(long currentOffset, Integer id) {
        if (uniqueIds.contains(id)) {
            System.out.println("Duplicate ID found: " + id + " at offset: " + currentOffset + " in File:" + filePath);
            return true;
        }
        uniqueIds.add(id);
        return false;
    }

    public Long getLineById(Integer id) throws IllegalArgumentException {

        if (!uniqueIds.contains(id)) {
            throw new IllegalArgumentException("ID not found: " + id + "in file: " + filePath);
        }
        return idToOffset.get(id);
    }

    public Map<String, String> getMapLineById(Integer id) {
        try {
            Long idPosition = getLineById(id);
            randomAccessFile.seek(idPosition);
            String line = randomAccessFile.readLine();
            return getLineInMap(line, columnIndexes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
