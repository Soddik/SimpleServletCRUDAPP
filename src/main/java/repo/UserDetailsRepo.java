package repo;

import model.entity.UserDetails;

import java.util.UUID;

public abstract class UserDetailsRepo extends CrudRepository<UserDetails, UUID> {
}
