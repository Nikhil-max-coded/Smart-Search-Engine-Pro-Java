import java.util.*;

public class SearchEngine {

    private IndexManager indexManager;
    private SearchHistory history;

    public SearchEngine(IndexManager indexManager,
                        SearchHistory history) {

        this.indexManager = indexManager;
        this.history = history;
    }

    // ===============================
    // SINGLE WORD SEARCH
    // ===============================

    public ArrayList<SearchResult> search(String keyword) {

        long start = System.currentTimeMillis();

        keyword = Utils.cleanWord(keyword);

        history.addSearch(keyword);

        ArrayList<SearchResult> results =
                new ArrayList<SearchResult>();

        if (!indexManager.containsWord(keyword)) {

            System.out.println();
            System.out.println("No Results Found.");
            return results;
        }

        HashMap<String, FileInfo> files =
                indexManager.getFileMap(keyword);

        for (Map.Entry<String, FileInfo> entry : files.entrySet()) {

            FileInfo info = entry.getValue();

            SearchResult result =
                    new SearchResult(info.getFileName());

            result.addWord(keyword,
                    info.getFrequency());

            results.add(result);
        }

        Collections.sort(results);

        printResults(results, keyword);

        long end = System.currentTimeMillis();

        System.out.println();
        System.out.println("Search Time : "
                + (end - start)
                + " ms");

        return results;
    }

    // ===============================
    // MULTI KEYWORD (OR SEARCH)
    // ===============================

    public ArrayList<SearchResult> searchOR(String query) {

        long start = System.currentTimeMillis();

        history.addSearch(query);

        String[] words =
                query.toLowerCase().split("\\s+");

        HashMap<String, SearchResult> map =
                new HashMap<String, SearchResult>();

        for (String word : words) {

            word = Utils.cleanWord(word);

            if (!indexManager.containsWord(word))
                continue;

            HashMap<String, FileInfo> files =
                    indexManager.getFileMap(word);

            for (Map.Entry<String, FileInfo> entry
                    : files.entrySet()) {

                FileInfo info = entry.getValue();

                SearchResult result;

                if (map.containsKey(info.getFileName())) {

                    result = map.get(info.getFileName());

                } else {

                    result =
                            new SearchResult(info.getFileName());

                    map.put(info.getFileName(),
                            result);
                }

                result.addWord(word,
                        info.getFrequency());
            }
        }

        ArrayList<SearchResult> results =
                new ArrayList<SearchResult>(
                        map.values());

        Collections.sort(results);

        printResults(results, query);

        long end = System.currentTimeMillis();

        System.out.println();
        System.out.println("Search Time : "
                + (end - start)
                + " ms");

        return results;
    }

    // ===============================
    // PRINT RESULTS
    // ===============================

    private void printResults(
            ArrayList<SearchResult> results,
            String query) {

        Utils.heading("SEARCH RESULTS");

        if (results.isEmpty()) {

            System.out.println(
                    "No Results Found.");

            return;
        }

        int rank = 1;

        for (SearchResult result : results) {

            System.out.println("--------------------------------");

            System.out.println(
                    "Rank : " + rank++);

            System.out.println(
                    "File : "
                            + result.getFileName());

            System.out.println(
                    "Score : "
                            + result.getScore());

            System.out.print(
                    "Matched Words : ");

            for (String word
                    : result.getMatchedWords()) {

                System.out.print(word + " ");
            }

            System.out.println();
        }

        System.out.println("--------------------------------");
    }

    // ==========================================
    // AND SEARCH
    // ==========================================

    public ArrayList<SearchResult> searchAND(String query) {

        long start = System.currentTimeMillis();

        history.addSearch(query);

        String[] words = query.toLowerCase().split("\\s+");

        ArrayList<SearchResult> results =
                new ArrayList<SearchResult>();

        if(words.length==0)
            return results;

        String firstWord = Utils.cleanWord(words[0]);

        if(!indexManager.containsWord(firstWord))
        {
            System.out.println("No Results Found.");
            return results;
        }

        HashMap<String, FileInfo> firstMap =
                indexManager.getFileMap(firstWord);

        for(String fileName : firstMap.keySet())
        {

            boolean found = true;

            int totalScore = 0;

            SearchResult result =
                    new SearchResult(fileName);

            for(String word : words)
            {

                word = Utils.cleanWord(word);

                if(!indexManager.containsWord(word))
                {
                    found = false;
                    break;
                }

                HashMap<String, FileInfo> map =
                        indexManager.getFileMap(word);

                if(!map.containsKey(fileName))
                {
                    found = false;
                    break;
                }

                FileInfo info = map.get(fileName);

                totalScore += info.getFrequency();

                result.addWord(word,
                        info.getFrequency());
            }

            if(found)
            {
                results.add(result);
            }
        }

        Collections.sort(results);

        printResults(results, query);

        long end = System.currentTimeMillis();

        System.out.println();

        System.out.println("Search Time : "
                +(end-start)+" ms");

        return results;
    }


    // ==========================================
    // WORD DETAILS
    // ==========================================

    public void showWordDetails(String word)
    {

        word = Utils.cleanWord(word);

        if(!indexManager.containsWord(word))
        {
            System.out.println("Word Not Found.");
            return;
        }

        Utils.heading("WORD DETAILS");

        HashMap<String,FileInfo> files =
                indexManager.getFileMap(word);

        int totalFrequency = 0;

        for(FileInfo info : files.values())
        {

            totalFrequency += info.getFrequency();

            System.out.println("--------------------------------");

            System.out.println("File : "
                    +info.getFileName());

            System.out.println("Frequency : "
                    +info.getFrequency());

            System.out.print("Lines : ");

            for(Integer line :
                    info.getLineNumbers())
            {
                System.out.print(line+" ");
            }

            System.out.println();
        }

        System.out.println("--------------------------------");

        System.out.println("Appears in Files : "
                +files.size());

        System.out.println("Total Frequency : "
                +totalFrequency);
    }


    // ==========================================
    // TOP MATCH
    // ==========================================

    public void bestMatch(String query)
    {

        ArrayList<SearchResult> list =
                searchOR(query);

        if(list.size()==0)
            return;

        Utils.heading("BEST MATCH");

        SearchResult best = list.get(0);

        System.out.println("File : "
                +best.getFileName());

        System.out.println("Score : "
                +best.getScore());

        System.out.print("Matched Words : ");

        for(String s :
                best.getMatchedWords())
        {
            System.out.print(s+" ");
        }

        System.out.println();
    }


    // ==========================================
    // FILE FREQUENCY
    // ==========================================

    public void fileFrequency(String fileName)
    {

        Utils.heading("FILE FREQUENCY");

        int total = 0;

        for(String word :
                indexManager.getAllWords())
        {

            HashMap<String,FileInfo> map =
                    indexManager.getFileMap(word);

            if(map.containsKey(fileName))
            {
                total += map.get(fileName)
                        .getFrequency();
            }
        }

        System.out.println("File : "+fileName);

        System.out.println("Indexed Words : "
                +total);
    }
        // ==========================================
    // PREFIX SEARCH
    // ==========================================

    public void prefixSearch(String prefix)
    {
        prefix = Utils.cleanWord(prefix);

        Utils.heading("PREFIX SEARCH : " + prefix);

        boolean found = false;

        for(String word : indexManager.getAllWords())
        {
            if(word.startsWith(prefix))
            {
                System.out.println(word);
                found = true;
            }
        }

        if(!found)
        {
            System.out.println("No Matching Words.");
        }
    }


    // ==========================================
    // LIST ALL INDEXED WORDS
    // ==========================================

    public void listAllWords()
    {
        Utils.heading("ALL INDEXED WORDS");

        ArrayList<String> words =
                new ArrayList<String>(indexManager.getAllWords());

        Collections.sort(words);

        for(String word : words)
        {
            System.out.println(word);
        }

        System.out.println();
        System.out.println("Total Words : " + words.size());
    }


    // ==========================================
    // SHOW FILES CONTAINING A WORD
    // ==========================================

    public void showFilesContainingWord(String word)
    {
        word = Utils.cleanWord(word);

        if(!indexManager.containsWord(word))
        {
            System.out.println("Word Not Found.");
            return;
        }

        Utils.heading("FILES CONTAINING : " + word);

        HashMap<String,FileInfo> map =
                indexManager.getFileMap(word);

        for(FileInfo info : map.values())
        {
            System.out.println(info.getFileName());
        }
    }


    // ==========================================
    // SEARCH STATISTICS
    // ==========================================

    public void searchStatistics()
    {
        Utils.heading("SEARCH ENGINE STATISTICS");

        System.out.println("Indexed Files : "
                + indexManager.getTotalFiles());

        System.out.println("Unique Words : "
                + indexManager.getUniqueWords());

        System.out.println("Total Words : "
                + indexManager.getTotalWords());

        System.out.println();

        indexManager.showTopWords();
    }


    // ==========================================
    // AUTO SUGGESTION
    // ==========================================

    public void suggestWord(String input)
    {
        input = Utils.cleanWord(input);

        String bestWord = "";
        int bestDistance = Integer.MAX_VALUE;

        for(String word : indexManager.getAllWords())
        {
            int distance =
                    levenshteinDistance(input, word);

            if(distance < bestDistance)
            {
                bestDistance = distance;
                bestWord = word;
            }
        }

        if(bestDistance <= 2)
        {
            System.out.println("Did you mean : "
                    + bestWord + " ?");
        }
        else
        {
            System.out.println("No Suggestions.");
        }
    }


    // ==========================================
    // LEVENSHTEIN DISTANCE
    // ==========================================

    private int levenshteinDistance(String a,String b)
    {
        int dp[][] =
                new int[a.length()+1][b.length()+1];

        for(int i=0;i<=a.length();i++)
            dp[i][0]=i;

        for(int j=0;j<=b.length();j++)
            dp[0][j]=j;

        for(int i=1;i<=a.length();i++)
        {
            for(int j=1;j<=b.length();j++)
            {
                if(a.charAt(i-1)==b.charAt(j-1))
                {
                    dp[i][j]=dp[i-1][j-1];
                }
                else
                {
                    dp[i][j]=1+
                            Math.min(dp[i-1][j-1],
                            Math.min(dp[i-1][j],
                                     dp[i][j-1]));
                }
            }
        }

        return dp[a.length()][b.length()];
    }
}