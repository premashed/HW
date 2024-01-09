package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TreeViewer extends JFrame{

    private JFrame mainFrame;
    private JPanel mainPanel;
    private DefaultListModel<String> listModel;
    private JList<String> dataList;
    private List<String> buttonNames;
    private List<Tree> trees;

    public TreeViewer(final List<Tree> trees) {
            mainFrame = new JFrame("Просмотр деревьев");
            mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            mainFrame.setSize(500, 300);
            mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            listModel = new DefaultListModel<>();
            dataList = new JList<>(listModel);

            buttonNames = new ArrayList<>();
            for (Tree tree : trees) {
                buttonNames.add("Дерево с корнем " + String.valueOf(tree.getRoot().getId()));
            }
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(buttonNames.size(), 1));
            for (final String buttonName : buttonNames) {
                JButton button = new JButton(buttonName);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateData(buttonName, trees);
                    }
                });
                buttonPanel.add(button);
            }

            mainPanel.add(buttonPanel, BorderLayout.WEST);
            mainPanel.add(new JScrollPane(dataList), BorderLayout.CENTER);
            mainFrame.add(mainPanel);

        }

    private void updateData(String buttonName, List<Tree> trees) {
        listModel.clear();
        List<String> data = getData(buttonName, trees);
        for (String item : data) {
            listModel.addElement(item);
        }
    }
    private List<String> getData(String buttonName, List<Tree> trees) {
        List<String> data = new ArrayList<>();
        data = trees.get(buttonNames.indexOf(buttonName)).treeInfo();
        return data;
    }

    public void setVisible() {
        mainFrame.setVisible(true);
    }
}