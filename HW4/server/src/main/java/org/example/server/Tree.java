package org.example.server;

import org.hibernate.Transaction;

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

    public boolean deleteNode(int NodeIn) {
        for (Node node : this.getAllNodes()) {
            if (node.getId() == NodeIn) {
                Node parent = node.getParent();
                parent.removeChild(node);
                return true;
            }
        }
        return false;
    }
    public boolean addChild(int childId, int parentId) {
        for (Node node : this.getAllNodes()) {
            if (node.getId() == parentId) {
                Node child = new Node(childId);
                node.addChild(child);
                child.setParent(node);
                return true;
                }
            }
        return false;
    }
    public boolean addParent(int NodeIn, int ParentIn) {
        for (Node node : this.getAllNodes()) {
            if (node.getId() == NodeIn) {
                if (node.isRoot()){
                    Node parent = new Node(ParentIn);
                    this.root = parent;
                    parent.addChild(node);
                    node.setParent(parent);
                    return true;
                } else {
                    Node parent = new Node(ParentIn);
                    Node oldParent = node.getParent();
                    parent.addChild(node);
                    node.setParent(parent);
                    oldParent.addChild(parent);
                    oldParent.removeChild(node);
                    parent.setParent(oldParent);
                    return true;
                }
            }
        }
        return false;
    }


    public List<String> treeInfo() {
        List<String> li = new ArrayList<>();
        for (Node node : this.getAllNodes()) {
            li.add(nodeInfo(node));
        }
        return li;
    }
    public String nodeInfo(Node node) {
        String res = "";
        StringBuilder line = new StringBuilder();
        if (node.isRoot()) {
            line.append("Корень: ");
            line.append(node.getId());
        } else {
            line.append("Узел: ");
            line.append(node.getId());
            line.append(", Родитель: ");
            line.append(node.getParent().getId());
            line.append(",");
        }
        if (node.isLeaf()) {
            line.append(" Дети: ()");
            res = line.toString();
        } else {
            line.append(" Дети: (");
            for (Node child : node.getChildren()) {
                line.append(child.getId());
                line.append(", ");
            }
            res = line.toString();
            res = res.substring(0, res.length() - 2);
            res = res + ")";
        }
        return res;
    }
}