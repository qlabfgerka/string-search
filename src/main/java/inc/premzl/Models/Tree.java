package inc.premzl.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tree {
    private final List<Node> children;

    public Tree() {
        this.children = new ArrayList<>();
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addWord(String word, List<Node> nodes, int counter) {
        if (counter >= word.length()) return;

        for (Node node : nodes) {
            if (Objects.equals(node.getWeight(), String.valueOf(word.charAt(counter)))) {
                addWord(word, node.getChildren(), ++counter);
                return;
            }
        }

        Node node = new Node(String.valueOf(word.charAt(counter)));
        nodes.add(node);
        addWord(word, node.getChildren(), ++counter);
    }

    public void addWord(String word,
                        List<Node> nodes,
                        boolean found,
                        Node parent,
                        int currentSequence,
                        int substring,
                        boolean continueCompare) {
        if (word.length() == 0) return;

        List<Node> children = new ArrayList<>();
        int firstCounter = 0;

        for (Node node : nodes) {
            while (Objects.equals(node.getWeight().charAt(firstCounter), word.charAt(firstCounter)) && continueCompare) {
                ++firstCounter;
                found = true;

                if (firstCounter >= node.getWeight().length() || firstCounter >= word.length()) break;
            }

            if (found && firstCounter != 0) {
                addWord(word.substring(firstCounter),
                        node.getChildren(),
                        true,
                        node,
                        currentSequence,
                        firstCounter,
                        firstCounter == node.getWeight().length());
                node.setWeight(node.getWeight().substring(0, firstCounter));
                node.setSequenceNumber(null);
                return;
            }
        }

        if (found && !continueCompare) {
            children = Node.clone(nodes);
            nodes.clear();
        }

        Node node = new Node(word, currentSequence);
        nodes.add(node);

        if (found && !continueCompare) {
            node = new Node(parent.getWeight().substring(substring), parent.getSequenceNumber());
            node.setChildren(children);
            nodes.add(node);
        }
    }

    public boolean findIndexes(String haystack, int i, List<Node> nodes, List<Integer> indexes) {
        if (nodes.size() == 0) return true;
        if (haystack.length() == 0) return false;

        for (Node node : nodes) {
            if (Objects.equals(haystack.charAt(0), node.getWeight().charAt(0))) {
                if (findIndexes(haystack.substring(1), i, node.getChildren(), indexes))
                    indexes.add(i);
            }
        }

        return false;
    }

    public void findIndexes(String haystack, List<Node> nodes, boolean finished, List<Integer> indexes) {
        int counter;
        boolean currentFinished = false;
        for (Node node : nodes) {
            boolean found = false;
            counter = 0;
            while (!finished && !Objects.equals(haystack, "") && haystack.charAt(counter) == node.getWeight().charAt(counter)) {
                found = true;
                counter++;

                if (counter >= haystack.length()) {
                    finished = true;
                    currentFinished = true;
                    break;
                }
                if (counter >= node.getWeight().length()) break;
            }

            if (finished && node.getSequenceNumber() != null) {
                indexes.add(node.getSequenceNumber());
            }

            if (found || finished) {
                findIndexes(haystack.substring(counter), node.getChildren(), finished, indexes);
            }

            if (currentFinished) return;
        }
    }

    public void displayTree() {
        StringBuilder builder = new StringBuilder();
        for (Node node : children) node.print(builder, "", "");
        System.out.println(builder);
    }
}
