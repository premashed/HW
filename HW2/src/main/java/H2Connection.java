import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Connection implements DatabaseConnection {
    private final String user = properties.getProperty("h2_user");
    private final String url = properties.getProperty("h2_url");
    private final String password = properties.getProperty("h2_password");
    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
