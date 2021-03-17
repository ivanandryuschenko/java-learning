import java.io.*;
import java.sql.*;

public class CacheDaoImpl implements CacheDao {

    private static final String INSERT_OBJ_SQL = "insert into cache (key, value) values(?, ?)";
    private static final String SELECT_OBJ_SQL = "select * from cache where key = ?";

    private DataBase db;

    public CacheDaoImpl(DataBase db) {
        this.db = db;
    }

    public void save(Object key, Object value) throws SQLException {
        try (PreparedStatement statement = db.connection().prepareStatement(INSERT_OBJ_SQL)) {
            statement.setBytes(1, serialize(key));
            statement.setBytes(2, serialize(value));
            statement.execute();
        } catch (SQLException | IOException e) {
            throw new SQLException();
        }
    }

    public Object load(Object key) throws SQLException {
        try (PreparedStatement statement = db.connection().prepareStatement(SELECT_OBJ_SQL)) {
            statement.setBytes(1, serialize(key));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return deserialize(rs.getBytes(2));
            } else {
                throw new SQLException();
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new SQLException();
        }
    }

    private byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.close();
        return baos.toByteArray();
    }

    private Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object object = ois.readObject();
        ois.close();
        return object;
    }
}
