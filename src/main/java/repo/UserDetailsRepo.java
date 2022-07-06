package repo;

import model.entity.UserDetails;

import java.util.UUID;

public interface UserDetailsRepo extends CrudRepository<UserDetails, UUID> {
}
