package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import neo4j.models.Contact;
import neo4j.models.User;
import neo4j.services.ContactService;
import neo4j.services.Neo4JServiceProvider;
import neo4j.services.UserService;
import neo4jplugin.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Users extends Controller {


    @Transactional
    public Result all() {

        final UserService userService = Neo4JServiceProvider.get().userService;

        List<User> userList = userService.getAllUsers();

        return ok(Json.toJson(userList));
    }

    @Transactional
    public Result count() {

        final UserService userService = Neo4JServiceProvider.get().userService;

        return ok(userService.count()+"");
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result add() {

        final UserService userService = Neo4JServiceProvider.get().userService;

        JsonNode json = request().body().asJson();
        ObjectMapper mapper = new ObjectMapper();

        User user = userService.save(mapper.convertValue(json, User.class));

        return ok(Json.toJson(user));
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result addList() {

        final UserService userService = Neo4JServiceProvider.get().userService;

        JsonNode json = request().body().asJson();

        Iterator<JsonNode> it = json.iterator();
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = new ArrayList<>();

        while (it.hasNext()) {

            JsonNode next =  it.next();
            User user = userService.save(mapper.convertValue(next, User.class));
            users.add(user);
        }


        return ok(Json.toJson(users));
    }

    @Transactional
    public Result findByObjectId(String objectId) {

        final UserService userService = Neo4JServiceProvider.get().userService;

        User user = userService.find(objectId);

        if (user == null) {

            return notFound();

        } else {

            return ok(Json.toJson(user));
        }

    }

    @Transactional
    public Result knows(String objectId) {

        final UserService userService = Neo4JServiceProvider.get().userService;

        List<Contact> contacts = userService.knows(objectId);

        if (contacts == null) {

            return notFound();

        } else {

            return ok(Json.toJson(userService.knows(objectId)));
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result addKnown(String objectId) {

        final UserService userService = Neo4JServiceProvider.get().userService;
        final ContactService contactService = Neo4JServiceProvider.get().contactService;

        JsonNode json = request().body().asJson();
        ObjectMapper mapper = new ObjectMapper();

        Contact contact = mapper.convertValue(json, Contact.class);
        contact = contactService.find(contact.objectId);

        if (contact == null) {

            contact = contactService.save(mapper.convertValue(json, Contact.class));
        }

        User user = userService.addKnown(objectId, contact);

        if (user == null) {

            return notFound();

        } else {

            return ok(Json.toJson(user));
        }
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result addKnownList(String objectId) {

        final UserService userService = Neo4JServiceProvider.get().userService;
        final ContactService contactService = Neo4JServiceProvider.get().contactService;

        User user = userService.find(objectId);

        if(user == null) {

            return notFound();

        } else {

            JsonNode json = request().body().asJson();
            ObjectMapper mapper = new ObjectMapper();
            Iterator<JsonNode> it = json.elements();

            while (it.hasNext()) {

                JsonNode next = it.next();


                Contact contact = mapper.convertValue(next, Contact.class);
                contact = contactService.find(contact.objectId);
                user = userService.addKnown(objectId, contact);
            }

            return ok(Json.toJson(user));
        }
    }
}
