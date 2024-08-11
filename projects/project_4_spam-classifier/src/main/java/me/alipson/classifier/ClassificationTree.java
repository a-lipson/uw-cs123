package me.alipson.classifier;

// Alexandre Lipson
// 08/12/24
// CSE 123
// P4: Spam Classifier
// TA: Daniel

import java.util.*;
import java.io.*;

/**
*/
public class ClassificationTree extends Classifier {
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

        for (int i = 0; i < data.size(); i++)
            this.root = fromLists(this.root, data.get(i), labels.get(i));
    }

    /**
     * Trains model
     * 
     * @param node
     * @param data
     * @param label
     * 
     * @return
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
     * @param node
     * @param features
     * 
     * @return
     */
    private boolean canClassifyHelper(ClassificationNode node, Set<String> features) {
        return node == null || node.isLabel()
                || (features.contains(node.getSplit().getFeature())
                        && canClassifyHelper(node.left, features)
                        && canClassifyHelper(node.right, features));
    }

    /**
     * @param input
     * 
     * @return
     */
    public String classify(Classifiable input) {
        if (!canClassify(input))
            throw new IllegalArgumentException("cannot classify input.");
        return classifyHelper(root, input);
    }

    /**
     * @param node
     * @param input
     * 
     * @return
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
     * @param ps
     */
    public void save(PrintStream ps) {
        saveHelper(root, ps);
        ps.close();
    }

    /**
     * @param node
     * @param outputFile
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
        public final Classifiable data;

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
    */
    private interface TreeLens extends Lens<ClassificationNode, ClassificationNode> {
    }

    /**
     * source, attribute
     */
    private interface Lens<S, A> {
        /**
        */
        A get(S s);

        /**
        */
        S set(S s, A a);
    }
}
