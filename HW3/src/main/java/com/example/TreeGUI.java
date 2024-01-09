package com.example;

import org.hibernate.Session;

import javax.swing.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.*;

class TreeGUI extends JFrame{
    List<Tree> trees;
    public TreeGUI(final Session session) {
        trees = TreesDAO.readTable(session);
        this.setTitle("Меню деревьев");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 300);
        this.setBackground(Color.WHITE);
        JPanel panel = new JPanel(new GridLayout(5, 3, 10, 10));

        JButton deleteFunc = new JButton("Удалить узел");
        final JTextField deleteIndex = new JTextField(10);
        JButton addChildFunc = new JButton("Добавить ребенка");
        final JTextField childIndex = new JTextField(10);
        final JTextField parentIndex = new JTextField(10);
        JButton addTreeFunc = new JButton("Добавить дерево");
        final JTextField newTreeIndex = new JTextField(10);
        JButton viewFunc = new JButton("Просмотреть");
        JButton readFunc = new JButton("Прочитать из БД");
        JButton writeFunc = new JButton("Записать в БД");
        JButton addParentFunc = new JButton("Добавить родителя");
        final JTextField child2Index = new JTextField(10);
        final JTextField parent2Index = new JTextField(10);
        deleteFunc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int nodeIn = Integer.parseInt(deleteIndex.getText());
                for (Tree tree : trees) {
                    if (tree.deleteNode(nodeIn, trees)) {
                        break;
                    }
                }
            }
        });

        addChildFunc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int childIn = Integer.parseInt(childIndex.getText());
                int parentIn = Integer.parseInt(parentIndex.getText());
                if (!Tree.idInTree(trees, childIn)) {
                    for (Tree tree : trees) {
                        if (tree.addChild(childIn, parentIn)) {
                            break;
                        }
                    }
                }
            }
        });

        addTreeFunc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rootIn = Integer.parseInt(newTreeIndex.getText());
                    if (!Tree.idInTree(trees, rootIn)) {
                        trees.add(new Tree(new Node(rootIn)));
                    }
            }
        });

        viewFunc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreeViewer viewer = new TreeViewer(trees);
                viewer.setVisible();
            }
        });

        readFunc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trees = TreesDAO.readTable(session);
            }
        });
        writeFunc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreesDAO.writeTable(session, trees);
            }
        });
        addParentFunc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int childIn = Integer.parseInt(child2Index.getText());
                int parentIn = Integer.parseInt(parent2Index.getText());
                if (!Tree.idInTree(trees, parentIn)) {
                    for (Tree tree : trees) {
                        if (tree.addParent(childIn, parentIn)) {
                            break;
                        }
                    }
                }
            }
        });

        panel.add(viewFunc);
        panel.add(readFunc);
        panel.add(writeFunc);
        panel.add(deleteFunc);
        panel.add(deleteIndex);
        panel.add(new JLabel(""));
        panel.add(addChildFunc);
        panel.add(childIndex);
        panel.add(parentIndex);
        panel.add(addParentFunc);
        panel.add(child2Index);
        panel.add(parent2Index);
        panel.add(addTreeFunc);
        panel.add(newTreeIndex);
        panel.add(new JLabel(""));
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }
}
