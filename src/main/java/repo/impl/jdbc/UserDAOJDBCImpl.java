package repo.impl.jdbc;

import config.DataBaseConfig;
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

public class UserDAOJDBCImpl extends UserRepo {

    private UserDetailsRepo userDetailsRepo;

    public UserDAOJDBCImpl() {
        this.userDetailsRepo = new UserDetailsDAOJDBCImpl();
    }

    @Override
    public User create(User user) {
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.INSERT.QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setObject(3, user.getUserDetails().getId());

            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                user.setId(rs.getObject("id", UUID.class));
                return user;
            }
        } catch (SQLException e) {
            //todo implement logging
            e.printStackTrace();
        } finally {
            DataBaseConfig.closeConnection();
        }
        return null;
    }

    @Override
    public User read(UUID id) {
        User user = null;
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.GET.QUERY)) {
            statement.setObject(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getObject("id", UUID.class));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setUserDetails(userDetailsRepo.read(rs.getObject("id", UUID.class)));
            }
        } catch (SQLException e) {
            //todo implement logging
            e.printStackTrace();
        } finally {
            DataBaseConfig.closeConnection();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> userList = null;
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.GET_ALL.QUERY)) {
            ResultSet rs = statement.executeQuery();
            userList = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getObject("id", UUID.class));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setUserDetails(userDetailsRepo.read(rs.getObject("id", UUID.class)));

                userList.add(user);
            }
        } catch (SQLException e) {
            //todo implement logging
            e.printStackTrace();
        } finally {
            DataBaseConfig.closeConnection();
        }
        return userList;
    }

    @Override
    public User update(User user) {
        if (read(user.getId()) != null) {
            if (!read(user.getId()).equals(user)) {
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
                } finally {
                    DataBaseConfig.closeConnection();
                }
            }
        }
        return null;
    }

    @Override
    public void delete(UUID id) {
        if (read(id) != null) {
            try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.DELETE.QUERY)) {
                statement.setObject(1, id);
                statement.execute();
            } catch (SQLException e) {
                //TODO implement logger
                e.printStackTrace();
            } finally {
                DataBaseConfig.closeConnection();
            }
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = DataBaseConfig.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.DELETE_ALL.QUERY)) {
            statement.execute();
        } catch (SQLException e) {
            //TODO implement logger
            e.printStackTrace();
        } finally {
            DataBaseConfig.closeConnection();
        }
    }

    private enum SQL {
        GET("SELECT * FROM users WHERE id = (?)"),
        GET_ALL("SELECT * FROM users"),
        INSERT("INSERT INTO users(id, login, password, details) VALUES(uuid_generate_v4(),(?),(?),(?)) RETURNING id"),
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
