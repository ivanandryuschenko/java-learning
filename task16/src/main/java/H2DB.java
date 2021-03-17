import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DB implements DataBase {
    public Connection connection() throws SQLException {
        //Class.forName("com.h2database.h2");
        return DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
    }
}
