import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.io.IOException;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        DatabaseConnection db = new H2Connection();
        ReadTreeFromFile reader = new ReadTreeFromFile();
        reader.setConnection(db);
        List<Tree> treeList = reader.makeTreeList();
        int leafCounter = 0;
        for (Tree tree : treeList) {
            leafCounter += tree.getAllLeaves().size();
        }
        try {
            String outputFile = "output.csv";
            try (PrintWriter writer = new PrintWriter(outputFile)) {
                writer.println(String.valueOf(leafCounter));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
