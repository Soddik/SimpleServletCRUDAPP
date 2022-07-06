package repo.impl.jdbc;

import config.DataBaseConfig;
import exception.EntityNotFoundException;
import model.entity.UserDetails;
import repo.UserDetailsRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDetailsDAOJDBCImpl implements UserDetailsRepo {

    @Override
    public UserDetails create(final UserDetails userDetails) {
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.INSERT.QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, userDetails.getFirstName());
            statement.setString(2, userDetails.getLastName());
            statement.setInt(3, userDetails.getPassportNum());
            statement.setString(4, userDetails.getEmail());

            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                userDetails.setId(rs.getObject("id", UUID.class));
                return userDetails;
            }
        } catch (SQLException e) {
            //todo implement logging
            e.printStackTrace();
        }

        throw new EntityNotFoundException(String.format("Entity %s with id: %s not found", UserDetails.class.getSimpleName(), userDetails.getId()));
    }

    @Override
    public UserDetails read(UUID id) {
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.GET.QUERY)) {
            statement.setObject(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return createUserDetails(rs);
            }
        } catch (SQLException e) {
            //todo implement logging
            e.printStackTrace();
        }

        throw new EntityNotFoundException(String.format("Entity %s with id: %s not found", UserDetails.class.getSimpleName(),id));
    }

    @Override
    public List<UserDetails> findAll() {
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.GET_ALL.QUERY)) {
            ResultSet rs = statement.executeQuery();
            List<UserDetails> userDetailsList = new ArrayList<>();

            while (rs.next()) {
                userDetailsList.add(createUserDetails(rs));
            }
            return userDetailsList;
        } catch (SQLException e) {
            //todo implement logging
            e.printStackTrace();
        }

        throw new EntityNotFoundException(String.format("Entity %s not found", UserDetails.class.getSimpleName()));
    }

    @Override
    public UserDetails update(UserDetails userDetails) {
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.UPDATE.QUERY)) {
            statement.setString(1, userDetails.getFirstName());
            statement.setString(2, userDetails.getLastName());
            statement.setInt(3, userDetails.getPassportNum());
            statement.setString(4, userDetails.getEmail());
            statement.setObject(5, userDetails.getId());

            statement.execute();

            return read(userDetails.getId());
        } catch (SQLException e) {
            //TODO implement logger
            e.printStackTrace();
        }

        throw new EntityNotFoundException(String.format("Entity %s with id: %s not found", UserDetails.class.getSimpleName(), userDetails.getId()));
    }

    @Override
    public Boolean delete(UUID id) {
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.DELETE.QUERY)) {
            statement.setObject(1, id);
            statement.execute();
        } catch (SQLException e) {
            //TODO implement logger
            e.printStackTrace();
        }

        throw new EntityNotFoundException(String.format("Entity %s with id: %s not found", UserDetails.class.getSimpleName(), id));
    }

    @Override
    public void deleteAll() {
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.DELETE_ALL.QUERY)) {
            statement.execute();
        } catch (SQLException e) {
            //TODO implement logger
            e.printStackTrace();
        }
    }

    private enum SQL {
        GET("SELECT * FROM user_details WHERE id = (?);"),
        GET_ALL("SELECT * FROM user_details;"),
        INSERT("INSERT INTO user_details (id, first_name, last_name, passport_num, email) VALUES (uuid_generate_v4(),(?),(?),(?),(?)) RETURNING id;"),
        DELETE("DELETE FROM user_details WHERE id = (?);"),
        DELETE_ALL("TRUNCATE user_details CASCADE;"),
        UPDATE("UPDATE user_details SET first_name = (?), last_name = (?), passport_num = (?), email = (?), modified_at = NOW() WHERE id = (?) RETURNING id;");

        private final String QUERY;

        SQL(String QUERY) {
            this.QUERY = QUERY;
        }
    }

    private UserDetails createUserDetails(ResultSet rs) throws SQLException {
        return new UserDetails.Builder()
                .setId(rs.getObject("id", UUID.class))
                .setFirstName(rs.getString("first_name"))
                .setLastName(rs.getString("last_name"))
                .setPassportNum(rs.getInt("passport_num"))
                .setEmail(rs.getString("email"))
                .setCreatedAt(rs.getTimestamp("created_at"))
                .setModifiedAt(rs.getTimestamp("modified_at"))
                .build();
    }
}
