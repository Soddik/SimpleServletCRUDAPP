package repo;

import model.entity.User;

import java.util.UUID;

public abstract class UserRepo extends CrudRepository<User, UUID> {
}
