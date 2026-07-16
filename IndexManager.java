import java.io.*;
import java.util.*;

public class IndexManager {

    // word -> file -> information
    private HashMap<String, HashMap<String, FileInfo>> invertedIndex;

    private int totalFiles;
    private int totalWords;
    private int uniqueWords;

    public IndexManager() {
        invertedIndex = new HashMap<String, HashMap<String, FileInfo>>();
        totalFiles = 0;
        totalWords = 0;
        uniqueWords = 0;
    }

    public void buildIndex(String folderPath) {

        File folder = new File(folderPath);

        if (!folder.exists()) {
            System.out.println("Folder not found.");
            return;
        }

        if (!folder.isDirectory()) {
            System.out.println("Invalid folder.");
            return;
        }

        File[] files = folder.listFiles();

        if (files == null) {
            System.out.println("No files found.");
            return;
        }

        for (File file : files) {

            if (file.isFile()
                    && file.getName().toLowerCase().endsWith(Constants.FILE_EXTENSION)) {

                totalFiles++;

                indexSingleFile(file);
            }
        }

        uniqueWords = invertedIndex.size();

        System.out.println();
        System.out.println("Index Created Successfully.");
        System.out.println("Files Indexed : " + totalFiles);
        System.out.println("Unique Words  : " + uniqueWords);
        System.out.println("Total Words   : " + totalWords);
    }

    private void indexSingleFile(File file) {

        try {

            BufferedReader reader =
                    new BufferedReader(new FileReader(file));

            String line;

            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {

                lineNumber++;

                List<String> words = Utils.tokenize(line);

                for (String word : words) {

                    totalWords++;

                    addWord(word,
                            file.getName(),
                            lineNumber,
                            line);
                }
            }

            reader.close();

        } catch (Exception e) {

            System.out.println("Error Reading File : "
                    + file.getName());
        }
    }

    private void addWord(String word,
                         String fileName,
                         int lineNumber,
                         String line) {

        if (!invertedIndex.containsKey(word)) {

            invertedIndex.put(
                    word,
                    new HashMap<String, FileInfo>());
        }

        HashMap<String, FileInfo> fileMap =
                invertedIndex.get(word);

        FileInfo info;

        if (fileMap.containsKey(fileName)) {

            info = fileMap.get(fileName);

        } else {

            info = new FileInfo(fileName);

            fileMap.put(fileName, info);
        }

        info.addOccurrence(lineNumber, line);
    }
        public HashMap<String, HashMap<String, FileInfo>> getInvertedIndex() {
        return invertedIndex;
    }

    public boolean containsWord(String word) {
        return invertedIndex.containsKey(word);
    }

    public HashMap<String, FileInfo> getFileMap(String word) {

        if (containsWord(word)) {
            return invertedIndex.get(word);
        }

        return null;
    }

    public int getTotalFiles() {
        return totalFiles;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public int getUniqueWords() {
        return uniqueWords;
    }

    public void showStatistics() {

        Utils.heading("INDEX STATISTICS");

        System.out.println("Total Files  : " + totalFiles);
        System.out.println("Total Words  : " + totalWords);
        System.out.println("Unique Words : " + uniqueWords);

        System.out.println();
    }

    public void showTopWords() {

        HashMap<String, Integer> frequencyMap =
                new HashMap<String, Integer>();

        for (String word : invertedIndex.keySet()) {

            int totalFrequency = 0;

            HashMap<String, FileInfo> fileMap =
                    invertedIndex.get(word);

            for (FileInfo info : fileMap.values()) {

                totalFrequency += info.getFrequency();
            }

            frequencyMap.put(word, totalFrequency);
        }

        List<Map.Entry<String, Integer>> list =
                new ArrayList<Map.Entry<String, Integer>>
                        (frequencyMap.entrySet());

        Collections.sort(list,
                new Comparator<Map.Entry<String, Integer>>() {

                    public int compare(
                            Map.Entry<String, Integer> a,
                            Map.Entry<String, Integer> b) {

                        return b.getValue() - a.getValue();
                    }

                });

        Utils.heading("TOP WORDS");

        int count = 0;

        for (Map.Entry<String, Integer> entry : list) {

            System.out.printf("%-20s %d\n",
                    entry.getKey(),
                    entry.getValue());

            count++;

            if (count == Constants.TOP_WORDS)
                break;
        }

        System.out.println();
    }

    public Set<String> getAllWords() {
        return invertedIndex.keySet();
    }

    public void clearIndex() {

        invertedIndex.clear();

        totalFiles = 0;
        totalWords = 0;
        uniqueWords = 0;
    }
}