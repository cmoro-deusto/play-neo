package neo4j.models;

import org.springframework.data.neo4j.annotation.*;

import java.util.ArrayList;
import java.util.List;


@NodeEntity
public class Contact extends AbstractNode {

    @Indexed(unique = true)
    public String objectId;

    public String createdAt;
    
    public String updatedAt;

    public List<String> phones = new ArrayList<>();
    
    public List<String> emails = new ArrayList<>();
    
    public List<String> emailDomains = new ArrayList<>();

    @Indexed
    public String name;

    @Indexed
    public String surname;

    @Indexed
    public String fullName;

    public String country;

    public List<String> addresses = new ArrayList<>();

    public String organization;

    public String job;
    
    public List<String> websites = new ArrayList<>();

    public String ownerObjectId;


    public Contact() {
    }

    public Contact(String name, String surname) {

        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
