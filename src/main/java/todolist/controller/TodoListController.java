package todolist.controller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.appengine.repackaged.com.google.api.client.json.jackson.JacksonFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todolist.model.TodoList;
import todolist.model.TodoListRow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * Created by elvis on 2/6/17.
 */

@Controller
@SessionAttributes("userId")
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

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddTodoList(HttpServletRequest request) {
        return "add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addTodoList(HttpServletRequest request, @RequestBody TodoList todoList) throws Exception {
        /* retrieve datastore */
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        /* save todolist entity */
        Entity todoListEntity = new Entity("TodoList");
        todoListEntity.setProperty("privateTodo", todoList.isPrivateTodo());
        todoListEntity.setProperty("name", todoList.getName());
        todoListEntity.setProperty("userId", request.getSession().getAttribute("userId"));
        ds.put(todoListEntity);
        /* retrieve todolist key */

        Key todoListKey = todoListEntity.getKey();
        /* save row's entities */
        for(int i = 0; i < todoList.getRows().size(); ++i) {
            TodoListRow row = todoList.getRows().get(i);
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
        return "success";
    }

    @RequestMapping(value = "/mylists", method = RequestMethod.GET)
    public String myListsView(HttpServletRequest request, Model model) {
        String userId = (String) request.getSession().getAttribute("userId");
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("TodoList");
        query.setFilter(Query.FilterOperator.EQUAL.of("userId", userId));
        System.out.println("userId=> " + userId);
        PreparedQuery preparedQuery = ds.prepare(query);
        /* get all todolist from current user */
        ArrayList<TodoList> listOfTodos = new ArrayList<>();
        for (Entity todoListEntity : preparedQuery.asIterable()) {
            Key todoListKey = todoListEntity.getKey();
            Map<String, Object> properties = todoListEntity.getProperties();
            TodoList currTodo = new TodoList();
            currTodo.setPrivateTodo((Boolean) properties.get("privateTodo"));
            currTodo.setName((String) properties.get("name"));
            currTodo.setId(todoListKey);
            query = new Query("TodoListRow");
            query.setFilter(Query.FilterOperator.EQUAL.of("todoListId", todoListKey));
            preparedQuery = ds.prepare(query);
            for(Entity todoListRow : preparedQuery.asIterable()) {
                properties = todoListRow.getProperties();
                TodoListRow currRow = new TodoListRow();
                currRow.setLevel((Long)properties.get("level"));
                currRow.setCategory((String)properties.get("category"));
                currRow.setDescription((String)properties.get("description"));
                currRow.setCompleted((boolean)properties.get("completed"));
                currRow.setStart((Date)properties.get("start"));
                currRow.setEnd((Date)properties.get("end"));
                currRow.setTodoListId((Key)properties.get("todoListId"));
                currTodo.getRows().add(currRow);
            }
            listOfTodos.add(currTodo);
        }
        model.addAttribute("todoList", listOfTodos);
        return "mylists";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEditView(HttpServletRequest req, Model model) {
        // select todolist entity with given id to load name and privacy
        String todoKey = req.getParameter("todoId");
        String userId = (String) req.getSession().getAttribute("userId");
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("TodoList");
        query.setFilter(Query.FilterOperator.EQUAL.of("userId", userId));
        PreparedQuery preparedQuery = ds.prepare(query);
        /* get all todolist from current user */
        for (Entity todoListEntity : preparedQuery.asIterable()) {
            Key todoListKey = todoListEntity.getKey();
            if(todoListKey.toString().equals(todoKey)) {
                model.addAttribute("id", todoListKey);
                model.addAttribute("name", todoListEntity.getProperties().get("name"));
                model.addAttribute("privateTodo", todoListEntity.getProperties().get("privateTodo"));
                ArrayList<TodoListRow> rowArrayList = new ArrayList<>();
                query = new Query("TodoListRow");
                query.setFilter(Query.FilterOperator.EQUAL.of("todoListId", todoListKey));
                preparedQuery = ds.prepare(query);
                for(Entity todoListRow : preparedQuery.asIterable()) {
                    Map<String, Object> properties = todoListRow.getProperties();
                    TodoListRow row = new TodoListRow();
                    row.setLevel((Long)properties.get("level"));
                    row.setCategory((String)properties.get("category"));
                    row.setDescription((String)properties.get("description"));
                    row.setCompleted((boolean)properties.get("completed"));
                    row.setStart((Date)properties.get("start"));
                    row.setEnd((Date)properties.get("end"));
                    row.setTodoListId((Key)properties.get("todoListId"));
                    rowArrayList.add(row);
                }
                model.addAttribute("rows", rowArrayList);
                return "edit";
            }
        }
        return"edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String postEdit(HttpServletRequest req, @RequestBody TodoList todoList) {
        System.out.println("=>posting edit");
        // work from here, start deleting all todolistrows with given id and add
        String todoId = todoList.getId().toString().replace("(no-id-yet)", "");
        System.out.println("todoList:" + todoId);
        String userId = (String) req.getSession().getAttribute("userId");
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
                /*System.out.println("<>todoListEntity: " + todoListRowEntity);
                TodoListRow row = todoList.getRows().get(rowIndex);
                todoListRowEntity.setProperty("level", row.getLevel());
                todoListRowEntity.setProperty("category", row.getCategory());
                todoListRowEntity.setProperty("description", row.getDescription());
                todoListRowEntity.setProperty("start", row.getStart());
                todoListRowEntity.setProperty("end", row.getEnd());
                todoListRowEntity.setProperty("completed", row.isCompleted());

                ++rowIndex;
                ds.put(todoListRowEntity);}*/
                ds.put(todoListEntity);
            }
        }


        return "successedit";
    }

    @RequestMapping(value = "/browse", method = RequestMethod.GET)
    public String browseView(HttpServletRequest request) {
        return "browse";
    }


    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String successView(HttpServletRequest request) {
        return "success";
    }


}
