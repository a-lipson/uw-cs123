package me.alipson.quiz;

// Alexandre Lipson
// 08/07/24
// CSE 123
// P3: JoeFeed Quiz
// TA: Daniel

import java.io.*;
import java.util.*;

/**
 * QuizTree represents a quiz with binary choices that lead to answers.
 * QuizTree is built from a file containing question options and answers.
 * It has three main methods: takeQuiz, used to traverse the QuizTree by
 * asking questions; addQuestion, used to replace a result node with a new
 * question and pair of answers; and export, used to write the contents of
 * the current, possibly modified, quiz to a file.
 * 
 * @see #takeQuiz(Scanner)
 * @see #addQuestion(String, String, String, String, String)
 * @see #export(PrintStream)
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
        this.root = constructorHelper(inputFile);
    }

    /**
     * Interprets input file as a preorder traversal
     * and constructs QuizTree's binary search tree from
     * the root node.
     * 
     * @param inputFile
     */
    private QuizTreeNode constructorHelper(Scanner inputFile) {
        if (!inputFile.hasNextLine()) { // file fully read
            inputFile.close();
            return null;
        }

        QuizTreeNode node = parseQuizNodeOutput(inputFile.nextLine());
        if (!isResultNode(node)) { // not base case
            node.left = constructorHelper(inputFile);
            node.right = constructorHelper(inputFile);
        }

        return node;
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
     * Recursive helper method for takeQuiz to parse nodes and progress
     * on by user choice.
     *
     * @param node    the QuizTreeNode node to parse as either a question or a
     *                result
     * @param console the Scanner user input
     * 
     * @see #takeQuiz(Scanner)
     */
    private void takeQuizHelper(QuizTreeNode node, Scanner console) {
        if (isResultNode(node)) { // base case
            System.out.println("Your result is: " + node.result);
        } else {
            System.out.printf("Do you prefer %s or %s?\n", node.leftChoice, node.rightChoice);
            String input = console.nextLine();

            if (input.equalsIgnoreCase(node.leftChoice)) {
                node = node.left;
            } else if (input.equalsIgnoreCase(node.rightChoice)) {
                node = node.right;
            } else {
                System.out.println("\tInvalid response; try again.");
            }

            takeQuizHelper(node, console);
        }
    }

    /**
     * Checks whether a QuizTreeNode node is a result leaf Node
     * by assessing whether the result field is null.
     * 
     * @param node the QuizTreeNode node to check
     * 
     * @return if node is a result leaf, {@code true} if yes and {@code false}
     *         otherwise.
     */
    private static boolean isResultNode(QuizTreeNode node) {
        if (node == null)
            throw new IllegalArgumentException("Node cannot be null.");
        // return node.left == null && node.right == null;
        return node.result != null;
    }

    /**
     * Record the current QuizTree quiz the PrintStream output file.
     * 
     * @param outputFile the PrintStream file buffer on which to output the quiz.
     */
    public void export(PrintStream outputFile) {
        exportHelper(root, outputFile);
        outputFile.close();
    }

    /**
     * Recursive helper method for export; prints the current node to the
     * provided output file and uses preordering traversal for the rest of the tree.
     * 
     * @param node       the QuizTreeNode node to parse into a String line
     * @param outputFile the PrintStream file to write to
     * 
     * @see #export(PrintStream)
     */
    private void exportHelper(QuizTreeNode node, PrintStream outputFile) {
        if (node != null) {
            outputFile.println(formatQuizNodeOutput(node));
            exportHelper(node.left, outputFile);
            exportHelper(node.right, outputFile);
        }
    }

    /**
     * Returns the String text formatted version of a QuizTreeNode node for the
     * output file.
     * 
     * @param node the QuizTreeNode to format
     * 
     * @return the formatted QuizTreeNode node String.
     * 
     * @see #parseQuizNodeOutput(String) the parser for this format
     */
    private static String formatQuizNodeOutput(QuizTreeNode node) {
        return isResultNode(node) ? "END:" + node.result : node.leftChoice + "/" + node.rightChoice;
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
     * 
     * @param toReplace   the result String to replace
     * @param leftChoice  the new question's leftChoice field String
     * @param rightChoice the new question's rightChoice field String
     * @param leftResult  the new question's leftResult field String
     * @param rightResult the new question's rightResult field String
     */
    public void addQuestion(String toReplace, String leftChoice, String rightChoice,
            String leftResult, String rightResult) {
        addQuestionHelper(root, toReplace, leftChoice, rightChoice, leftResult, rightResult);
    }

    /**
     * Recursive helper method for addQuestion; traverses the QuizTree to match for
     * a QuizTreeNode who has a result node child with a result field that matches
     * toReplace.
     * On a case-insensitive match, replaces the node with a new question node with
     * a pair of answers.
     * 
     * @param node        the QuizTreeNode node to check
     * @param toReplace   the result String to replace
     * @param leftChoice  the new question's leftChoice field String
     * @param rightChoice the new question's rightChoice field String
     * @param leftResult  the new question's leftResult field String
     * @param rightResult the new question's rightResult field String
     */
    private void addQuestionHelper(QuizTreeNode node, String toReplace,
            String leftChoice, String rightChoice, String leftResult, String rightResult) {
        if (node != null) {
            QuizTreeNode[] children = new QuizTreeNode[] { node.left, node.right };

            for (QuizTreeNode child : children) {
                if (child != null && isResultNode(child) && child.result.equals(toReplace)) {
                    QuizTreeNode newNode = new QuizTreeNode(null, leftChoice, rightChoice,
                            new QuizTreeNode(leftResult), new QuizTreeNode(rightResult));

                    if (child == node.right)
                        node.right = newNode;

                    if (child == node.left)
                        node.left = newNode;
                } else {
                    addQuestionHelper(child,
                            toReplace, leftChoice, rightChoice, leftResult, rightResult);
                }
            }
        }
    }

    /**
     * The QuizTreeNode type represents a single node in the QuizTree;
     * the data class provides no methods beyond instance constructors.
     */
    private static class QuizTreeNode {
        // NOTE: may not construct any methods which are not used by #QuizTree!
        // structurally, i think it would be reasonable to have an #isleaf() method
        // for this class, and also a #tostring() override contingent upon the node
        // type.
        public QuizTreeNode left, right;

        public final String result;
        public final String leftChoice, rightChoice;

        /**
         * Constructs a general QuizTreeNode question leaf or branch
         * with data and links.
         */
        public QuizTreeNode(String result, String leftChoice, String rightChoice,
                QuizTreeNode left, QuizTreeNode right) {
            // NOTE: result field is not used with branch nodes
            this.result = result;
            this.leftChoice = leftChoice;
            this.rightChoice = rightChoice;
            this.left = left;
            this.right = right;
        }

        /**
         * Constructs a quiz result node.
         * 
         * @param result the final QuizTreeNode text
         */
        public QuizTreeNode(String result) {
            this(result, null, null, null, null);
        }
    }
}
