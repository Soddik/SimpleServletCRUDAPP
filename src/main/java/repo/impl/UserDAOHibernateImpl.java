package repo.impl;

import model.entity.UserDetails;
import repo.UserDetailsRepo;

import java.util.List;
import java.util.UUID;

public class UserDAOHibernateImpl extends UserDetailsRepo {
    @Override
    public UserDetails create(UserDetails userDetails) {
        return null;
    }

    @Override
    public UserDetails read(UUID uuid) {
        return null;
    }

    @Override
    public List<UserDetails> findAll() {
        return null;
    }

    @Override
    public UserDetails update(UserDetails userDetails) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public void deleteAll() {

    }
}
