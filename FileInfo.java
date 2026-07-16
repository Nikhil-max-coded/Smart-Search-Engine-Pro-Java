import java.util.*;

public class FileInfo {

    private String fileName;

    private int frequency;

    private ArrayList<Integer> lineNumbers;

    private ArrayList<String> lines;

    public FileInfo(String fileName)
    {
        this.fileName=fileName;

        frequency=0;

        lineNumbers=new ArrayList<Integer>();

        lines=new ArrayList<String>();
    }

    public void addOccurrence(int lineNo,String line)
    {
        frequency++;

        if(!lineNumbers.contains(lineNo))
            lineNumbers.add(lineNo);

        if(!lines.contains(line))
            lines.add(line);
    }

    public String getFileName()
    {
        return fileName;
    }

    public int getFrequency()
    {
        return frequency;
    }

    public ArrayList<Integer> getLineNumbers()
    {
        return lineNumbers;
    }

    public ArrayList<String> getLines()
    {
        return lines;
    }

}