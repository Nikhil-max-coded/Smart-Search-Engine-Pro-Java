import java.util.*;

public class Utils {

    public static String cleanWord(String word)
    {
        word = word.toLowerCase();
        word = word.replaceAll("[^a-z0-9]", "");
        return word.trim();
    }

    public static List<String> tokenize(String line)
    {
        List<String> words = new ArrayList<String>();

        String arr[] = line.split("\\s+");

        for(String word : arr)
        {
            word = cleanWord(word);

            if(isUseful(word))
                words.add(word);
        }

        return words;
    }

    public static boolean isUseful(String word)
    {
        if(word==null)
            return false;

        if(word.length()==0)
            return false;

        if(Constants.STOP_WORDS.contains(word))
            return false;

        return true;
    }

    public static void heading(String text)
    {
        System.out.println();
        System.out.println("=================================================");
        System.out.println(text);
        System.out.println("=================================================");
    }

}