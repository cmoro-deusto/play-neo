package neo4j.repositories;

import neo4j.models.Contact;
import org.springframework.data.neo4j.repository.GraphRepository;


public interface ContactRepository extends GraphRepository<Contact> {

    Contact findByObjectId(String objectId);

}
