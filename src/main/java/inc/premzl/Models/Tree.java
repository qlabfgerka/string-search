package inc.premzl.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tree {
    private List<Node> children;

    public Tree() {
        this.children = new ArrayList<>();
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addWord(String word, List<Node> nodes, int counter) {
        if (counter >= word.length()) return;

        boolean found = false;
        for (Node node : nodes) {
            if (Objects.equals(node.getWeight(), String.valueOf(word.charAt(counter)))) {
                addWord(word, node.getChildren(), ++counter);
                found = true;
                counter = 0;
                return;
            }
        }

        if (found) return;

        Node node = new Node(String.valueOf(word.charAt(counter)));
        nodes.add(node);
        addWord(word, node.getChildren(), ++counter);
    }

    public void addWordCollapse(String word,
                                List<Node> nodes,
                                int counter,
                                boolean found,
                                String parent,
                                int parentSequence,
                                int currentSequence) {
        if (counter >= word.length()) return;

        for (Node node : nodes) {
            while (Objects.equals(node.getWeight().charAt(counter), word.charAt(counter))) {
                ++counter;
                found = true;
            }

            if (found) {
                addWordCollapse(word,
                        node.getChildren(),
                        counter,
                        true,
                        node.getWeight(),
                        node.getSequenceNumber(),
                        currentSequence);
                node.setWeight(node.getWeight().substring(0, counter));
                node.setSequenceNumber(null);
                return;
            }
        }

        Node node = new Node(word.substring(counter), currentSequence);
        nodes.add(node);

        if (found) {
            node = new Node(parent.substring(counter), parentSequence);
            nodes.add(node);
        }
    }

    public void displayTree() {
        StringBuilder builder = new StringBuilder();
        for (Node node : children) node.print(builder, "", "");
        System.out.println(builder);
    }
}
