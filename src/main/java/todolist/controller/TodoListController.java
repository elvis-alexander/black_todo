package todolist.controller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.appengine.repackaged.com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.appengine.repackaged.com.google.api.client.json.jackson.JacksonFactory;
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
@RequestMapping("")
public class TodoListController {
    private static final JacksonFactory jacksonFactory = new JacksonFactory();

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public void signIn(HttpServletRequest request, @RequestParam("idtoken") String idTokenString, HttpServletResponse resp) throws Exception {
        // signing user in
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(UrlFetchTransport.getDefaultInstance(), jacksonFactory)
                .setAudience(Collections.singletonList("916429395024-fm59d90t7lukalhmia1s0o3f3gt4j9kf.apps.googleusercontent.com"))
                .build();

        GoogleIdToken.Payload payload = verifier.verify(idTokenString).getPayload();
        String userId = payload.getSubject();

        /* set userId as session */
        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);
        session.setAttribute("fullName", (String)payload.get("given_name") + " " + (String)payload.get("family_name"));
        System.out.println("SET USERID: " + userId + " session val: " + session.getAttribute("userId"));

        /* save to data store */
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Entity user = new Entity("User", userId);
        user.setProperty("firstName",  (String) payload.get("given_name"));
        user.setProperty("lastName", (String)payload.get("family_name"));
        user.setProperty("userId", (String)userId);
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
        /* save todolist entity */
        Entity todoListEntity = new Entity("TodoList");
        String fullName = (String)request.getSession().getAttribute("fullName");
        todoListEntity.setProperty("privateTodo", todoList.isPrivateTodo());
        todoListEntity.setProperty("name", todoList.getName());
        todoListEntity.setProperty("owner", fullName);
        todoListEntity.setProperty("ownerId", request.getSession().getAttribute("userId"));
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

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEditView(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        if(session.getAttribute("userId") == null)
            return "index";

        // select todolist entity with given id to load name and privacy
        String todoKey = req.getParameter("todoId");
        String userId = (String) session.getAttribute("userId");
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
                    row.setCategory((String)properties.get("category"));
                    row.setDescription((String)properties.get("description"));
                    row.setLevel((Long)properties.get("level"));
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

    @RequestMapping(value = "/view/{todoKey}", method = RequestMethod.GET)
    public String getEditView(HttpServletRequest req, Model model, @PathVariable String todoKey) {
        HttpSession session = req.getSession();
        if(session.getAttribute("userId") == null)
            return "index";

        // select todolist entity with given id to load name and privacy
        //String todoKey = req.getParameter("todoId");
        String userId = (String) session.getAttribute("userId");
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
                    TodoListRow row = new TodoListRow();
                    Map<String, Object> properties = todoListRow.getProperties();
                    row.setTodoListId((Key)properties.get("todoListId"));
                    row.setLevel((Long)properties.get("level"));
                    row.setCategory((String)properties.get("category"));
                    row.setDescription((String)properties.get("description"));
                    row.setCompleted((boolean)properties.get("completed"));
                    row.setStart((Date)properties.get("start"));
                    row.setEnd((Date)properties.get("end"));
                    rowArrayList.add(row);
                }
                model.addAttribute("rows", rowArrayList);
                return "view";
            }
        }
        return "view";
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

    @RequestMapping(value = "/browse", method = RequestMethod.GET)
    public String browseView(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if(session.getAttribute("userId") == null)
            return "index";

        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        String userId = (String) session.getAttribute("userId");
        Query query = new Query("TodoList");
        PreparedQuery pq = ds.prepare(query);
        ArrayList<TodoList> listOfTodos = new ArrayList<>();
        for (Entity todoListEntity : pq.asIterable()) {
            if (!userId.equals((String) todoListEntity.getProperties().get("userId"))) {
                // display to user
                if((Boolean)todoListEntity.getProperties().get("privateTodo") == false) {
                    TodoList currTodo = new TodoList();
                    currTodo.setName((String) todoListEntity.getProperties().get("name"));
                    currTodo.setId(todoListEntity.getKey());
                    // loop through every todolist
                    query = new Query("TodoListRow");
                    query.setFilter(Query.FilterOperator.EQUAL.of("todoListId", todoListEntity.getKey()));
                    pq = ds.prepare(query);
                    for(Entity todoListRow : pq.asIterable()) {
                        Map<String, Object> properties = todoListRow.getProperties();
                        TodoListRow currRow = new TodoListRow();
                        currRow.setCategory((String)properties.get("category"));
                        currTodo.getRows().add(currRow);
                    }

                    Query userNameQuery = new Query("User");
                    PreparedQuery userNamePQ = ds.prepare(userNameQuery);
                    currTodo.ownerName = "default";
                    for(Entity userEntity : userNamePQ.asIterable()) {
                        String user = userEntity.getKey().toString().replace("User(\"", "").replace("\")","");
                        if(user.equals(todoListEntity.getProperties().get("userId"))) {
                            currTodo.ownerName = (String) userEntity.getProperties().get("firstName");
                            break;
                        }
                    }
                    listOfTodos.add(currTodo);
                }
            }
        }
        model.addAttribute("todoList", listOfTodos);
        return "browse";
    }

    @RequestMapping(value = "/browseextended", method = RequestMethod.GET)
    public String getBrowseExtended(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if(session.getAttribute("userId") == null)
            return "index";

        String todoKey = request.getParameter("todoId");
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("TodoList");
        PreparedQuery pq = ds.prepare(query);
        for (Entity todoListEntity : pq.asIterable()) {
            Key todoListKey = todoListEntity.getKey();
            if (todoListKey.toString().equals(todoKey)) {

                model.addAttribute("ownerName", (String)request.getParameter("ownerName"));
                model.addAttribute("name", todoListEntity.getProperties().get("name"));
                model.addAttribute("privateTodo", todoListEntity.getProperties().get("privateTodo"));
                ArrayList<TodoListRow> rowArrayList = new ArrayList<>();
                query = new Query("TodoListRow");
                query.setFilter(Query.FilterOperator.EQUAL.of("todoListId", todoListKey));
                pq = ds.prepare(query);
                for (Entity todoListRow : pq.asIterable()) {
                    Map<String, Object> properties = todoListRow.getProperties();
                    TodoListRow row = new TodoListRow();
                    row.setLevel((Long) properties.get("level"));
                    row.setCategory((String) properties.get("category"));
                    row.setDescription((String) properties.get("description"));
                    row.setCompleted((boolean) properties.get("completed"));
                    row.setStart((Date) properties.get("start"));
                    row.setEnd((Date) properties.get("end"));
                    rowArrayList.add(row);
                }
                model.addAttribute("rows", rowArrayList);
                return "browseextended";
            }
        }
        return "browseextended";
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
