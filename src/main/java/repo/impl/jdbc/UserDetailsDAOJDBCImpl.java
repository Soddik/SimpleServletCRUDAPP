package repo.impl.jdbc;

import model.entity.UserDetails;
import repo.UserDetailsRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDetailsDAOJDBCImpl extends UserDetailsRepo {

    @Override
    public UserDetails create(final UserDetails userDetails) {
        try (Connection connection = super.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.INSERT.QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
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
        } finally {
            super.closeConnection();
        }
        return null;
    }

    @Override
    public UserDetails read(UUID id) {
        UserDetails userDetails = null;
        try (Connection connection = super.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.GET.QUERY)) {
            statement.setObject(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                userDetails = new UserDetails();
                userDetails.setId(rs.getObject("id", UUID.class));
                userDetails.setFirstName(rs.getString("first_name"));
                userDetails.setLastName(rs.getString("last_name"));
                userDetails.setPassportNum(rs.getInt("passport_num"));
                userDetails.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            //todo implement logging
            e.printStackTrace();
        } finally {
            super.closeConnection();
        }
        return userDetails;
    }

    @Override
    public List<UserDetails> findAll() {
        List<UserDetails> userDetailsList = null;
        try (Connection connection = super.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.GET_ALL.QUERY)) {
            ResultSet rs = statement.executeQuery();
            userDetailsList = new ArrayList<>();
            while (rs.next()) {
                UserDetails userDetails = new UserDetails();
                userDetails.setId(rs.getObject("id", UUID.class));
                userDetails.setFirstName(rs.getString("first_name"));
                userDetails.setLastName(rs.getString("last_name"));
                userDetails.setPassportNum(rs.getInt("passport_num"));
                userDetails.setEmail(rs.getString("email"));

                userDetailsList.add(userDetails);
            }
        } catch (SQLException e) {
            //todo implement logging
            e.printStackTrace();
        } finally {
            super.closeConnection();
        }
        return userDetailsList;
    }

    @Override
    public UserDetails update(UserDetails userDetails) {
        if (read(userDetails.getId()) != null) {
            if (!read(userDetails.getId()).equals(userDetails)) {
                try (Connection connection = super.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.UPDATE.QUERY)) {
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
                } finally {
                    super.closeConnection();
                }
            }
        }
        return null;
    }

    @Override
    public void delete(UUID id) {
        if (read(id) != null) {
            try (Connection connection = super.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.DELETE.QUERY)) {
                statement.setObject(1, id);
                statement.execute();
            } catch (SQLException e) {
                //TODO implement logger
                e.printStackTrace();
            } finally {
                super.closeConnection();
            }
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = super.getConnection(); PreparedStatement statement = connection.prepareStatement(SQL.DELETE_ALL.QUERY)) {
            statement.execute();
        } catch (SQLException e) {
            //TODO implement logger
            e.printStackTrace();
        } finally {
            super.closeConnection();
        }
    }

    private enum SQL {
        GET("SELECT * FROM user_details WHERE id = (?);"),
        GET_ALL("SELECT * FROM user_details;"),
        INSERT("INSERT INTO user_details (id, first_name, last_name, passport_num, email) VALUES (uuid_generate_v4(),(?),(?),(?),(?)) RETURNING id;"),
        DELETE("DELETE FROM user_details WHERE id = (?);"),
        DELETE_ALL("TRUNCATE user_details CASCADE;"),
        UPDATE("UPDATE user_details SET first_name = (?), last_name = (?), passport_num = (?), email = (?) WHERE id = (?) RETURNING id;");

        private final String QUERY;

        SQL(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
