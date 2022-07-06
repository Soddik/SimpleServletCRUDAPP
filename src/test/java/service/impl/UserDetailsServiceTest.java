package service.impl;

import config.DataBaseConfig;
import mapper.UserDetailsMapper;
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

class UserDetailsServiceTest {
    private UserDetailsService userDetailsService;
    private UserDetailsRepo repo;

    private UserDetails userDetails;
    private UserDetailsDTO userDetailsDTO;

    @BeforeEach
    void init() {
        repo = new UserDetailsDAOJDBCImpl();
        userDetailsService = new UserDetailsService(repo);

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

        userDetailsDTO = UserDetailsMapper.toDTO(userDetails);
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