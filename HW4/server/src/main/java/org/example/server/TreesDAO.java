package org.example.server;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TreesDAO {

    public static List<TransferNode> readTable(Session session) {
        List<TransferNode> nodeList = new ArrayList<>();
        Transaction readTransaction = session.beginTransaction();
        List<TreeEntity> EntityPairs = session.createQuery("FROM TreeEntity", TreeEntity.class).getResultList();
        readTransaction.commit();
        for (TreeEntity pair : EntityPairs) {
            int nodeId = pair.getId();
            int parentNodeId = pair.getParentId();
            TransferNode node = new TransferNode(new Node(nodeId), parentNodeId);
            nodeList.add(node);
        }
        return nodeList;
    }

    private static Node findNodeById(Node node, int nodeId) {
        if (node.getId() == nodeId) {
            return node;
        }
        for (Node child : node.getChildren()) {
            Node result = findNodeById(child, nodeId);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public static void writeTable(Session session, List<TransferNode> nodes) {
        Transaction cleanTransaction = session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE Trees", TreeEntity.class).executeUpdate();
        cleanTransaction.commit();
        Transaction write = session.beginTransaction();
        session.clear();
        for (TransferNode node : nodes) {
                session.persist(new TreeEntity(node.node.getId(), node.parentId));
            }
        write.commit();
    }

    public static void insert(Session session, int[] li) {
        Transaction insertTransaction = session.beginTransaction();
        for (int i = 0; i < li.length; i += 2) {
            session.persist(new TreeEntity(li[i], li[i + 1]));
        }
        insertTransaction.commit();
    }
}

