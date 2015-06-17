package neo4j.services;

import neo4j.models.Contact;
import neo4j.models.User;
import neo4j.repositories.UserRepository;
import neo4jplugin.Transactional;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.kernel.Traversal;
import org.neo4j.kernel.impl.traversal.MonoDirectionalTraversalDescription;
import org.neo4j.shell.kernel.apps.Trav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.nodes.NodeId;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public long count() {

        return userRepository.count();
    }

    public User createUser(String username, String name) {

        return userRepository.save(new User(username, name));
    }

    public User save(User user) {

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return new ArrayList<User>(IteratorUtil.asCollection(userRepository.findAll()));
    }

    public User find(String objectId) {

        return userRepository.findByObjectId(objectId);
    }

    public User findById(Long id) {

        return userRepository.findOne(id);
    }

    public List<Contact> knows(String objectId) {

        User user = this.find(objectId);

        if (user == null) return null;

        return new ArrayList<Contact>(this.find(objectId).knows);
    }

    public User addKnown(String objectId, Contact contact) {

        User user = this.find(objectId);

        if (user == null) { return null; }

        user.knows(contact);
        userRepository.save(user);

        return user;
    }
}
