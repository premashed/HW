import java.util.ArrayList;
import java.util.List;
public class Node {
    private int id;
    private Node parent;
    private List<Node> children = new ArrayList<>();
    private int parentId;

    public Node(int id) {this.id = id;}

    public int getId() {
        return id;
    }
    public int getParentId() {return parentId;}
    public void setParentId(int parentId) {this.parentId = parentId;}

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public boolean isLeaf() {return children.isEmpty();}

    public boolean isRoot() {
        return parent == null;
    }

    public void setParent(Node parentNode) {
        this.parent = parentNode;
    }

    public void addChild(Node child) {child.setParent(this);this.children.add(child);}
}
