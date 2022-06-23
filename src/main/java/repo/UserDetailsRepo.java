package repo;

import model.entity.UserDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public abstract class UserDetailsRepo extends CrudRepository<UserDetails, UUID> {
    public Connection getConnection() throws SQLException {
        return super.getConnection();
    }
}
