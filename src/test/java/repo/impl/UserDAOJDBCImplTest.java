package repo.impl;

import model.entity.User;
import model.entity.UserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repo.UserDetailsRepo;
import repo.UserRepo;
import util.Queries;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

class UserDAOJDBCImplTest {
    private UserRepo userRepo;

    private UserDetailsRepo userDetailsRepo;

    private UserDetails userDetails;
    private User user;

    @BeforeEach
    void init() {
        userRepo = new UserDAOJDBCImpl();
        userDetailsRepo = new UserDetailsDAOJDBCImpl();

        try (PreparedStatement statement = userDetailsRepo.getConnection().prepareStatement(Queries.INIT_QUERY)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        userDetails = new UserDetails();
        userDetails.setFirstName("Alex");
        userDetails.setLastName("G");
        userDetails.setEmail("place_holder@mail.net");
        userDetails.setPassportNum(12345);

        user = new User();
        user.setLogin("SOD");
        user.setPassword("qwerty");
        user.setUserDetails(userDetails);
    }

    @Test
    void create() {
        UUID detailsID = userDetailsRepo.create(userDetails).getId();
        user.getUserDetails().setId(detailsID);


        User fromBase = userRepo.create(user);
        user.setId(fromBase.getId());

        System.out.println("handmade: " + user);
        System.out.println("frombase: " + fromBase);
    }

    @Test
    void read() {
        UUID detailsID = userDetailsRepo.create(userDetails).getId();
        user.getUserDetails().setId(detailsID);

        UUID id = userRepo.create(user).getId();

        Assertions.assertNotNull(userRepo.read(id));
    }

    @Test
    void findAll() {
        UUID detailsID = userDetailsRepo.create(userDetails).getId();
        user.getUserDetails().setId(detailsID);

        userRepo.create(user);

        System.out.println(userRepo.findAll());
    }

    @Test
    void update() {
        UUID detailsID = userDetailsRepo.create(userDetails).getId();
        user.getUserDetails().setId(detailsID);

        System.out.println("BEFORE");
        System.out.println(userRepo.create(user));

        userDetails.setEmail("OMG@NET.NET");
        user.setPassword("******");

        System.out.println("AFTER");
        System.out.println(userRepo.update(user));
    }

    @Test
    void delete() {
    }

    @Test
    void deleteAll() {
    }
}