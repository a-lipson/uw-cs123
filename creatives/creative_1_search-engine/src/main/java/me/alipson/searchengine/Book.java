package me.alipson.searchengine;

// Alexandre Lipson
// 06/26/2024
// CSE 123 
// P1: Workspace
// TA: Daniel

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// a subclass of media for books which stores the author(s), title, content, 
// and a list of numeric ratings for a book
public class Book implements Media, Comparable<Book> {

    private final String title;
    private final List<String> authors;

    private Scanner scanner;
    private List<String> content;

    private List<Integer> ratings;

    private String query;

    public Book(String title, String author) {
        this.title = title;
        this.authors = List.of(author);
        this.ratings = new ArrayList<>();
    }

    public Book(String title, List<String> authors) {
        this.title = title;
        this.authors = authors;
        this.ratings = new ArrayList<>();
    }

    public Book(String title, String author, Scanner sc) {
        this.title = title;
        this.authors = List.of(author);
        this.scanner = sc;
        this.content = new ArrayList<>();
        this.ratings = new ArrayList<>();
    }

    // public Book(String title, List<String> authors, Scanner sc)

    @Override
    // gives the book title string
    public String getTitle() {
        return title;
    }

    @Override
    // gives a list of the book authors
    public List<String> getArtists() {
        return authors;
    }

    @Override
    // takes a rating score int to add to the the ratings list
    public void addRating(int score) {
        ratings.add(Integer.valueOf(score));
    }

    @Override
    // gives the number of ratings in the ratings list
    public int getNumRatings() {
        if (ratings == null) {
            return 0;
        }
        return ratings.size();
    }

    @Override
    // gives an average of the ratings as a double
    public double getAverageRating() {
        if (getNumRatings() == 0) {
            return 0;
        }

        double averageRating = 0.;

        for (Integer r : ratings) {
            averageRating += r;
        }

        return averageRating / getNumRatings();
    }

    @Override
    // gives a list of all words within book, or an empty list if the Book Scanner
    // was not initialized
    public List<String> getWords() {
        if (scanner == null) { // scanner not initialized
            return List.of(); // immutable
        }

        if (content.size() == 0) { // scanner not consumed
            setContent();
        }

        return content;
    }

    // consumes words from scanner and adds to content
    private void setContent() {
        while (scanner.hasNext()) {
            content.add(scanner.next());
        }
    }

    @Override
    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    // compare book objects by cumulative appearance count of query tokens,
    // otherwise uses rating for equal counts, otherwise returns equal
    public int compareTo(Book other) {

        int thisCounts = 0;
        int otherCounts = 0;

        Scanner tokens = new Scanner(query);
        while (tokens.hasNext()) {
            String token = tokens.next().toLowerCase();

            thisCounts += countWordOccurrences(this.getWords(), token);
            otherCounts += countWordOccurrences(other.getWords(), token);
        }
        tokens.close();

        int relativeCounts = thisCounts - otherCounts;

        if (relativeCounts == 0) { // net token count matches are equal
            relativeCounts = (int) (this.getAverageRating() - other.getAverageRating());
        }

        return relativeCounts;
    }

    // counts occurences of a target word string in a list of words
    private int countWordOccurrences(List<String> words, String target) {
        int count = 0;

        for (String w : words) {
            if (w.equals(target)) {
                count++;
            }
        }

        return count;
    }

    @Override
    public String toString() {
        String display = title + " by [";

        for (int i = 0; i < authors.size(); i++) {
            // formatting
            if (0 < i) {
                display += ", "; // pad all authors after the first
            }
            display += authors.get(i);
        }

        display += "]";

        if (!(getNumRatings() == 0)) {
            display += ": " + String.format("%.2f", getAverageRating()) +
                    " (" + getNumRatings() + " ratings)";
        }

        return display;
    }
}
