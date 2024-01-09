package com.example;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TreesDAO {
    public static List<Tree> readTable(Session session) {
        List<Tree> treeList = new ArrayList<>();
        Transaction readTransaction = session.beginTransaction();
        List<TreeEntity> nodesList = session.createQuery("FROM TreeEntity", TreeEntity.class).getResultList();
        readTransaction.commit();

        for (TreeEntity entity : nodesList) {
            if (entity.getId()==entity.getParentId()) {
                Node node = new Node(entity.getId());
                addChildren(nodesList, node);
                Tree tree = new Tree(node);
                treeList.add(tree);
            }
        }
        return treeList;
    }

    private static void addChildren(List<TreeEntity> nodesList, Node currentNode) {
        int currentNodeId = currentNode.getId();
        List<Node> childNodes = findChildNodes(nodesList, currentNodeId);
        if (!childNodes.isEmpty()) {
            for (Node childNode : childNodes) {
                currentNode.addChild(childNode);
                addChildren(nodesList, childNode);
            }
        }
    }

    private static List<Node> findChildNodes(List<TreeEntity> nodesList, int parentNodeId) {
        List<Node> childNodes = new ArrayList<>();
        for (TreeEntity TreeEntity : nodesList) {
            if (TreeEntity.getParentId() == parentNodeId && TreeEntity.getId() !=parentNodeId) {
                Node node = new Node(TreeEntity.getId());
                childNodes.add(node);
            }
        }
        return childNodes;
    }


    public static void writeTable(Session session, List<Tree> trees) {
        Transaction cleanTransaction = session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE Trees", TreeEntity.class).executeUpdate();
        cleanTransaction.commit();
        Transaction write = session.beginTransaction();
        session.clear();
        for (Tree tree : trees) {
            for (Node node : tree.getAllNodes()) {
                int parentId = node.getId();
                if (!node.isRoot()) {
                    parentId = node.getParent().getId();
                }
                session.persist(new TreeEntity(node.getId(), parentId));
            }
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

