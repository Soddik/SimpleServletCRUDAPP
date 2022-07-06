package repo;

import model.entity.User;

import java.util.UUID;

public interface UserRepo extends CrudRepository<User, UUID> {
}
