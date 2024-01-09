import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReadTreeFromFile {
    private DatabaseConnection Connection;
    public void setConnection(DatabaseConnection conn) {
        this.Connection = conn;
    }
    public List<Tree> makeTreeList() throws SQLException {
        List<Tree> treeList = new ArrayList<>();
        List<Node> nodesList = new ArrayList<>();
        try (java.sql.Connection connection = Connection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM TREES")) {
            while (resultSet.next()) {
                int nodeId = resultSet.getInt("id");
                int parentNodeId = resultSet.getInt("parent_id");
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
}
