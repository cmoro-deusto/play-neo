package neo4j.services;

import neo4j.models.Contact;
import neo4j.repositories.ContactRepository;
import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import play.api.libs.iteratee.Cont;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public long count() {

        return contactRepository.count();
    }

    public Contact createContact(String username, String name) {

        return contactRepository.save(new Contact(username, name));
    }

    public List<Contact> getAllContacts() {
        return new ArrayList<>(IteratorUtil.asCollection(contactRepository.findAll()));
    }

    public Contact find(String objectId) {

        return contactRepository.findByObjectId(objectId);
    }

    public Contact save(Contact contact) {

        return contactRepository.save(contact);
    }
}
