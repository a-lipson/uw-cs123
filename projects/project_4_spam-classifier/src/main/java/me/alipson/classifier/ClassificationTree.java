package me.alipson.classifier;

// Alexandre Lipson
// 08/12/24
// CSE 123
// P4: Spam Classifier
// TA: Daniel

import java.util.*;
import java.io.*;

/**
 * ClassificationTree provides an implementation for the abstract class
 * Classifier for a Binary Search Tree branching decision model.
 * The class provides methods to validate whether input Classifiable data can be
 * classified, and also to perform classification. Additionally, it also
 * provides export and import functionality of pre-ordered BST models from
 * files.
 * 
 * @see #canClassify(Classifiable)
 * @see #classify(Classifiable)
 * @see #ClassificationTree(Scanner)
 * @see #save(PrintStream)
 */
public class ClassificationTree extends Classifier {
    private ClassificationNode root;

    /**
     * Creates a new ClassificationTree through training from pairs of data and
     * labels.
     * 
     * @param data   the List<Classifiable> of data entries to process
     * @param labels the List<String> of corresponding label results from the data
     * 
     * @throws IllegalArgumentException if input Lists are either empty or not the
     *                                  same size.
     */
    public ClassificationTree(List<Classifiable> data, List<String> labels) {
        if (data.isEmpty())
            throw new IllegalArgumentException("data list cannot be empty.");
        if (data.size() != labels.size())
            throw new IllegalArgumentException("data list must be the same length as labels list.");

        for (int i = 0; i < data.size(); i++)
            this.root = fromLists(this.root, data.get(i), labels.get(i));
    }

    /**
     * Trains model according to the following algorithm:
     * 1. Traverse through the current classification tree until a label leaf node.
     * 2. If the node's label matches the current label, do nothing as the model is
     * accurate up to this point.
     * 3. If the label is incorrect, create a split between the data used to create
     * the original leaf node and the current input.
     * 
     * @param node  the current ClassificationNode node
     * @param data  the Classifiable data object to process in the current model
     * @param label the String result label of the current data
     * 
     * @return the ClassificationNode root holding the rest of the
     *         ClassificationTree.
     * 
     * @see #ClassificationTree(List, List) the public constructor.
     * @see Classifiable#partition(Classifiable) the method to generate Splits.
     */
    private ClassificationNode fromLists(ClassificationNode node, Classifiable data, String label) {
        if (node == null) // base case
            return new ClassificationNode(label, null, data);

        if (!node.isLabel()) { // traverse until label node
            TreeLens sideLens = node.getSplit().evaluate(data)
                    ? ClassificationNode.leftLens
                    : ClassificationNode.rightLens;
            sideLens.set(node, fromLists(sideLens.get(node), data, label));
            return node;
        }

        if (node.label.equals(label)) // model is accurate
            return node;

        Split split = data.partition(node.data);

        ClassificationNode newNode = new ClassificationNode(split);
        ClassificationNode newLabel = new ClassificationNode(label, null, data);

        boolean applyLeft = split.evaluate(data);

        TreeLens newLens = applyLeft ? ClassificationNode.leftLens : ClassificationNode.rightLens;
        TreeLens oldLens = applyLeft ? ClassificationNode.rightLens : ClassificationNode.leftLens;

        newLens.set(newNode, newLabel);
        oldLens.set(newNode, node);

        return newNode;
    }

    /**
     * Creates a new ClassificationTree by reading from an input file.
     * 
     * @param sc the input file Scanner to read from.
     */
    public ClassificationTree(Scanner sc) {
        this.root = fromScanner(sc);
        if (this.root == null)
            throw new IllegalStateException("Classification tree is null.");
    }

    /**
     * Helper method for the input file constructor; reads input from file as
     * preorder following format conventions from ClassificationNode's String
     * representation.
     * 
     * @param inputFile the input file Scanner to read from.
     * 
     * @return the ClassificationNode root holding the ClassificationTree.
     * 
     * @see #ClassificationTree(Scanner)
     * @see ClassificationNode#toString()
     */
    private ClassificationNode fromScanner(Scanner inputFile) {
        if (!inputFile.hasNextLine()) {
            inputFile.close();
            return null;
        }

        String inputLine = inputFile.nextLine();

        if (inputLine.contains("Feature")) {
            // limit split if ":" in feature String
            String feature = inputLine.split(":", 2)[1].trim();
            double threshold = Double.parseDouble(inputFile.nextLine().split(":")[1]);
            ClassificationNode node = new ClassificationNode(new Split(feature, threshold));

            node.left = fromScanner(inputFile);
            node.right = fromScanner(inputFile);

            return node;
        }

        return new ClassificationNode(inputLine);
    }

    /**
     * Returns whether the given Classifiable input can be classified by the
     * ClassificationTree.
     * 
     * @param input the Classifiable input to check
     * 
     * @return {@code true} if the input can be labeled, {@code false} otherwise.
     */
    public boolean canClassify(Classifiable input) {
        return canClassifyHelper(root, input.getFeatures());
    }

    /**
     * Checks whether the given ClassificationNode is null or a leaf node, or if the
     * provided features from the Classifiable input contain both of the node's
     * Split split features.
     * 
     * @param node     the current ClassificationNode node to check
     * @param features the Set<String> of features to match for branching split
     *                 nodes
     * 
     * @return {@code true} if the given node is a leaf label node or is a split
     *         with valid features, {@code false} otherwise.
     */
    private boolean canClassifyHelper(ClassificationNode node, Set<String> features) {
        return node == null || node.isLabel()
                || (features.contains(node.getSplit().getFeature())
                        && canClassifyHelper(node.left, features)
                        && canClassifyHelper(node.right, features));
    }

    /**
     * Classifies the provided input, if input can be classified, by providing a
     * label.
     * 
     * @param input the Classifiable input to classify
     * 
     * @return the String label assigned to the input.
     * 
     * @throws IllegalArgumentException if the input cannot be classified
     * 
     * @see #canClassify(Classifiable)
     */
    public String classify(Classifiable input) {
        if (!canClassify(input))
            throw new IllegalArgumentException("cannot classify input.");
        return classifyHelper(root, input);
    }

    /**
     * Helper method for classify that descescends the ClassificationTree tree using
     * each node's split evaluate method until a leaf label node.
     * 
     * @param node  the current ClassificationNode node used to process the input
     * @param input the Classifiable input to classify
     * 
     * @return the String label for the input.
     * 
     * @see Split#evaluate(Classifiable)
     */
    private String classifyHelper(ClassificationNode node, Classifiable input) {
        // return node.isLabel()
        // ? node.toString()
        // : node.map(node.getSplit().evaluate(input), new TreeOperation<String>() {
        // public String apply(ClassificationNode node) {
        // return classifyHelper(node, input);
        // }});

        return node.isLabel()
                ? node.toString()
                : node.getSplit().evaluate(input)
                        ? classifyHelper(node.left, input)
                        : classifyHelper(node.right, input);
    }

    /**
     * Saves the current ClassificationTree model to an output file.
     * 
     * @param ps the output file PrintStream on which to write
     */
    public void save(PrintStream ps) {
        saveHelper(root, ps);
        ps.close();
    }

    /**
     * Helper method for writing to an output file. Writes the node's String to the
     * buffer and proceeds in a preorder traversal.
     * 
     * @param node       the current ClassificationNode node to print
     * @param outputFile the output file PrintStream buffer to write to
     * 
     * @see #save(PrintStream)
     * @see ClassificationNode#toString()
     */
    private void saveHelper(ClassificationNode node, PrintStream outputFile) {
        if (!(node == null)) {
            outputFile.println(node.toString());
            saveHelper(node.left, outputFile);
            saveHelper(node.right, outputFile);
        }
    }

    /**
     * ClassificationNode is the node class for the {@link ClassificationTree} BST.
     * It stores both left and right child nodes in addition to a String label, a
     * Split split, and the Classifiable data used to generate the node from a Split
     * It offers constructors for a label leaf node, a branch split node, and a
     * general constructor for all fields without child links.
     */
    private static class ClassificationNode {
        public ClassificationNode left;
        public ClassificationNode right;

        public final String label;
        public final Split split;
        public final Classifiable data;

        /**
         * General constructor for ClassificationNode that takes a String label, a Split
         * split, and Classifiable input data used for the split; does not set any child
         * node links.
         * 
         * @param label the String label to use
         * @param split the Split split to use
         * @param data  the Classifiable input data used to create the split
         */
        public ClassificationNode(String label, Split split, Classifiable data) {
            this.label = label;
            this.split = split;
            this.data = data;
        }

        /**
         * Creates a new leaf label node with a String label.
         * 
         * @param label the String label to use
         */
        public ClassificationNode(String label) {
            this(label, null, null);
        }

        /**
         * Creates a new branching split node with a Split split.
         * 
         * @param split the Split split to use
         */
        public ClassificationNode(Split split) {
            this(null, split, null);
        }

        /**
         * Indicates whether the current ClassificationNode node is a label leaf node.
         * 
         * @return {@code true} is yes and {@code false} otherwise.
         */
        public boolean isLabel() {
            return label != null;
        }

        /**
         * Getter method for the Split split field of ClassificationNode.
         * 
         * @return a reference to the Split split field.
         */
        public Split getSplit() {
            return split;
        }

        // /**
        // */
        // public <T> T map(boolean applyLeft, TreeOperation<T> operation) {
        // return applyLeft ? operation.apply(left) : operation.apply(right);
        // }

        /**
        */
        public static TreeLens rightLens = new TreeLens() {
            public ClassificationNode get(ClassificationNode node) {
                return node.right;
            }

            public ClassificationNode set(ClassificationNode node, ClassificationNode newChild) {
                node.right = newChild;
                return node;
            }
        };

        /**
         * // NOTE: will probably have to get rid of these so they won't receive
         * comments for now.
         */
        public static TreeLens leftLens = new TreeLens() {
            public ClassificationNode get(ClassificationNode node) {
                return node.left;
            }

            public ClassificationNode set(ClassificationNode node, ClassificationNode newChild) {
                node.left = newChild;
                return node;
            }
        };

        /**
         * Provides a String representation for a ClassificationNode contingent on
         * whether the node is leaf label node or a branching split node.
         * Uses the split's string representation, or simply the label String.
         * 
         * @return the conditionally formatted String.
         * 
         * @see Split#toString()
         */
        @Override
        public String toString() {
            return (split == null) ? label : split.toString();
        }
    }

    // private interface TreeOperation<T> {
    // T apply(ClassificationNode node);
    // }

    /**
     * TreeLens extends {@link Lens} for {@link ClassificationNode} by providing a
     * Lens interface for BST node links.
     */
    private interface TreeLens extends Lens<ClassificationNode, ClassificationNode> {
    }

    /**
     * Functional interface for the Lens Optic to modify an Attribute field within a
     * Source object.
     * Requires a get method which returns the attribute part of the source object,
     * and a set method, which modifies the attribute part of the source object and
     * returns the modified source.
     */
    private interface Lens<S, A> {
        /**
         * Getter function for the Lens.
         * 
         * @param s the source Object of type S
         * 
         * @return the attribute of type A.
         */
        A get(S s);

        /**
         * Setter function for the lens.
         * 
         * @param s the source Object of type S
         * @param a the attribute of type A to update
         * 
         * @return the modified source.
         */
        S set(S s, A a);
    }
}
