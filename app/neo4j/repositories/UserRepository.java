package neo4j.repositories;

import neo4j.models.User;
import org.springframework.data.neo4j.repository.GraphRepository;


public interface UserRepository extends GraphRepository<User> {

    User save(User user);

    User findByObjectId(String objectId);

}
