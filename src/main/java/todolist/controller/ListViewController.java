package todolist.controller;

import com.google.appengine.api.datastore.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import todolist.model.TodoList;
import todolist.model.TodoListRow;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by Chaeyoung on 2/11/17.
 */

@Controller
@SessionAttributes("userId")
@RequestMapping("/list")
public class ListViewController {


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getMyLists(HttpServletRequest request, Model model) {
        // Get my user id
        String userId = (String) request.getSession().getAttribute("userId");
        if(userId == null) return "index";

        // Load all lists from datastore
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("TodoList");
        query.setFilter(Query.FilterOperator.EQUAL.of("userId", userId));
        PreparedQuery preparedQuery = ds.prepare(query);
        ArrayList<TodoList> listOfTodos = new ArrayList<>();
        for (Entity todoListEntity : preparedQuery.asIterable()) {
            TodoList currTodo = new TodoList();
            Key todoListKey = todoListEntity.getKey();
            Map<String, Object> properties = todoListEntity.getProperties();
            currTodo.setPrivateTodo((Boolean) properties.get("privateTodo"));
            currTodo.setName((String) properties.get("name"));
            currTodo.setId(todoListKey);
            query = new Query("TodoListRow");
            query.setFilter(Query.FilterOperator.EQUAL.of("todoListId", todoListKey));
            preparedQuery = ds.prepare(query);
            for(Entity todoListRow : preparedQuery.asIterable()) {
                TodoListRow currRow = new TodoListRow();
                properties = todoListRow.getProperties();
                currRow.setLevel((Long)properties.get("level"));
                currRow.setCategory((String)properties.get("category"));
                currRow.setDescription((String)properties.get("description"));
                currRow.setStart((Date)properties.get("start"));
                currRow.setEnd((Date)properties.get("end"));
                currRow.setTodoListId((Key)properties.get("todoListId"));
                currRow.setCompleted((boolean)properties.get("completed"));
                currTodo.getRows().add(currRow);
            }
            listOfTodos.add(currTodo);
        }
        model.addAttribute("todoList", listOfTodos);
        return "mylists";
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public String getUserLists(HttpServletRequest request, Model model, @PathVariable String userId) {
        // Check valid entry; Accept only if session and request is matched.
        String sessionUserId = (String) request.getSession().getAttribute("userId");
        if(!sessionUserId.equals(userId)) return "error";

        // Load all lists from datastore
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("TodoList");
        query.setFilter(Query.FilterOperator.EQUAL.of("userId", userId));
        PreparedQuery preparedQuery = ds.prepare(query);
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getPublicListDetail(HttpServletRequest req, Model model, @PathVariable String id) {
        String todoListId = req.getParameter("todoListId");
        String userId = (String) req.getSession().getAttribute("userId");
        String listUserId = "";
        model.addAttribute("id", todoListId);

        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("TodoList");
        query.setFilter(Query.FilterOperator.EQUAL.of("todoId", todoListId));
        PreparedQuery preparedQuery = ds.prepare(query);
        for(Entity todoListEntity : preparedQuery.asIterable()){
            model.addAttribute("name", todoListEntity.getProperties().get("name"));
            model.addAttribute("privateTodo", todoListEntity.getProperties().get("privateTodo"));
            listUserId = todoListEntity.getProperties().get("userId").toString();
        }
        if(listUserId.equals(userId)) model.addAttribute("owner", true);
        else model.addAttribute("owner", false);

        ArrayList<TodoListRow> todoListRows = new ArrayList<>();
        query = new Query("TodoListRow");
        query.setFilter(Query.FilterOperator.EQUAL.of("todoListId", todoListId));
        preparedQuery = ds.prepare(query);

        for(Entity todoListRow : preparedQuery.asIterable()){
            TodoListRow row = new TodoListRow();
            Map<String, Object> properties = todoListRow.getProperties();
            row.setLevel((Long)properties.get("level"));
            row.setCategory((String)properties.get("category"));
            row.setDescription((String)properties.get("description"));
            row.setCompleted((boolean)properties.get("completed"));
            row.setStart((Date)properties.get("start"));
            row.setEnd((Date)properties.get("end"));
            row.setTodoListId((Key)properties.get("todoListId"));
            todoListRows.add(row);
        }

        model.addAttribute("rows", todoListRows);
        return "view";
    }

    @RequestMapping(value = "/public", method = RequestMethod.GET)
    public String getPublicList(HttpServletRequest req, Model model) throws Exception {
        String userId = (String) req.getSession().getAttribute("userId");
        if(userId == null) return "index";
        String fullName = (String) req.getSession().getAttribute("fullName");
        model.addAttribute("fullName", fullName);
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("TodoList");
        query.setFilter(Query.FilterOperator.EQUAL.of("privateTodo", false));
        PreparedQuery preparedQuery = ds.prepare(query);

        ArrayList<TodoList> listOfTodos = new ArrayList<>();
        for (Entity todoListEntity : preparedQuery.asIterable()) {
            Key todoListKey = todoListEntity.getKey();
            Map<String, Object> properties = todoListEntity.getProperties();

            TodoList currTodo = new TodoList();
            currTodo.setPrivateTodo(false);
            currTodo.setName((String) properties.get("name"));
            currTodo.setId(todoListKey);
            currTodo.setOwner((String) properties.get("owner"));
            currTodo.setOwnerId((Key)properties.get("ownerId"));

            query = new Query("TodoListRow");
            query.setFilter(Query.FilterOperator.EQUAL.of("todoListId", todoListKey));

            preparedQuery = ds.prepare(query);
            for(Entity todoListRow : preparedQuery.asIterable()) {
                TodoListRow currRow = new TodoListRow();
                properties = todoListRow.getProperties();
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
        return "list_public";
    }

}
