import java.util.*;

public class SearchResult implements Comparable<SearchResult>{

    private String fileName;

    private int score;

    private ArrayList<String> matchedWords;

    public SearchResult(String fileName)
    {
        this.fileName=fileName;

        score=0;

        matchedWords=new ArrayList<String>();
    }

    public void addWord(String word,int frequency)
    {
        matchedWords.add(word);

        score+=frequency;
    }

    public String getFileName()
    {
        return fileName;
    }

    public int getScore()
    {
        return score;
    }

    public ArrayList<String> getMatchedWords()
    {
        return matchedWords;
    }

    public int compareTo(SearchResult other)
    {
        return other.score-this.score;
    }

}