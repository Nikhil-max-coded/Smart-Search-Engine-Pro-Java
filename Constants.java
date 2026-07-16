import java.util.*;

public class Constants {

    public static final String FILE_EXTENSION = ".txt";

    public static final int MAX_HISTORY = 20;

    public static final int TOP_WORDS = 10;

    public static final Set<String> STOP_WORDS =
            new HashSet<String>(Arrays.asList(

            "a","an","the","is","are","was","were",
            "of","to","in","on","at","for","with",
            "by","and","or","not","be","been",
            "being","this","that","these","those",
            "it","its","as","from","into","over",
            "under","between","after","before",
            "will","shall","would","should",
            "can","could","has","have","had",
            "do","does","did"
    ));

}