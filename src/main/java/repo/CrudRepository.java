package repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public abstract class CrudRepository<Entity, ID> {
    private Connection connection;

    protected Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String dbUser = "postgres";
        String password = "1234";

        return DriverManager.getConnection(url, dbUser, password);
    }

    protected void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                //todo implement logger
            }
        }
    }

    public abstract Entity create(Entity entity);

    public abstract Entity read(ID id);

    public abstract List<Entity> findAll();

    public abstract Entity update(Entity entity);

    public abstract void delete(ID id);

    public abstract void deleteAll();
}
