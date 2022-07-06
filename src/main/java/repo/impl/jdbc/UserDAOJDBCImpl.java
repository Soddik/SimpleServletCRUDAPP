package repo.impl.jdbc;

import config.DataBaseConfig;
import exception.EntityNotFoundException;
import model.entity.User;
import repo.UserDetailsRepo;
import repo.UserRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDAOJDBCImpl implements UserRepo {

    private final UserDetailsRepo userDetailsRepo;

    public UserDAOJDBCImpl() {
        this.userDetailsRepo = new UserDetailsDAOJDBCImpl();
    }

    @Override
    public User create(User user) {
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.INSERT.QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setObject(3, userDetailsRepo.create(user.getUserDetails()).getId());

            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                user.setId(rs.getObject("id", UUID.class));
                return user;
            }
        } catch (SQLException e) {
            //todo implement logging
            e.printStackTrace();
        }
        throw new EntityNotFoundException(String.format("Entity %s not created", User.class.getSimpleName()));
    }

    @Override
    public User read(UUID id) {
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.GET.QUERY)) {
            statement.setObject(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return createUser(rs);
            }
        } catch (SQLException e) {
            //todo implement logging
            e.printStackTrace();
        }
        throw new EntityNotFoundException(String.format("Entity %s with id: %s not found", User.class.getSimpleName(), id));
    }

    @Override
    public List<User> findAll() {
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.GET_ALL.QUERY)) {
            ResultSet rs = statement.executeQuery();
            List<User> userList = userList = new ArrayList<>();

            while (rs.next()) {
                userList.add(createUser(rs));
            }

            return userList;

        } catch (SQLException e) {
            //todo implement logging
            e.printStackTrace();
        }
        throw new EntityNotFoundException(String.format("Entities %s not found", User.class.getSimpleName()));
    }

    @Override
    public User update(User user) {
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.UPDATE.QUERY)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setObject(3, user.getId());

            statement.execute();

            User fromBase = read(user.getId());
            fromBase.setUserDetails(userDetailsRepo.update(user.getUserDetails()));

            return fromBase;
        } catch (SQLException e) {
            //TODO implement logger
            e.printStackTrace();
        }

        throw new EntityNotFoundException(String.format("Entity %s with id: %s not found", User.class.getSimpleName(), user.getId()));
    }

    @Override
    public Boolean delete(UUID id) {
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.DELETE.QUERY)) {
            statement.setObject(1, id);
            return statement.execute();
        } catch (SQLException e) {
            //TODO implement logger
            e.printStackTrace();
        }

        throw new EntityNotFoundException(String.format("Entity %s with id: %s not found", User.class.getSimpleName(), id));
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

    private User createUser(ResultSet rs) throws SQLException {
        return new User.Builder()
                .setId(rs.getObject("id", UUID.class))
                .setLogin(rs.getString("login"))
                .setPassword(rs.getString("password"))
                .setUserDetails(userDetailsRepo.read(rs.getObject("user_details_id", UUID.class)))
                .build();
    }

    private enum SQL {
        GET("SELECT * FROM users WHERE id = (?)"),
        GET_ALL("SELECT * FROM users"),
        INSERT("INSERT INTO users(id, login, password, user_details_id) VALUES(uuid_generate_v4(),(?),(?),(?)) RETURNING id"),
        DELETE("DELETE FROM users WHERE id = (?)"),
        DELETE_ALL("TRUNCATE user CASCADE;"),
        UPDATE("UPDATE users SET login = (?), password = (?) WHERE id = (?) RETURNING id");

        private final String QUERY;

        String getQUERY() {
            return QUERY;
        }

        SQL(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
