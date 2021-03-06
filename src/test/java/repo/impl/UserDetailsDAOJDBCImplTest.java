package repo.impl;

import config.DataBaseConfig;
import model.entity.UserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repo.UserDetailsRepo;
import repo.impl.jdbc.UserDetailsDAOJDBCImpl;
import util.Queries;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

class UserDetailsDAOJDBCImplTest {
    private UserDetailsRepo repo;

    private UserDetails userDetails;

    @BeforeEach
    void init() {
        repo = new UserDetailsDAOJDBCImpl();

        try (PreparedStatement statement = DataBaseConfig.getConnection().prepareStatement(Queries.INIT_QUERY)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        userDetails = new UserDetails.Builder()
                .setFirstName("Alex")
                .setLastName("G")
                .setPassportNum(12345)
                .build();
    }

    @Test
    void create() {
        UserDetails fromBase = repo.create(userDetails);

        Assertions.assertEquals(userDetails, fromBase);
    }

    @Test
    void read() {
        UUID id = repo.create(userDetails).getId();

        Assertions.assertNotNull(repo.read(id));
    }

    @Test
    void findAll() {
        repo.create(userDetails);

        List<UserDetails> userDetailsList = repo.findAll();

        Assertions.assertNotEquals(userDetailsList.size(), 0);
    }

    @Test
    void update() {
        UUID id = repo.create(userDetails).getId();

        userDetails.setId(id);
        userDetails.setFirstName("Axel");

        Assertions.assertEquals(repo.update(userDetails).getFirstName(), "Axel");
    }

    @Test
    void delete() {
        UUID id = repo.create(userDetails).getId();

        repo.delete(id);
        Assertions.assertNull(repo.read(id));
    }

    @Test
    void deleteAll() {
        repo.create(userDetails);
        repo.deleteAll();

        List<UserDetails> userDetailsList = repo.findAll();

        Assertions.assertEquals(userDetailsList.size(), 0);
    }
}