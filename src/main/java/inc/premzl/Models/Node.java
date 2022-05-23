package inc.premzl.Models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node {
    private String weight;
    private Integer sequenceNumber;
    List<Node> children;

    public Node(String weight) {
        this.weight = weight;
        this.sequenceNumber = null;
        this.children = new ArrayList<>();
    }

    public Node(String weight, Integer sequenceNumber) {
        this.weight = weight;
        this.sequenceNumber = sequenceNumber;
        this.children = new ArrayList<>();
    }

    public Node(String weight, Integer sequenceNumber, List<Node> children) {
        this.weight = weight;
        this.sequenceNumber = sequenceNumber;
        this.children = children;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(weight).append(" ").append(sequenceNumber);
        buffer.append('\n');
        for (Iterator<Node> it = children.iterator(); it.hasNext(); ) {
            Node next = it.next();
            if (it.hasNext()) {
                next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }

    public static List<Node> clone(List<Node> nodes) {
        List<Node> cloned = new ArrayList<>();
        for (Node node : nodes) {
            cloned.add(new Node(node.getWeight(), node.getSequenceNumber(), clone(node.getChildren())));
        }
        return cloned;
    }
}
