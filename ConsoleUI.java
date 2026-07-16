import java.util.*;

public class ConsoleUI {

    private Scanner sc;

    private IndexManager indexManager;

    private SearchHistory history;

    private SearchEngine searchEngine;

    public ConsoleUI() {

        sc = new Scanner(System.in);

        indexManager = new IndexManager();

        history = new SearchHistory();

        searchEngine =
                new SearchEngine(indexManager, history);
    }

    public void start() {

        Utils.heading("SMART SEARCH ENGINE PRO");

        String folder = "files";
        if (!new java.io.File(folder).exists()) {
            folder = "search engine/files";
        }

        System.out.println("Automatically loading files from: " + folder);

        indexManager.buildIndex(folder);

        int choice;

        do {

            menu();

            System.out.print("\nEnter Choice : ");

            choice = Integer.parseInt(sc.nextLine());

            switch(choice) {

                case 1:

                    singleSearch();

                    break;

                case 2:

                    orSearch();

                    break;

                case 3:

                    andSearch();

                    break;

                case 4:

                    wordDetails();

                    break;

                case 5:

                    prefixSearch();

                    break;

                case 6:

                    bestMatch();

                    break;

                case 7:

                    fileFrequency();

                    break;

                case 8:

                    history.showHistory();

                    break;

                case 9:

                    searchEngine.listAllWords();

                    break;

                case 10:

                    searchEngine.searchStatistics();

                    break;

                case 11:

                    indexManager.showStatistics();

                    break;

                case 12:

                    indexManager.showTopWords();

                    break;

                case 13:

                    suggestion();

                    break;

                case 0:

                    System.out.println("\nThank You!");

                    break;

                default:

                    System.out.println("Invalid Choice.");

            }

        } while(choice!=0);

    }

    private void menu() {

        Utils.heading("MAIN MENU");

        System.out.println("1. Single Word Search");

        System.out.println("2. Multi Keyword Search");

        System.out.println("3. AND Search");

        System.out.println("4. Word Details");

        System.out.println("5. Prefix Search");

        System.out.println("6. Best Match");

        System.out.println("7. File Frequency");

        System.out.println("8. Search History");

        System.out.println("9. List All Words");

        System.out.println("10. Search Statistics");

        System.out.println("11. Index Statistics");

        System.out.println("12. Top Frequent Words");

        System.out.println("13. Auto Suggest Word");

        System.out.println("0. Exit");

    }

    private void singleSearch() {

        System.out.print("Enter Word : ");

        String word = sc.nextLine();

        searchEngine.search(word);
    }

    private void orSearch() {

        System.out.print("Enter Keywords : ");

        String word = sc.nextLine();

        searchEngine.searchOR(word);
    }

    private void andSearch() {

        System.out.print("Enter Keywords : ");

        String word = sc.nextLine();

        searchEngine.searchAND(word);
    }

    private void wordDetails() {

        System.out.print("Enter Word : ");

        String word = sc.nextLine();

        searchEngine.showWordDetails(word);
    }

    private void prefixSearch() {

        System.out.print("Enter Prefix : ");

        String prefix = sc.nextLine();

        searchEngine.prefixSearch(prefix);
    }

    private void bestMatch() {

        System.out.print("Enter Keywords : ");

        String query = sc.nextLine();

        searchEngine.bestMatch(query);
    }

    private void fileFrequency() {

        System.out.print("Enter File Name : ");

        String file = sc.nextLine();

        searchEngine.fileFrequency(file);
    }

    private void suggestion() {

        System.out.print("Enter Incorrect Word : ");

        String word = sc.nextLine();

        searchEngine.suggestWord(word);
    }

    private void pause() {

        System.out.println();
        System.out.println("Press ENTER to continue...");

        sc.nextLine();
    }

    public void welcome() {

        Utils.heading("WELCOME");

        System.out.println("Smart Search Engine Pro");
        System.out.println("------------------------");
        System.out.println("Features Included:");
        System.out.println();
        System.out.println("- Single Word Search");
        System.out.println("- Multi Keyword Search");
        System.out.println("- AND Search");
        System.out.println("- Word Frequency");
        System.out.println("- Prefix Search");
        System.out.println("- Auto Suggestion");
        System.out.println("- Ranking");
        System.out.println("- Search History");
        System.out.println("- File Statistics");
        System.out.println("- Top Frequent Words");
        System.out.println();
    }

    public void exit() {

        Utils.heading("EXIT");

        System.out.println("Thank You For Using");
        System.out.println("Smart Search Engine Pro");
    }

}