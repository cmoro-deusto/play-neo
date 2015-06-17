package neo4j.models;


import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.data.neo4j.annotation.*;

import java.util.Date;
import java.util.Set;


@NodeEntity
public class User extends AbstractNode {

    @Indexed(unique = true)
    public String objectId;

    public String createdAt;
    
    public String updatedAt;
    
    public String authData;

    @Indexed
    public String username;
    
    public String signUpDate;

    @Indexed
    public String name;

    public String country;

    public String phone;

    public String nationalPhone;

    public String bcryptPassword;

    public String email;

    public String emailDomain;

    public String emailVerified;

    public String isActive;

    public String signUpEventGenerated;
    
    public String photo;

    public Date lastSyncDate;

    public Integer totalSyncContacts;

    public String totalSyncPhones;

    public String totalSyncEmails;

    public String totalSyncKeys;

    public String friendsByPhone;

    public String friendsByEmail;
    
    public String friendsByKeys;

    public String totalFriends;
    
    public String listedByPhone;
    
    public String listedByEmail;

    public String listedByKeys;

    public String totalListed;

    public String processingError;

    public String cancellationDate;

    public String isCancelled;


    public User() {
    }

    @Fetch
    @RelatedTo(type = "KNOWS", direction = Direction.OUTGOING)
    public Set<Contact> knows;

    public User(String username, String name) {

        this.username = username;
        this.name = name;
    }

    public void knows(Contact contact) {

        knows.add(contact);
    }

    public enum RelTypes implements RelationshipType {

        KNOWS
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
