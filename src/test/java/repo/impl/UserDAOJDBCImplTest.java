package repo.impl;

import config.DataBaseConfig;
import model.entity.User;
import model.entity.UserDetails;
import org.junit.jupiter.api.*;
import repo.UserDetailsRepo;
import repo.UserRepo;
import repo.impl.jdbc.UserDAOJDBCImpl;
import repo.impl.jdbc.UserDetailsDAOJDBCImpl;
import util.Queries;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

class UserDAOJDBCImplTest {
    private static UserRepo userRepo;

    private static UserDetailsRepo userDetailsRepo;

    private static UserDetails userDetails;
    private static User user;
    private static UUID userId;

    @BeforeAll
    static void init() {
        userRepo = new UserDAOJDBCImpl();
        userDetailsRepo = new UserDetailsDAOJDBCImpl();

        try (PreparedStatement statement = DataBaseConfig.getConnection().prepareStatement(Queries.INIT_QUERY)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        userDetails = new UserDetails.Builder()
                .setFirstName("Alex")
                .setLastName("G")
                .setPassportNum(12345)
                .setEmail("placeholder@mail.com")
                .build();

        user = new User.Builder()
                .setLogin("SOD")
                .setPassword("qwerty")
                .setUserDetails(userDetails)
                .build();
    }

    @Test
    void create() {
        System.out.println("====Create====");

        User fromBase = userRepo.create(user);
        userId = fromBase.getId();
        user.setId(userId);

        System.out.println("handmade: " + user);
        System.out.println("frombase: " + fromBase);
    }

    @Test
    void read() {
        System.out.println("====Read====");
        System.out.println(userRepo.read(userId));
        Assertions.assertNotNull(userRepo.read(userId));
    }

    @Test
    void findAll() {
        System.out.println("====Find all====");
        System.out.println(userRepo.findAll());
    }

    @Test
    void update() {
        System.out.println("====UPDATE====");
        System.out.println("====BEFORE====");
        System.out.println(userRepo.read(userId));

        user.setPassword("******");

        System.out.println("====AFTER====");
        System.out.println(userRepo.update(user));
    }

    @Test
    void delete() {
        System.out.println("====DELETE====");
       // System.out.println(userRepo.delete(userId));
    }

    @Test
    void deleteAll() {
    }
}