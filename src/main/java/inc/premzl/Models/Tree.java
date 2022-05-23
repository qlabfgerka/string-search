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

    public void addWordCollapse(String word,
                                List<Node> nodes,
                                int counter,
                                boolean found,
                                Node parent,
                                Integer parentSequence,
                                int substring,
                                int currentSequence) {
        if (counter >= word.length()) return;

        List<Node> children = new ArrayList<>();
        int firstCounter = 0;

        for (Node node : nodes) {
            while (Objects.equals(node.getWeight().charAt(firstCounter), word.charAt(counter))) {
                ++counter;
                ++firstCounter;
                found = true;

                if (firstCounter >= node.getWeight().length()) break;
            }

            if (found && firstCounter != 0) {
                addWordCollapse(word,
                        node.getChildren(),
                        counter,
                        true,
                        node,
                        node.getSequenceNumber(),
                        firstCounter,
                        currentSequence);
                node.setWeight(node.getWeight().substring(0, firstCounter));
                node.setSequenceNumber(null);
                return;
            }
        }

        if (found) {
            children = Node.clone(nodes);
            nodes.clear();
        }

        Node node = new Node(word.substring(counter), currentSequence);
        nodes.add(node);

        if (found) {
            node = new Node(parent.getWeight().substring(substring), parentSequence);
            node.setChildren(children);
            nodes.add(node);
        }
    }

    public void displayTree() {
        StringBuilder builder = new StringBuilder();
        for (Node node : children) node.print(builder, "", "");
        System.out.println(builder);
    }
}
