package service.impl;

import mapper.CustomMapper;
import model.dto.UserDetailsDTO;
import model.entity.UserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repo.UserDetailsRepo;
import repo.impl.jdbc.UserDetailsDAOJDBCImpl;
import service.UserDetailsService;
import util.Queries;

import java.sql.PreparedStatement;
import java.sql.SQLException;

class UserDetailsServiceImplTest {
    private UserDetailsService userDetailsService;
    private UserDetailsRepo repo;

    private UserDetails userDetails;
    private UserDetailsDTO userDetailsDTO;

    @BeforeEach
    void init() {
        repo = new UserDetailsDAOJDBCImpl();
        userDetailsService = new UserDetailsServiceImpl(repo);

        try (PreparedStatement statement = repo.getConnection().prepareStatement(Queries.INIT_QUERY)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        userDetails = new UserDetails();
        userDetails.setFirstName("Alex");
        userDetails.setLastName("G");
        userDetails.setEmail("place_holder@mail.net");
        userDetails.setPassportNum(12345);

        userDetailsDTO = CustomMapper.toDTO(userDetails);
    }

    @Test
    void create() {


    }

    @Test
    void read() {

    }

    @Test
    void update() {

    }

    @Test
    void delete() {

    }
}