package me.alipson.quiz;

// Alexandre Lipson
// 08/07/24
// CSE 123
// P3: JoeFeed Quiz
// TA: Daniel

import java.io.*;
import java.util.*;

import com.sun.source.tree.Tree;

/**
*/
public class QuizTree {
    private QuizTreeNode root;

    /**
     * Constructs a new quiz from the provided Scanner input.
     * 
     * @param inputFile the file input Scanner from which to create a new QuizTree
     *                  quiz
     */
    public QuizTree(Scanner inputFile) {
        // QuizTreeNode curr, prev;
        // while (inputFile.hasNextLine()) {
        // String line = inputFile.nextLine();
        // curr = parseQuizNodeOutput(line);
        // // need prev null check, would occur on the first iter only
        // if (prev)
        // prev = curr;
        // }
        constructorHelper(null, inputFile);

        inputFile.close();
    }

    /**
     * interpret input file as preorder traversal
     */
    private void constructorHelper(QuizTreeNode curr, Scanner inputFile) {

        // TODO: finish this method! it's not quite done yet;
        // can't traverse back upward and to the right!

        if (inputFile.hasNextLine()) { // base case
            String line = inputFile.nextLine();
            QuizTreeNode next = parseQuizNodeOutput(line);

            // on first pass through, we have no comparable parent node
            if (curr == null)
                constructorHelper(next, inputFile);

            // assume that our input will not have more than
            // two nodes per parent.
            if (curr.left == null) {
                curr.left = next;
            } else if (curr.right == null) {
                curr.right = next;
            } else {
                // should i put this here?
                constructorHelper(curr, inputFile);
            }

            // set next as current root if not a result leaf node
            if (!isResultNode(next))
                curr = next;

            constructorHelper(curr, inputFile);
        }
    }

    /**
     * Takes the current QuizTree quiz by prompting choices until a result is
     * reached.
     * 
     * @param console the user input Scanner object.
     */
    public void takeQuiz(Scanner console) {
        takeQuizHelper(root, console);
    }

    /**
     * Helper method
     *
     * @param node
     * @param console
     */
    private void takeQuizHelper(QuizTreeNode node, Scanner console) {
        if (isResultNode(node)) { // base case
            System.out.println("Your result is: " + node.result);
        }

        System.out.printf("Do you prefer %s or %s?\n", node.leftChoice, node.rightChoice);
        String input = console.nextLine();

        // NOTE: here is a great place for a bifunctor!
        if (input.equals(node.leftChoice)) {
            takeQuizHelper(node.left, console);
        } else if (input.equals(node.rightChoice)) {
            takeQuizHelper(node.right, console);
        }

        System.out.println("\tInvalid response; try again.");
        takeQuizHelper(node, console);

    }

    private static boolean isResultNode(QuizTreeNode node) {
        return node.left == null && node.right == null;
    }

    /**
     * Record the current QuizTree quiz the PrintStream output file.
     * 
     * @param outputFile the PrintStream file buffer on which to output the quiz.
     */
    public void export(PrintStream outputFile) {
        map(new TreeOperation() {
            @Override
            public void apply(QuizTreeNode node) {
                outputFile.println(formatQuizNodeOutput(node));
            }
        });
        outputFile.close();
    }

    /**
     * Returns the document
     * 
     * @param node the QuizTreeNode to format.
     * 
     * @see #parseQuizNodeOutput(String) the parser for this format
     */
    private static String formatQuizNodeOutput(QuizTreeNode node) {
        return isResultNode(node) ? "END:" + node.result : node.left.result + "/" + node.right.result;
    }

    /**
     * Provides the QuizTreeNode object parsed from a String line.
     * No input validation is performed.
     * 
     * @param line the String line input to parse
     * 
     * @return the parsed QuizTreeNode node with data but no links.
     * 
     * @see #formatQuizNodeOutput(QuizTreeNode) the formatter for this format
     */
    private static QuizTreeNode parseQuizNodeOutput(String line) {
        if (line.startsWith("END:"))
            return new QuizTreeNode(line.substring(4));

        String parts[] = line.split("/");
        return new QuizTreeNode(null, parts[0], parts[1], null, null);
    }

    /**
     * Replaces a result leaf QuizTreeNode with a new question and linked results.
     */
    public void addQuestion(String toReplace, String leftChoice, String rightChoice,
            String leftResult, String rightResult) {
        // HACK: bypassing forbidden feature of Java 8 anonymous functions.
        map(new TreeOperation() {
            @Override
            public void apply(QuizTreeNode node) {
                if (isResultNode(node) && node.result.equals(toReplace))
                    node = new QuizTreeNode(null, leftChoice, rightChoice,
                            new QuizTreeNode(leftResult), new QuizTreeNode(rightResult));
            }
        });

    }

    // private boolean contains(String data) {
    // return contains(root, data);
    // }
    //
    // private boolean contains(QuizTreeNode node, String data) {
    // if (node == null) {
    // return false;
    // }
    // return node.result.equals(data) || contains(node.right, data) ||
    // contains(node.left, data);
    // }

    /**
     * The QuizTreeNode type represents a single node in the QuizTree;
     * the data class provides no methods beyond instance constructors.
     */
    private static class QuizTreeNode {
        // NOTE: may not construct any methods which are not used by #quiztree!
        // structurally, i think it would be reasonable to have an #isleaf() method
        // for this class, and also a #tostring() override contingent upon the node
        // type.
        public QuizTreeNode left, right;

        public final String result;
        public final String leftChoice, rightChoice;

        /**
         * Constructs a general QuizTreenOde question leaf or branch
         * with data and links.
         */
        public QuizTreeNode(String result, String leftChoice, String rightChoice,
                QuizTreeNode left, QuizTreeNode right) {
            this.result = result;
            this.leftChoice = leftChoice;
            this.rightChoice = rightChoice;
            this.left = left;
            this.right = right;
        }

        /**
         * Constructs a quiz question node
         * 
         * @param result the final QuizTreeNode text.
         */
        public QuizTreeNode(String result) {
            this(result, null, null, null, null);
        }

    }

    /**
     * Provides a higher order function application for the TreeOperation type
     * through a preorder traversal.
     * 
     * @param operation a valid TreeOperation function to apply to a QuizTree
     * 
     * @see #map(QuizTreeNode, TreeOperation)
     */
    private void map(TreeOperation operation) {
        map(root, operation);
    }

    /**
     * Applies a TreeOperation type function through a preorder traversal of the
     * tree nodes belonging to the given QuizTreeNode node.
     * 
     * @param node      the root QuizTreeNode node over which to traverse.
     * @param operation a valid TreeOperation function to apply to the QuizTree
     */
    private void map(QuizTreeNode node, TreeOperation operation) {
        if (node != null) {
            operation.apply(node);
            map(node.left, operation);
            map(node.right, operation);
        }
    }

    /**
     * TreeOperation is a type for effectual functions that can be applied to
     * QuizTreeNodes.
     */
    private interface TreeOperation {
        void apply(QuizTreeNode node);
    }

}
