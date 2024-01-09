import java.util.ArrayList;
import java.util.List;
public class Tree {
    private Node root;

    public Tree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public List<Node> getAllNodes() {
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(root);
        recursivelyFindNodes(root, nodeList);
        return nodeList;
    }

    private void recursivelyFindNodes(Node node, List<Node> nodeList) {
        for (Node childNode : node.getChildren()) { // рекурсивно находим все узлы и иерархии дерева
            nodeList.add(childNode);
            recursivelyFindNodes(childNode, nodeList);
        }
    }

    public List<Node> getAllLeaves() {
        List<Node> leafList = new ArrayList<>();
        for (Node node : getAllNodes()) { // проверка всех узлов дерева на признаки листа
            if (node.isLeaf()) {
                leafList.add(node);
            }
        }
        return leafList;
    }
}
