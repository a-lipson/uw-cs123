package me.alipson.quiz;

// Alexandre Lipson
// 08/07/24
// CSE 123
// P3: JoeFeed Quiz
// TA: Daniel

import java.io.*;
import java.util.*;

/**
*/
public class QuizTree2 {
    private QuizTreeNode root;

    /**
     * Constructs a new quiz from the provided Scanner input.
     * 
     * @param inputFile the file input Scanner from which to create a new QuizTree
     *                  quiz
     */
    public QuizTree2(Scanner inputFile) {
        this.root = constructorHelper(inputFile);
    }

    /**
     * interpret input file as preorder traversal
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
     * Helper method
     *
     * @param node
     * @param console
     */
    private void takeQuizHelper(QuizTreeNode node, Scanner console) {
        if (isResultNode(node)) { // base case
            System.out.println("Your result is: " + node.result);
        } else {
            System.out.printf("Do you prefer %s or %s?\n", node.leftChoice, node.rightChoice);
            String input = console.nextLine();

            // NOTE: here is a great place for a bifunctor!
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
        map(new TreeConsumer<Void>() {
            @Override
            public Void apply(QuizTreeNode node) {
                outputFile.println(formatQuizNodeOutput(node));
                return null;
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
     */
    public void addQuestion(String toReplace, String leftChoice, String rightChoice,
            String leftResult, String rightResult) {
        // HACK: bypassing forbidden feature of Java 8 anonymous functions/lambdas.
        map(new TreeConsumer<Boolean>() {
            @Override
            public Boolean apply(QuizTreeNode node) {
                QuizTreeNode newNode = new QuizTreeNode(null, leftChoice, rightChoice,
                        new QuizTreeNode(leftResult), new QuizTreeNode(rightResult));
                for (Lens<QuizTreeNode, QuizTreeNode> lens : new Lens[] {
                        nodeLeftLens, nodeRightLens })
                    if (node != null && isResultNode(node) && node.result.equals(toReplace)) {
                        lens.set(node, newNode);
                        return true;
                    }

                return false;
            }
        });
    }

    /**
    */
    private static final Lens<QuizTreeNode, QuizTreeNode> nodeLeftLens = new Lens<>() {
        @Override
        public QuizTreeNode get(QuizTreeNode node) {
            return node.left;
        }

        @Override
        public QuizTreeNode set(QuizTreeNode node, QuizTreeNode newChild) {
            node.left = newChild;
            return node;
        }
    };

    /**
    */
    private static final Lens<QuizTreeNode, QuizTreeNode> nodeRightLens = new Lens<>() {
        @Override
        public QuizTreeNode get(QuizTreeNode node) {
            return node.right;
        }

        @Override
        public QuizTreeNode set(QuizTreeNode node, QuizTreeNode newChild) {
            node.right = newChild;
            return node;
        }
    };

    /**
     * Provides a higher order function application for the TreeOperation type
     * through a preorder traversal.
     * 
     * @param operation a valid TreeOperation function to apply to a QuizTree
     * 
     * @see #map(QuizTreeNode, TreeConsumer)
     */
    private void map(TreeConsumer<?> operation) {
        map(root, operation);
    }

    /**
     * Applies a TreeOperation type function through a preorder traversal of the
     * tree nodes belonging to the given QuizTreeNode node.
     * 
     * @param node      the root QuizTreeNode node over which to traverse.
     * @param operation a valid TreeOperation function to apply to the QuizTree
     */
    private void map(QuizTreeNode node, TreeConsumer<?> operation) {
        if (node != null) {
            Object result = operation.apply(node);
            if (result instanceof Boolean && !(Boolean) result) {
                map(node.left, operation);
                map(node.right, operation);
            }
        }
    }

    /**
     * TreeOperation is a type for effectual functions that can be applied to
     * QuizTreeNodes.
     */
    private interface TreeConsumer<T> {
        T apply(QuizTreeNode node);
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
         * Constructs a quiz question node
         * 
         * @param result the final QuizTreeNode text.
         */
        public QuizTreeNode(String result) {
            this(result, null, null, null, null);
        }

    }

}
