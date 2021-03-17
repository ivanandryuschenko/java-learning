import java.sql.SQLException;

public interface CacheDao {
    void save(Object key, Object value) throws SQLException;
    Object load(Object key) throws SQLException;
}
