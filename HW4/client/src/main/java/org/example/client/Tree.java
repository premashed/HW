package org.example.client;

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
        for (Node childNode : node.getChildren()) {
            nodeList.add(childNode);
            recursivelyFindNodes(childNode, nodeList);
        }
    }

    public boolean deleteNode(int NodeIn, List<Tree> TreeList) {
        for (Node node : this.getAllNodes()) {
            if (node.getId() == NodeIn) {
                if (node.getParent()==null) {
                    TreeList.remove(this);
                    return true;
                }
                Node parent = node.getParent();
                List<Node> children = node.getChildren();
                for (Node child : children) {
                    parent.addChild(child);
                }
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
    public static boolean idInTree(List<Tree> treeList, int NodeIn) {
        for (Tree tree : treeList) {
            for (Node node : tree.getAllNodes()) {
                if (node.getId() == NodeIn) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean addParent(int NodeIn, int ParentIn) {
        for (Node node : this.getAllNodes()) {
            if (node.getId() == NodeIn) {
                if (node.isRoot()){
                    Node parent = new Node(ParentIn);
                    parent.addChild(node);
                    node.setParent(parent);
                    root = parent;
                    return true;
                } else {
                    Node parent = new Node(ParentIn);
                    Node oldParent = node.getParent();
                    oldParent.addChild(parent);
                    oldParent.removeChild(node);
                    parent.addChild(node);
                    return true;
                }
            }
        }
        return false;
    }
    public static List<Tree> assemble(List<TransferNode> nodesList) {
        List<Tree> treeList = new ArrayList<>();

        for (TransferNode entity : nodesList) {
            if (entity.node.getId()==entity.parentId) {
                addChildren(nodesList, entity.node);
                Tree tree = new Tree(entity.node);
                treeList.add(tree);
            }
        }
        return treeList;
    }

    private static void addChildren(List<TransferNode> nodesList, Node currentNode) {
        int currentNodeId = currentNode.getId();
        List<Node> childNodes = findChildNodes(nodesList, currentNodeId);
        if (!childNodes.isEmpty()) {
            for (Node childNode : childNodes) {
                currentNode.addChild(childNode);
                addChildren(nodesList, childNode);
            }
        }
    }

    private static List<Node> findChildNodes(List<TransferNode> nodesList, int parentNodeId) {
        List<Node> childNodes = new ArrayList<>();
        for (TransferNode transferNode : nodesList) {
            if (transferNode.parentId == parentNodeId && transferNode.node.getId() !=parentNodeId) {
                childNodes.add(transferNode.node);
            }
        }
        return childNodes;
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