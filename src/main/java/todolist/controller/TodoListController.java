package todolist.controller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.appengine.repackaged.com.google.api.client.json.jackson.JacksonFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import todolist.model.TodoList;
import todolist.model.TodoListRow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by elvis on 2/6/17.
 */

@Controller
@RequestMapping("/todolist")
public class TodoListController {
    private static final JacksonFactory jacksonFactory = new JacksonFactory();
    private static final Logger logger = Logger.getLogger(TodoList.class);


    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public void signIn(HttpServletRequest request, @RequestParam("idtoken") String idTokenString, HttpServletResponse resp) throws Exception {
        // signing user in
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(UrlFetchTransport.getDefaultInstance(), jacksonFactory)
                .setAudience(Collections.singletonList("525024588682-7l84ocjn11k8t9j2n34hgaidieu9vtig.apps.googleusercontent.com"))
                .build();
        GoogleIdToken.Payload payload = verifier.verify(idTokenString).getPayload();
        String userId = payload.getSubject();
        /* set userId as session */
        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);

        /* save to data store */
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Entity user = new Entity("User", userId);
        user.setProperty("firstName",  (String) payload.get("given_name"));
        user.setProperty("lastName", (String)payload.get("family_name"));
        ds.put(user);
    }

    @RequestMapping(value = "/browse", method = RequestMethod.GET)
    public String getBrowseView() {
        return "browse";
    }

    @RequestMapping(value = "/mylists", method = RequestMethod.GET)
    public String myLists(HttpServletRequest request, Model model) {
        String userId = (String) request.getSession().getAttribute("userId");
        System.out.println("=> " + userId);
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("TodoList");
        query.setFilter(Query.FilterOperator.EQUAL.of("userId", userId));
        PreparedQuery preparedQuery = ds.prepare(query);
        /* get all todolist from current user */
        ArrayList<TodoList> listOfTodos = new ArrayList<>();
        for (Entity todoListEntity : preparedQuery.asIterable()) {
            Key todoListKey = KeyFactory.createKey("TodoList", todoListEntity.getKey().getId());
            Map<String, Object> properties = todoListEntity.getProperties();
            TodoList currTodo = new TodoList();
            currTodo.setPrivateTodo((Boolean) properties.get("privateTodo"));

            query = new Query("TodoListRow");
            query.setFilter(Query.FilterOperator.EQUAL.of("todoListId", todoListKey));
            preparedQuery = ds.prepare(query);
            for(Entity todoListRow : preparedQuery.asIterable()) {
                System.out.println("=>" + todoListRow);
            }

            listOfTodos.add(currTodo);
        }
        model.addAttribute("todoList", listOfTodos);


        return "mylists";
    }



    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddTodoList() {
        return "add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveNewTodoList(HttpServletRequest request, @RequestBody TodoList todoList) throws Exception {
        /* retrieve datastore */
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        /* save todolist entity */
        Entity todoListEntity = new Entity("TodoList");
        todoListEntity.setProperty("privateTodo", todoList.isPrivateTodo());
        todoListEntity.setProperty("userId", request.getSession().getAttribute("userId"));
        ds.put(todoListEntity);
        /* retrieve todolist key */
        Key todoListKey = KeyFactory.createKey("TodoList", todoListEntity.getKey().getId());
        /* save row's entities */
        for(int i = 0; i < todoList.getRows().size(); ++i) {
            TodoListRow row = todoList.getRows().get(i);
            Entity rowEntity = new Entity("TodoListRow");
            rowEntity.setProperty("level", row.getLevel());
            rowEntity.setProperty("category", row.getCategory());
            rowEntity.setProperty("description", row.getDescription());
            rowEntity.setProperty("completed", row.isCompleted());
            rowEntity.setProperty("start", row.getStart());
            rowEntity.setProperty("end", row.getEnd());
            rowEntity.setProperty("todoListId", todoListKey);
            ds.put(rowEntity);
        }
        return "add";
    }
}

