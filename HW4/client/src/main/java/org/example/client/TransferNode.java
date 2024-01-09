package org.example.client;

public class TransferNode {
    public Node node;
    public int parentId;

    public TransferNode(Node node, int parentId) {
        this.node = node;
        this.parentId = parentId;
    }

    public TransferNode() {}
}
