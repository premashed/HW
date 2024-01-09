import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {

    private static List<Tree> makeTreeFromFile(String filePath) throws IOException {
        List<Tree> treeList = new ArrayList<>();
        List<Node> nodesList = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine().replaceAll("\"", "");
                String[] values = line.split(",");
                int nodeId = Integer.parseInt(values[0]);
                int parentNodeId = Integer.parseInt(values[1]);
                Node node = new Node(nodeId);
                node.setParentId(parentNodeId);
                nodesList.add(node);
            }
            for (Node node : nodesList) {
                if (node.getId()==node.getParentId()) {
                    addChildren(nodesList, node);
                    Tree tree = new Tree(node);
                    treeList.add(tree);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        return treeList;
    }
    private static void addChildren(List<Node> nodesList, Node currentNode) {
        int currentNodeId = currentNode.getId();
        List<Node> childNodes = findChildNodes(nodesList, currentNodeId);
        if (!childNodes.isEmpty()) {
            for (Node childNode : childNodes) {
                currentNode.addChild(childNode);
                addChildren(nodesList, childNode);
            }
        }
    }

    private static List<Node> findChildNodes(List<Node> nodesList, int parentNodeId) {
        List<Node> childNodes = new ArrayList<>();
        for (Node currentNode : nodesList) {
            if (currentNode.getParentId() == parentNodeId && currentNode.getId() !=parentNodeId) {
                childNodes.add(currentNode);
            }
        }
        return childNodes;
    }

    private static Tree findTreeWithMaxLeaves(List<Tree> trees) {
        List<Tree> treeWithMaxLeaves = new ArrayList<>();
        int maxLeafCount = 0;
        // использование списка чтоб не упустить деревья
        for (Tree tree : trees) {
            int leafCount = tree.getAllLeaves().size();
            if (leafCount > maxLeafCount) {
                maxLeafCount = leafCount;
                treeWithMaxLeaves.clear();
                treeWithMaxLeaves.add(tree);
            } else if (leafCount == maxLeafCount) {
                treeWithMaxLeaves.add(tree);
            }
        }
        try {
            if (treeWithMaxLeaves.size() > 1) { // исключение в случае нахождения нескольких деревьев
                throw new IllegalStateException("Несколько деревьев с максимальным количеством листьев!");
            } else {
                return treeWithMaxLeaves.get(0);
            }
        } catch (IllegalStateException er){
            er.printStackTrace();
            try {
                String outputFile = "output.csv";
                try (PrintWriter writer = new PrintWriter(outputFile)) {
                    writer.println("0,0");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(2);
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            String inputFile = "input.csv";
            String outputFile = "output.csv";

            List<Tree> trees = makeTreeFromFile(inputFile);
            Tree treeWithMaxLeaves = findTreeWithMaxLeaves(trees);
            if (findTreeWithMaxLeaves(trees) != null) {
                int maxLeafId = treeWithMaxLeaves.getRoot().getId();
                int maxLeafCount = treeWithMaxLeaves.getAllLeaves().size();

                try (PrintWriter writer = new PrintWriter(outputFile)) {
                    writer.println(maxLeafId + "," + maxLeafCount);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}