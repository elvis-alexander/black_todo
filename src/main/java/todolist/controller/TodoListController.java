package todolist.controller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.appengine.repackaged.com.google.api.client.json.jackson.JacksonFactory;
import com.googlecode.objectify.ObjectifyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import todolist.model.TodoList;
import todolist.model.User;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Created by elvis on 2/6/17.
 */



@Controller
@RequestMapping("/todolist")
public class TodoListController {
    private static final JacksonFactory jacksonFactory = new JacksonFactory();
    private static final Logger logger = Logger.getLogger(TodoList.class);


    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public void signIn(@RequestParam("idtoken") String idTokenString, HttpServletResponse resp) throws Exception {

        // signing user in
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(UrlFetchTransport.getDefaultInstance(), jacksonFactory)
                .setAudience(Collections.singletonList("525024588682-7l84ocjn11k8t9j2n34hgaidieu9vtig.apps.googleusercontent.com"))
                .build();
        GoogleIdToken.Payload payload = verifier.verify(idTokenString).getPayload();
        // Print user identifier
        String userId = payload.getSubject();
        List<User> users = ObjectifyService.ofy().load().type(User.class).list();
        boolean exists = false;
        for (User user : users) {
            if (user.getId().equals(userId)) exists = true;
        }
        if(!exists) {
            // save new user
            System.out.println("-> saving new user");
            User user = new User(userId, (String) payload.get("given_name"), (String) payload.get("family_name"));
            ObjectifyService.ofy().save().entity(user).now();
        } else System.out.println("-> user already exists");
        System.out.println("userId -> " + userId);
        // @TODO save their session
    }

    @RequestMapping(value = "/browse", method = RequestMethod.GET)
    public String getBrowseView() {
        return "browse";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddTodoList() {
        return "create";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveNewTodoList(@RequestBody TodoList todoList) {
        System.out.println("->" + todoList);
        /* save to data store */

        Entity entityTodoList = new Entity("TodoList");
        entityTodoList.setProperty("privateTodo", todoList.isPrivateTodo());
        // entityTodoList.setProperty("rows", todoList.getRows());
        ObjectifyService.register(TodoList.class);
        ObjectifyService.ofy().save().entity(entityTodoList).now();
        // @Todo redirect to home page (view all public todo list)
        return "create";
    }


}

