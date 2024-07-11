package me.alipson.workspace;

// Alexandre Lipson
// 06/26/2024
// CSE 123 
// P1: Workspace
// TA: Daniel

import java.util.*;

// entrypoint class, provides the createIndex method to create an inverted index
// where the keys are words within media objects 
// and the values are sets of media object themselves
public class InvertedIndex {
    public static void main(String[] args) {
        List<Media> docs = List.of(
                new Book("Mistborn", "Brandon Sanderson",
                        new Scanner("Epic fantasy worldbuildling content")),
                new Book("Farenheit 451", "Ray Bradbury",
                        new Scanner("Realistic \"sci-fi\" content")),
                new Book("The Hobbit", "J.R.R. Tolkein",
                        new Scanner("Epic fantasy quest content")));

        Map<String, Set<Media>> result = createIndex(docs);
        System.out.println(docs);
        System.out.println();
        System.out.println(result);
    }

    // returns a map with alphebetically sorted lowercase keys from words within
    // the media objects from the media list whose values are a set of the media
    // objects which contain the key
    public static Map<String, Set<Media>> createIndex(List<Media> media) {
        Map<String, Set<Media>> index = new TreeMap<>();

        for (Media m : media) { // iterate over media list
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
}
