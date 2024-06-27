package me.alipson.workspace;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class Testing {

    @Test
    @DisplayName("Book 2 string constructor")
    public void testBook2String() {
        Book book = new Book("Title", "Author");

        assertEquals("Title", book.getTitle());

        assertEquals(List.of("Author"), book.getArtists());

        assertEquals("Title by [Author]", book.toString());
    }

    @Test
    @DisplayName("Book string, list constructor")
    public void testBookStringList() {
        Book book = new Book("Title", List.of("Author 1", "Author 2"));

        assertEquals("Title", book.getTitle());

        assertEquals(List.of("Author 1", "Author 2"), book.getArtists());

        assertEquals("Title by [Author 1, Author 2]", book.toString());
    }

    @Test
    @DisplayName("createIndex tests")
    public void testInvertedIndex() {
        Book mistborn = new Book("Mistborn", "Brandon Sanderson",
                new Scanner("Epic fantasy worldbuildling content"));
        Book farenheit = new Book("Farenheit 451", "Ray Bradbury",
                new Scanner("Realistic \"sci-fi\" content"));
        Book hobbit = new Book("The Hobbit", "J.R.R. Tolkein",
                new Scanner("Epic fantasy quest content"));

        List<Media> books = List.of(mistborn, farenheit, hobbit);
        Map<String, Set<Media>> index = InvertedIndex.createIndex(books);

        assertTrue(index.containsKey("\"sci-fi\""));

        Set<Book> expected = Set.of(mistborn, hobbit);

        assertEquals(expected, index.get("fantasy"));
    }
}
