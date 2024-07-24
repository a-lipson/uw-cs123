package me.alipson.minigit;

// Alexandre Lipson
// 07/24/2024
// CSE 123 
// P2: Mini-Git
// TA: Daniel

import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Repository stores a list of Commits and has the following actions:
 * 
 * @see #drop(String) drops a commit from the history
 * @see #commit(String) adds a new commit head to the top of the history
 * @see #contains(String) checks if the repo contains a given commit
 * @see #synchronize(Repository) merges two repositories together, sorting by
 *      commit time stamp
 * @see #getHistory(int) gives the repo history up to a number of commits
 */
public class Repository {

    private String name;
    private Commit head;
    // private int size;

    /**
     * Creates a new empty repository with a name.
     * 
     * @param name the repository name
     * 
     * @throws IllegalArgumentException if name is null or empty.
     */
    public Repository(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Cannot initialize repo without name.");
        }
        this.name = name;
    }

    /**
     * Returns the ID of the current head of this repository.
     * 
     * @return the ID String or "null" if there is no commit history.
     */
    public String getRepoHead() {
        if (head == null) {
            return "null";
        }
        return head.id;
    }

    /**
     * Returns the number of commits in the repository.
     * 
     * @return the number of commits int.
     */
    public int getRepoSize() {
        Commit curr = head;
        int size = 0;

        while (curr != null) {
            curr = curr.past;
            size++;
        }

        return size;
    }

    /**
     * Returns a string representation of the repository as
     * <name> - Current head: <head>
     *
     * If there are no commits, seturns
     * <name> - No commits
     * 
     * @return the human-readable String representation.
     */
    @Override
    public String toString() {
        if (head == null) {
            return name + " - No commits";
        }
        return name + " - Current head: " + head.toString();
    }

    /**
     * Checks whether commit with ID the target ID is present in the repository.
     * 
     * @param targetId the target ID String to check for.
     * 
     * @return <code>true<\code> if such a commit d is present, and
     *         <code>false<\code> if not.
     */
    public boolean contains(String targetId) {
        Commit curr = head;

        while (curr != null) {
            if (targetId.equals(curr.id)) {
                return true;
            }
            curr = curr.past;
        }

        return false;
    }

    /**
     * Return a string consisting of the String representations of the most recent n
     * commits in this repository, with the most recent first. Commits should be
     * separated by a newline (\n) character.
     * 
     * If there are fewer than n commits in this repository, return them all.
     * 
     * If there are no commits in this repository, return the empty string.
     * 
     * If n is non-positive, throw an IllegalArgumentException.
     * 
     * @return the history of commits String.
     * 
     * @throws IllegalArgumentException if history count n is non-positive.
     */
    public String getHistory(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Cannot get less than one history entry");
        }

        if (head == null) {
            return "";
        }

        Commit curr = head;
        int fromLatest = 0;
        String out = "";

        while (curr != null && fromLatest < n) {
            out += curr.toString() + "\n";
        }
        return out;
    }

    /**
     * Creates a new commit with the given message and sets it as the head of thej
     * repository.
     * 
     * @param messsage the commit message
     * 
     * @return the ID String of the new commit.
     */
    public String commit(String message) {
        head = new Commit(message, head);
        return head.id;
    }

    /**
     * Removes the commit with ID targetId from the repository while maintaining the
     * rest of the history.
     * 
     * @param targetId the target ID String to check for.
     * 
     * @return <code>true<\code> if the commit was successfully dropped, and
     *         <code>false<\code if there is no commit that matches
     *         the given ID in the repository.
     */
    public boolean drop(String targetId) {
        if (head == null) { // empty: cannot drop commits from empty repo
            return false;
        }

        if (head.id.equals(targetId)) { // first: drop first commit
            head = head.past;
            return true;
        }

        Commit curr = head;
        Commit prev = null;

        // NOTE: could consolidate logic with #contains(String) for read iterating over
        // repos

        while (curr != null) {
            if (curr.id.equals(targetId)) {
                prev.past = curr.past; // unlink target
                return true;
            }
            prev = curr; // move to next
            curr = curr.past;
        }

        return false; // target id not present in history
    }

    /**
     * Takes all the commits in the other repository and moves them into this
     * repository, combining the two repository histories such that chronological
     * order is preserved. That is, after executing this method, this repository
     * should contain all commits that were from this and other, and the commits
     * should be ordered in timestamp order from most recent to least recent.
     * 
     * If the other repository is empty, this repository should remain unchanged.
     * 
     * If this repository is empty, all commits in the other repository should be
     * moved into this repository.
     * 
     * At the end of this method's execution, other should be an empty repository in
     * all cases.
     * 
     * You should not construct any new Commit objects to implement this method. You
     * may however create as many references as you like.
     * 
     * @param other the other Repository to synchronize into the caller.
     */
    public void synchronize(Repository other) {
        // empty: no commits in this head
        if (this.head == null) {
            this.head = other.head;
            other.head = null;
        }

        Commit thisCurr = this.head;
        Commit otherCurr = other.head;

        // front: other head is more recent
        if (otherCurr != null && otherCurr.timeStamp > thisCurr.timeStamp) {
            // swap this and other heads if other head commit is more recent
            this.head = otherCurr;
            otherCurr = otherCurr.past;
            this.head.past = thisCurr;
            // thisCurr = other.head;
            // otherCurr = this.head;
        }

        // middle
        while (thisCurr.past != null && otherCurr != null) {
            if (otherCurr.timeStamp > thisCurr.past.timeStamp) { // lastest other is more recent
                // insert other head commit before this past
                Commit temp = otherCurr;
                otherCurr = otherCurr.past;
                temp.past = thisCurr.past;
                thisCurr.past = temp;
                // Commit thisPast = thisCurr.past;
                // thisCurr.past = otherCurr;
                // otherCurr = thisPast;
            }
            thisCurr = thisCurr.past;
        }

        // end: append remaining commits from other
        if (thisCurr.past == null) {
            thisCurr.past = otherCurr;
        }

        other.head = null;
    }

    /**
     * DO NOT MODIFY
     * A class that represents a single commit in the repository.
     * Commits are characterized by an identifier, a commit message,
     * and the time that the commit was made. A commit also stores
     * a reference to the immediately previous commit if it exists.
     *
     * Staff Note: You may notice that the comments in this
     * class openly mention the fields of the class. This is fine
     * because the fields of the Commit class are public. In general,
     * be careful about revealing implementation details!
     */
    public static class Commit {

        private static int currentCommitID;

        /**
         * The time, in milliseconds, at which this commit was created.
         */
        public final long timeStamp;

        /**
         * A unique identifier for this commit.
         */
        public final String id;

        /**
         * A message describing the changes made in this commit.
         */
        public final String message;

        /**
         * A reference to the previous commit, if it exists. Otherwise, null.
         */
        public Commit past;

        /**
         * Constructs a commit object. The unique identifier and timestamp
         * are automatically generated.
         * 
         * @param message A message describing the changes made in this commit.
         * @param past    A reference to the commit made immediately before this
         *                commit.
         */
        public Commit(String message, Commit past) {
            this.id = "" + currentCommitID++;
            this.message = message;
            this.timeStamp = System.currentTimeMillis();
            this.past = past;
        }

        /**
         * Constructs a commit object with no previous commit. The unique
         * identifier and timestamp are automatically generated.
         * 
         * @param message A message describing the changes made in this commit.
         */
        public Commit(String message) {
            this(message, null);
        }

        /**
         * Returns a string representation of this commit. The string
         * representation consists of this commit's unique identifier,
         * timestamp, and message, in the following form:
         * "[identifier] at [timestamp]: [message]"
         * 
         * @return The string representation of this collection.
         */
        @Override
        public String toString() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(timeStamp);

            return id + " at " + formatter.format(date) + ": " + message;
        }

        /**
         * Resets the IDs of the commit nodes such that they reset to 0.
         * Primarily for testing purposes.
         */
        public static void resetIds() {
            Commit.currentCommitID = 0;
        }
    }
}
