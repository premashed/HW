import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseProperties {

    public static Properties loadProperties(String file) {
        Properties properties = new Properties();
        try (InputStream input = DatabaseProperties.class.getClassLoader().getResourceAsStream(file)) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }
}
