package org.example.client;


import java.util.ArrayList;
import java.util.List;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;


public class TreeClient extends Application {
    final TextField deleteIndex;
    final TextField childIndex;
    final TextField parentIndex;
    final TextField newTreeIndex;
    final TextField child2Index;
    final TextField parent2Index;
    List<Tree> trees;
    private static final String url = "http://localhost:8080/trees";
    public static void main(String[] args) {
        launch();
    }
    public TreeClient() {
        deleteIndex = new TextField();
        childIndex = new TextField();
        parentIndex = new TextField();
        newTreeIndex = new TextField();
        child2Index = new TextField();
        parent2Index = new TextField();
    }
    public void start(Stage primaryStage) {
        read();
        primaryStage.setTitle("Меню деревьев");
        GridPane gridpane = new GridPane();
        Button deleteFunc = new Button("Удалить узел");
        Button addChildFunc = new Button("Добавить ребенка");
        Button addTreeFunc = new Button("Добавить дерево");
        Button viewFunc = new Button("Просмотреть");
        Button readFunc = new Button("Прочитать из БД");
        Button writeFunc = new Button("Записать в БД");
        Button addParentFunc = new Button("Добавить родителя");

        deleteFunc.setOnAction(e ->deleteNode());
        addParentFunc.setOnAction(e ->addParent());
        addChildFunc.setOnAction(e ->addChild());
        addTreeFunc.setOnAction(e ->addTree());
        readFunc.setOnAction(e ->read());
        writeFunc.setOnAction(e ->writeTable());
        viewFunc.setOnAction(e ->openViewer());
        primaryStage.setOnCloseRequest(e -> Platform.exit());

        gridpane.add(viewFunc, 0,0,1,1);
        gridpane.add(readFunc,1, 0, 1, 1);
        gridpane.add(writeFunc, 2, 0, 1, 1);
        gridpane.add(deleteFunc, 0, 1, 1, 1);
        gridpane.add(deleteIndex, 1, 1, 1, 1);
        gridpane.add(addChildFunc, 0, 2, 1, 1);
        gridpane.add(childIndex, 1, 2, 1, 1);
        gridpane.add(parentIndex, 2, 2, 1, 1);
        gridpane.add(addParentFunc, 0, 3, 1, 1);
        gridpane.add(child2Index, 1, 3, 1, 1);
        gridpane.add(parent2Index, 2, 3, 1, 1);
        gridpane.add(addTreeFunc, 0, 4, 1,1 );
        gridpane.add(newTreeIndex, 1, 4, 1,1);

        Scene scene = new Scene(gridpane, 430, 130);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void addTree() {
        int rootIn = Integer.parseInt(newTreeIndex.getText());
        if (!Tree.idInTree(trees, rootIn)) {
            trees.add(new Tree(new Node(rootIn)));
        }
    }
    private void addChild() {
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
    private void read() {
        ResponseEntity<List<TransferNode>> treeList = new RestTemplate().exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        List<TransferNode> li = treeList.getBody();
        assert li != null;
        trees = Tree.assemble(li);
    }
    private void deleteNode() {
        int nodeIn = Integer.parseInt(deleteIndex.getText());
        for (Tree tree : trees) {
            if (tree.deleteNode(nodeIn, trees)) {
                break;
            }
        }
    }
    private void openViewer() {
        TreeViewer viewer = new TreeViewer(trees);
        viewer.show();
    }
    private void writeTable() {
        List<TransferNode> nodesList = new ArrayList<>();
        for (Tree tree : trees) {
            for (Node node : tree.getAllNodes()) {
                int parentId = node.getId();
                if (node.getParent() != null) {
                    parentId = node.getParent().getId();
                }
                nodesList.add(new TransferNode(new Node(node.getId()), parentId));
            }
        }
        new RestTemplate().postForLocation(url, nodesList);
    }
    private void addParent() {
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

}

class TreeViewer extends Stage {
    private BorderPane mainBorderPane;
    private ObservableList<String> listItems;
    private ListView<String> dataList;
    private List<String> buttonNames;
    private List<Tree> trees;

    public TreeViewer(List<Tree> trees) {
        this.trees = trees;
        listItems = FXCollections.observableArrayList();
        buttonNames = new ArrayList<>();
        for (Tree tree : trees) {
            buttonNames.add("Дерево с корнем " + String.valueOf(tree.getRoot().getId()));
        }
        setTitle("Просмотр деревьев");

        mainBorderPane = new BorderPane();

        GridPane buttonGridPane = new GridPane();
        buttonGridPane.setVgap(5);
        buttonGridPane.setPadding(new Insets(10, 10, 10, 10));

        for (String buttonName : buttonNames) {
            Button button = new Button(buttonName);
            button.setOnAction(e -> updateData(buttonName));
            buttonGridPane.add(button, 0, buttonNames.indexOf(buttonName));
        }

        dataList = new ListView<>(listItems);
        mainBorderPane.setLeft(buttonGridPane);
        mainBorderPane.setCenter(new ScrollPane(dataList));

        Scene scene = new Scene(mainBorderPane, 420, 300);
        setScene(scene);

    }

    private void updateData(String buttonName) {
        listItems.clear();
        List<String> data = getData(buttonName);
        listItems.addAll(data);
    }

    private List<String> getData(String buttonName) {
        int index = buttonNames.indexOf(buttonName);
        if (index >= 0 && index < trees.size()) {
            return trees.get(index).treeInfo();
        }
        return new ArrayList<>();
    }

}