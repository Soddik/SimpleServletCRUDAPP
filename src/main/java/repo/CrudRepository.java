package repo;

import java.util.List;

public interface CrudRepository<Entity, ID> {

    Entity create(Entity entity);

    Entity read(ID id);

    List<Entity> findAll();

    Entity update(Entity entity);

    void delete(ID id);

    void deleteAll();
}
