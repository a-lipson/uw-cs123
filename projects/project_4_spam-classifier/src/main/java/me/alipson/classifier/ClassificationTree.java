package me.alipson.classifier;

// Alexandre Lipson
// 08/12/24
// CSE 123
// P4: Spam Classifier
// TA: Daniel

import java.util.*;
import java.io.*;

public class ClassificationTree extends Classifier {
    // TODO: Implement these!
    private ClassificationNode root;

    /**
     * 
     * @param data
     * @param labels
     */
    public ClassificationTree(List<Classifiable> data, List<String> labels) {
        if (data.isEmpty())
            throw new IllegalArgumentException("data list cannot be empty.");
        if (data.size() != labels.size())
            throw new IllegalArgumentException("data list must be the same length as labels list.");

        this.root = fromLists(root, data, labels);
    }

    /**
     * Trains model
     * 
     * @param node
     * @param data
     * @param labels
     * 
     * @return
     */
    private ClassificationNode fromLists(ClassificationNode node, List<Classifiable> data, List<String> labels) {
        if (data.isEmpty()) // end case
            return null;

        if (node == null) { // base case
            ClassificationNode newNode = new ClassificationNode(labels.remove(0), null, data.remove(0));
            return fromLists(newNode, data, labels);
        }

        if (!node.isLabel()) { // traverse until label node
            if (node.getSplit().evaluate(data.get(0))) {
                node.left = fromLists(node.left, data, labels);
            } else {
                node.right = fromLists(node.right, data, labels);
            }
            return node;
        }

        String currLabel = labels.remove(0);
        Classifiable currData = data.remove(0);

        if (node.label.equals(currLabel))
            return fromLists(node, data, labels);

        Split split = currData.partition(node.data);

        ClassificationNode newNode = new ClassificationNode(split);
        ClassificationNode newLabel = new ClassificationNode(currLabel, null, currData);

        if (split.evaluate(currData)) {
            newNode.left = newLabel;
            newNode.right = node;
        } else {
            newNode.left = node;
            newNode.right = newLabel;
        }

        if (node.left.isLabel()) {
            node.left = newNode;
        } else {
            node.right = newNode;
        }

        return fromLists(newNode, data, labels);
    }

    /**
     * 
     * @param sc
     */
    public ClassificationTree(Scanner sc) {
        this.root = fromScanner(sc);
        if (this.root == null)
            throw new IllegalStateException("Classification tree is null.");
    }

    /**
     * 
     * @param inputFile
     * 
     * @return
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
     * @param input
     * 
     * @return
     */
    public boolean canClassify(Classifiable input) {
        return canClassifyHelper(root, input.getFeatures());
    }

    /**
    */
    private boolean canClassifyHelper(ClassificationNode node, Set<String> features) {
        return node == null || node.isLabel()
                || (features.contains(node.getSplit().getFeature())
                        && canClassifyHelper(node.left, features)
                        && canClassifyHelper(node.right, features));
    }

    /**
    */
    public String classify(Classifiable input) {
        return classifyHelper(root, input);
    }

    /**
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
    */
    public void save(PrintStream ps) {
        saveHelper(root, ps);
        ps.close();
    }

    /**
    */
    private void saveHelper(ClassificationNode node, PrintStream outputFile) {
        if (!(node == null)) {
            outputFile.println(node.toString());
            saveHelper(node.left, outputFile);
            saveHelper(node.right, outputFile);
        }
    }

    /**
    */
    private static class ClassificationNode {
        public ClassificationNode left;
        public ClassificationNode right;

        public final String label;
        public final Split split;

        // TODO: rename this field to something better
        private final Classifiable data;

        /**
        */
        public ClassificationNode(String label, Split split, Classifiable data) {
            this.label = label;
            this.split = split;
            this.data = data;
        }

        /**
        */
        public ClassificationNode(String label) {
            this(label, null, null);
        }

        /**
        */
        public ClassificationNode(Split split) {
            this(null, split, null);
        }

        /**
        */
        public boolean isLabel() {
            return label != null;
        }

        /**
        */
        public Split getSplit() {
            return split;
        }

        public <T> T map(boolean applyLeft, TreeOperation<T> operation) {
            return applyLeft ? operation.apply(left) : operation.apply(right);
        }

        /**
         * @see Split#toString()
         */
        @Override
        public String toString() {
            return (split == null) ? label : split.toString();
        }
    }

    private interface TreeOperation<T> {
        T apply(ClassificationNode node);
    }
}
