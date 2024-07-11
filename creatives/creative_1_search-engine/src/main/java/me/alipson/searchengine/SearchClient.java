package me.alipson.searchengine;

// Alexandre Lipson
// 06/26/2024
// CSE 123 
// P1: Workspace
// TA: Daniel

import java.io.*;
import java.util.*;

// This class allows users to find and rate books within BOOK_DIRECTORY
// containing certain terms
public class SearchClient {
    public static final String BOOK_DIRECTORY = "./books";

    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        List<Book> books = loadBooks();
        List<Media> media = new ArrayList<>(books);

        Map<String, Set<Media>> index = createIndex(media);

        System.out.println("Welcome to the CSE 123 Search Engine!");
        String command = "";
        while (!command.equalsIgnoreCase("quit")) {
            System.out.println("What would you like to do? [Search, Rate, Quit]");
            System.out.print("> ");
            command = console.nextLine();

            if (command.equalsIgnoreCase("search")) {
                searchQuery(console, media, index);
            } else if (command.equalsIgnoreCase("rate")) {
                addRating(console, media);
            } else if (!command.equalsIgnoreCase("quit")) {
                System.out.println("Invalid command, please try again.");
            }
        }
        System.out.println("See you next time!");
    }

    // returns a map with alphebetically sorted lowercase keys from words within
    // the media objects from the media list whose values are a set of the media
    // objects which contain the key
    public static Map<String, Set<Media>> createIndex(List<Media> documents) {
        Map<String, Set<Media>> index = new TreeMap<>();

        for (Media m : documents) { // iterate over media list
            for (String w : m.getWords()) { // iterate over words in given media object
                w = w.toLowerCase();
                if (index.containsKey(w)) { // word is already present in index
                    index.get(w).add(m); // add media to value at give word key
                } else {
                    // new index entry of a singleton media set value at word key
                    index.put(w, new HashSet<>(Set.of(m)));
                }
            }
        }

        return index;
    }

    // Allows the user to search a specific query using the provided 'index' to find
    // appropraite
    // Media entries.
    //
    // Parameters:
    // console - the Scanner to get user input from
    // index - invertedIndex mapping terms to the Set of media containing those
    // terms
    public static void searchQuery(Scanner console, List<Media> documents, Map<String, Set<Media>> index) {
        System.out.println("Enter query:");
        System.out.print("> ");
        String query = console.nextLine();

        for (Media doc : documents) {
            doc.setQuery(query);
        }
        Set<Media> result = search(index, query);

        if (result.isEmpty()) {
            System.out.println("\tNo results!");
        } else {
            for (Media m : result) {
                System.out.println("\t" + m.toString());
            }
        }
    }

    // Allows the user to add a rating to one of the options wthin 'media'
    //
    // Parameters:
    // console - the Scanner to get user input from
    // media - list of all media options loaded into the search engine
    public static void addRating(Scanner console, List<Media> media) {
        for (int i = 0; i < media.size(); i++) {
            System.out.println("\t" + i + ": " + media.get(i).toString());
        }
        System.out.println("What would you like to rate (enter index)?");
        System.out.print("> ");
        int choice = Integer.parseInt(console.nextLine());
        if (choice < 0 || choice >= media.size()) {
            System.out.println("Invalid choice");
        } else {
            System.out.println("Rating [" + media.get(choice).getTitle() + "]");
            System.out.println("What rating would you give?");
            System.out.print("> ");
            int rating = Integer.parseInt(console.nextLine());
            media.get(choice).addRating(rating);
        }
    }

    // Searches a specific query using the provided 'index' to find appropraite
    // Media entries.
    // terms are determined by whitespace separation. If a term is proceeded by '-'
    // any entry
    // containing that term will be removed from the result.
    //
    // Parameters:
    // index - invertedIndex mapping terms to the Set of media containing those
    // terms
    // query - user's entered query string to use in searching
    //
    // Returns:
    // An optional set of all Media containing the requirested terms. If none,
    // Optional.Empty()
    public static Set<Media> search(Map<String, Set<Media>> index, String query) {
        Set<Media> ret = new TreeSet<>();

        Scanner tokens = new Scanner(query);
        while (tokens.hasNext()) {
            String token = tokens.next().toLowerCase();

            if (index.containsKey(token)) {
                ret.addAll(index.get(token));
            }
        }
        tokens.close();
        return ret;
    }

    // Loads all books from BOOK_DIRECTORY. Assumes that each book starts with two
    // lines -
    // "Title: " which is followed by the book's title
    // "Author: " which is followed by the book's author
    //
    // Returns:
    // A list of all book objects corresponding to the ones located in
    // BOOK_DIRECTORY
    public static List<Book> loadBooks() throws FileNotFoundException {
        List<Book> ret = new ArrayList<>();

        File dir = new File(BOOK_DIRECTORY);
        for (File f : dir.listFiles()) {
            Scanner sc = new Scanner(f);
            String title = sc.nextLine().substring("Title: ".length());
            String author = sc.nextLine().substring("Author: ".length());

            ret.add(new Book(title, author, sc));
        }

        return ret;
    }
}
