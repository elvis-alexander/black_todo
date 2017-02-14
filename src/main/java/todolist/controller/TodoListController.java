package todolist.controller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.appengine.repackaged.com.google.api.client.json.jackson.JacksonFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todolist.model.TodoList;
import todolist.model.TodoListRow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * Created by elvis on 2/6/17.
 */

@Controller
@SessionAttributes("userId")
@RequestMapping("")
public class TodoListController {
    private static final JacksonFactory jacksonFactory = new JacksonFactory();

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public void signIn(HttpServletRequest request, @RequestParam("idtoken") String idTokenString, HttpServletResponse resp) throws Exception {
        // signing user in
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(UrlFetchTransport.getDefaultInstance(), jacksonFactory)
                .setAudience(Collections.singletonList("389959298924-dr331r7s8m681dr2h5n3p7vg6ju1kkk2.apps.googleusercontent.com"))
                .build();

        GoogleIdToken.Payload payload = verifier.verify(idTokenString).getPayload();

        String userId = payload.getSubject();
        /* set userId as session */
        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);

        System.out.println("SET USERID: " + userId + " session val: " + session.getAttribute("userId"));

        /* save to data store */
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Entity user = new Entity("User", userId);
        user.setProperty("firstName",  (String) payload.get("given_name"));
        user.setProperty("lastName", (String)payload.get("family_name"));
        user.setProperty("userId", userId);
        ds.put(user);
    }

    @RequestMapping(value = "/signout", method = RequestMethod.POST)
    public void singOut(HttpServletRequest request) throws Exception {
        request.getSession().removeAttribute("userId");
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddTodoList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("userId") == null)
            return "index";

        return "add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addTodoList(HttpServletRequest request, @RequestBody TodoList todoList) throws Exception {
        HttpSession session = request.getSession();
        if(session.getAttribute("userId") == null)
            return "index";
        /* retrieve datastore */
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("User");
        String userId = (String)session.getAttribute("userId");
        query.setFilter(Query.FilterOperator.EQUAL.of("userId", userId));
        PreparedQuery preparedQuery = ds.prepare(query);
        String ownerName = "default";
        for (Entity todoListEntity : preparedQuery.asIterable()) {
            ownerName = todoListEntity.getProperties().get("firstName") + " " + todoListEntity.getProperties().get("lastName");
        }
        todoList.setOwnerName(ownerName);

        /* save todolist entity */
        Entity todoListEntity = new Entity("TodoList");
        todoListEntity.setProperty("privateTodo", todoList.isPrivateTodo());
        todoListEntity.setProperty("name", todoList.getName());
        todoListEntity.setProperty("ownerName", todoList.getOwnerName());
        todoListEntity.setProperty("ownerId", request.getSession().getAttribute("userId"));
        ds.put(todoListEntity);

        /* retrieve todolist key */
        Key todoListKey = todoListEntity.getKey();
        todoListEntity.setProperty("id", todoListKey);
        ds.put(todoListEntity);
        /* save row's entities */
        for(int i = 0; i < todoList.getRows().size(); ++i) {
            TodoListRow row = todoList.getRows().get(i);
            Entity rowEntity = new Entity("TodoListRow");
            rowEntity.setProperty("category", row.getCategory());
            rowEntity.setProperty("todoListId", todoListKey);
            rowEntity.setProperty("level", (long)i);
            rowEntity.setProperty("description", row.getDescription());
            rowEntity.setProperty("completed", row.isCompleted());
            rowEntity.setProperty("start", row.getStart());
            rowEntity.setProperty("end", row.getEnd());
            ds.put(rowEntity);
        }
        return "success";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String postEdit(HttpServletRequest req, @RequestBody TodoList todoList) {
        HttpSession session = req.getSession();
        if(session.getAttribute("userId") == null)
            return "index";

        System.out.println("=>posting edit");
        // work from here, start deleting all todolistrows with given id and add
        String todoId = todoList.getId().toString().replace("(no-id-yet)", "");
        System.out.println("todoList:" + todoId);
        String userId = (String) session.getAttribute("userId");
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("TodoList");
        query.setFilter(Query.FilterOperator.EQUAL.of("userId", userId));
        PreparedQuery preparedQuery = ds.prepare(query);
        /* get all todolist from current user */
        for (Entity todoListEntity : preparedQuery.asIterable()) {
            Key todoListKey = todoListEntity.getKey();
            System.out.println("todolistKey: " + todoListKey);
            System.out.println("todolistKey: " + todoListKey.getId());
            if(todoListKey.toString().equals(todoId)) {
                System.out.println("+> found todoList!");
                todoListEntity.setProperty("name", todoList.getName());
                todoListEntity.setProperty("privateTodo", todoList.isPrivateTodo());
                /*var curr_row = 0;
                for(loop through all todoListRows with todoListId of bleh) {
                    entityTodoListRow.setProperty("prop", todoList.foo());
                    ++curr_row;
                }                 */
                query = new Query("TodoListRow");
                query.setFilter(Query.FilterOperator.EQUAL.of("todoListId", todoListKey));
                preparedQuery = ds.prepare(query);
                int rowIndex = 0;
                for (Entity todoListRowEntity : preparedQuery.asIterable()) {
                    ds.delete(todoListRowEntity.getKey());
                }
                /* save row's entities */
                for(int i = 0; i < todoList.getRows().size(); ++i) {
                    TodoListRow row;
                    row = todoList.getRows().get(i);
                    Entity rowEntity = new Entity("TodoListRow");
                    rowEntity.setProperty("todoListId", todoListKey);
                    rowEntity.setProperty("level", row.getLevel());
                    rowEntity.setProperty("category", row.getCategory());
                    rowEntity.setProperty("description", row.getDescription());
                    rowEntity.setProperty("completed", row.isCompleted());
                    rowEntity.setProperty("start", row.getStart());
                    rowEntity.setProperty("end", row.getEnd());
                    ds.put(rowEntity);
                }
                ds.put(todoListEntity);
            }
        }
        return "successedit";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String successView(HttpServletRequest request) {
        return "success";
    }

    @RequestMapping(value = "/successedit", method = RequestMethod.GET)
    public String successEditView(HttpServletRequest request) {
        return "successedit";
    }


}
