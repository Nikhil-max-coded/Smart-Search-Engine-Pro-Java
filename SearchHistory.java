import java.util.ArrayList;

public class SearchHistory {

    private ArrayList<String> history;

    public SearchHistory() {
        history = new ArrayList<String>();
    }

    public void addSearch(String query) {

        if(query == null || query.trim().length() == 0)
            return;

        history.add(query);

        if(history.size() > Constants.MAX_HISTORY) {
            history.remove(0);
        }
    }

    public void showHistory() {

        Utils.heading("SEARCH HISTORY");

        if(history.isEmpty()) {
            System.out.println("No Search History.");
            return;
        }

        for(int i=0;i<history.size();i++) {
            System.out.println((i+1)+". "+history.get(i));
        }
    }

    public void clearHistory() {
        history.clear();
    }

    public ArrayList<String> getHistory() {
        return history;
    }
}