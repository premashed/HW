import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public interface DatabaseConnection {
    Connection getConnection() throws SQLException;
    Properties properties = DatabaseProperties.loadProperties("database.properties");

}