package me.alipson.minigit;

// Alexandre Lipson
// 07/24/2024
// CSE 123 
// P2: Mini-Git
// TA: Daniel

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class Testing {
    private Repository repo1;
    private Repository repo2;

    private String commit = "test";
    private String[] commits1 = { "this", "is", "a", "test" };
    private String[] commits2 = { "THIS", "IS", "A", "TEST" };

    // Occurs before each of the individual test cases
    // (creates new repos and resets commit ids)
    @BeforeEach
    public void setUp() {
        repo1 = new Repository("repo1");
        repo2 = new Repository("repo2");
        Repository.Commit.resetIds();
    }

    /*
     * TODO: Write your tests here! Remember that you must either use the helper
     * methods below, or make sure to Thread.sleep between individual commits.
     * Write 3 of your own test cases for synchronize covering the different
     * LinkedList cases we've talked about in class: Front, Middle, and Last. Each
     * of these test cases should be contained within their own method in your
     * Testing class.
     */

    /*
     * Repository#synchronize Tests
     */

    @Test
    public void testEmptySynchronize() throws InterruptedException {
        // both empty
        repo1.synchronize(repo2);
        assertEquals(0, repo1.getRepoSize());
        assertEquals(0, repo2.getRepoSize());

        // repo1 empty
        commitAll(repo2, commits1);

        repo1.synchronize(repo2);

        testHistory(repo1, commits1.length, commits1);

        assertEquals(0, repo2.getRepoSize());
    }

    @Test
    public void testFrontSynchronize() throws InterruptedException {
        // repo2 head more recent
        commitAll(repo1, commits1);
        Thread.sleep(1);
        commitAll(repo2, commits2);

        repo1.synchronize(repo2);

        assertEquals(8, repo1.getRepoSize());
        assertEquals(0, repo2.getRepoSize());

        testHistory(repo1, commits2.length, commits2);
    }

    @Test
    public void testMiddleSynchronize() throws InterruptedException {
        repo1.commit(commits1[3]);
        Thread.sleep(1);
        repo2.commit(commits1[2]);
        Thread.sleep(1);
        repo1.commit(commits1[1]);
        Thread.sleep(1);
        repo2.commit(commits1[0]);

        repo1.synchronize(repo2);

        testHistory(repo1, commits1.length, commits1);
        assertEquals(repo1.getRepoSize(), 4);
        assertEquals(repo2.getRepoSize(), 0);
    }

    @Test
    public void testLastSynchronize() throws InterruptedException {
        // repo2 empty
        commitAll(repo1, commits1);

        repo1.synchronize(repo2);

        testHistory(repo1, commits1.length, commits1);
    }

    /*
     * Other Repository Tests
     */

    @Test
    public void testGetHistory() throws InterruptedException {
        commitAll(repo1, commits1);

        testHistory(repo1, 4, commits1);

        testHistory(repo1, 3, Arrays.copyOfRange(commits1, 0, 3));
    }

    @Test
    public void testCommit() {
        repo1.commit(commit);

        // testHistory(repo1, 1, commit);
    }

    @Test
    public void testContains() {
        repo1.commit(commit);
        // repo1.commit(commit);
        assertTrue(repo1.contains("0"));
    }

    @Test
    public void testDrop() {
        repo1.commit(commit);

        repo1.drop("0");

        assertFalse(repo1.contains("0"));
    }

    /////////////////////////////////////////////////////////////////////////////////
    // PROVIDED HELPER METHODS (You don't have to use these if you don't want to!)
    ///////////////////////////////////////////////////////////////////////////////// //
    /////////////////////////////////////////////////////////////////////////////////

    // Commits all of the provided messages into the provided repo, making sure
    // timestamps
    // are correctly sequential (no ties). If used, make sure to include
    // 'throws InterruptedException'
    // much like we do with 'throws FileNotFoundException'. Example useage:
    //
    // repo1:
    // head -> null
    // To commit the messages "one", "two", "three", "four"
    // commitAll(repo1, new String[]{"one", "two", "three", "four"})
    // This results in the following after picture
    // repo1:
    // head -> "four" -> "three" -> "two" -> "one" -> null
    //
    // YOU DO NOT NEED TO UNDERSTAND HOW THIS METHOD WORKS TO USE IT! (this is why
    // documentation
    // is important!)
    public void commitAll(Repository repo, String[] messages) throws InterruptedException {
        // Commit all of the provided messages
        for (String message : messages) {
            int size = repo.getRepoSize();
            repo.commit(message);

            // Make sure exactly one commit was added to the repo
            assertEquals(size + 1, repo.getRepoSize(),
                    String.format("Size not correctly updated after commiting message [%s]",
                            message));

            // Sleep to guarantee that all commits have different time stamps
            Thread.sleep(2);
        }
    }

    // Makes sure the given repositories history is correct up to 'n' commits,
    // checking against
    // all commits made in order. Example useage:
    //
    // repo1:
    // head -> "four" -> "three" -> "two" -> "one" -> null
    // (Commits made in the order ["one", "two", "three", "four"])
    // To test the getHistory() method up to n=3 commits this can be done with:
    // testHistory(repo1, 3, new String[]{"one", "two", "three", "four"})
    // Similarly, to test getHistory() up to n=4 commits you'd use:
    // testHistory(repo1, 4, new String[]{"one", "two", "three", "four"})
    //
    // YOU DO NOT NEED TO UNDERSTAND HOW THIS METHOD WORKS TO USE IT! (this is why
    // documentation
    // is important!)
    public void testHistory(Repository repo, int n, String[] allCommits) {
        int totalCommits = repo.getRepoSize();
        assertTrue(n <= totalCommits,
                String.format("Provided n [%d] too big. Only [%d] commits",
                        n, totalCommits));

        String[] nCommits = repo.getHistory(n).split("\n");

        assertTrue(nCommits.length <= n,
                String.format("getHistory(n) returned more than n [%d] commits", n));
        assertTrue(nCommits.length <= allCommits.length,
                String.format("Not enough expected commits to check against. " +
                        "Expected at least [%d]. Actual [%d]",
                        n, allCommits.length));

        for (int i = 0; i < n; i++) {
            String commit = nCommits[i];

            // Old commit messages/ids are on the left and the more recent commit
            // messages/ids are
            // on the right so need to traverse from right to left
            int backwardsIndex = totalCommits - 1 - i;
            String commitMessage = allCommits[backwardsIndex];

            assertTrue(commit.contains(commitMessage),
                    String.format("Commit [%s] doesn't contain expected message [%s]",
                            commit, commitMessage));
            assertTrue(commit.contains("" + backwardsIndex),
                    String.format("Commit [%s] doesn't contain expected id [%d]",
                            commit, backwardsIndex));
        }
    }
}
