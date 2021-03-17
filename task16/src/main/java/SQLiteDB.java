import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDB implements DataBase {
    @Override
    public Connection connection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:cache.db");
        } catch (ClassNotFoundException e) {
            throw new SQLException();
        }
    }
}
