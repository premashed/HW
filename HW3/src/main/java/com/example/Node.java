package com.example;

import java.util.ArrayList;
import java.util.List;
public class Node {
    private int id;
    private Node parent;
    private List<Node> children = new ArrayList<>();

    public Node(int id) {this.id = id;}

    public int getId() {
        return id;
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }
    public void removeChild(Node child) {children.remove(child);}

    public boolean isLeaf() {return children.isEmpty();}

    public boolean isRoot() {
        return parent == null;
    }

    public void setParent(Node parentNode) {
        this.parent = parentNode;
    }

    public void addChild(Node child) {child.setParent(this);this.children.add(child);}
}
