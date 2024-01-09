import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PSQLConnection implements DatabaseConnection {
    private final String user = properties.getProperty("psql_user");
    private final String url = properties.getProperty("psql_url");
    private final String password = properties.getProperty("psql_password");
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
