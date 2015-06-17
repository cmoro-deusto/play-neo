package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import neo4j.models.Contact;
import neo4j.services.ContactService;
import neo4j.services.Neo4JServiceProvider;
import neo4jplugin.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Contacts extends Controller {

    @Transactional
    public Result all() {

        final ContactService contactService = Neo4JServiceProvider.get().contactService;

        List<Contact> contactList = contactService.getAllContacts();

        return ok(Json.toJson(contactList));
    }

    @Transactional
    public Result findByObjectId(String objectId) {

        final ContactService contactService = Neo4JServiceProvider.get().contactService;

        Contact contact = contactService.find(objectId);

        if (contact == null) {

            return notFound();

        } else {

            return ok(Json.toJson(contact));

        }
    }

    @Transactional
    public Result count() {

        final ContactService contactService = Neo4JServiceProvider.get().contactService;

        return ok(contactService.count()+"");
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result add() {

        final ContactService contactService = Neo4JServiceProvider.get().contactService;

        JsonNode json = request().body().asJson();
        ObjectMapper mapper = new ObjectMapper();

        Contact contact = mapper.convertValue(json, Contact.class);

        contact = contactService.save(contact);

        return ok(Json.toJson(contact));
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result addList() {

        final ContactService contactService = Neo4JServiceProvider.get().contactService;

        JsonNode json = request().body().asJson();
        ObjectMapper mapper = new ObjectMapper();

        Iterator<JsonNode> it = json.elements();
        List<Contact> contactList = new ArrayList<>();

        while (it.hasNext()) {

            JsonNode next = it.next();

            Contact contact = mapper.convertValue(next, Contact.class);
            contactService.save(contact);
            contactList.add(contact);
        }

        return ok(Json.toJson(contactList));

    }

}
