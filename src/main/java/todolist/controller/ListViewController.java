package todolist.controller;

import com.google.appengine.api.datastore.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import todolist.model.TodoList;
import todolist.model.TodoListRow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by Chaeyoung
 */
@Controller
@RequestMapping(value = "")
public class ListViewController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getMyLists(HttpServletRequest request, Model model) {
        // Get my user id
        String userId = (String) request.getSession().getAttribute("userId");
        if(userId == null) return "index";

        // Load all lists from datastore
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("TodoList");
        query.setFilter(Query.FilterOperator.EQUAL.of("ownerId", userId));
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
                currRow.setLevel((long)properties.get("level"));
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

    @RequestMapping(value = "/list/public", method = RequestMethod.GET)
    public String getPublicList(HttpServletRequest request, Model model) throws Exception {
        HttpSession session = request.getSession();
        if(session.getAttribute("userId") == null)
            return "index";
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        String userId = (String) session.getAttribute("userId");
        Query query = new Query("TodoList");
        PreparedQuery pq = ds.prepare(query);
        ArrayList<TodoList> listOfTodos = new ArrayList<>();
        for (Entity todoListEntity : pq.asIterable()) {
            if (!userId.equals((String) todoListEntity.getProperties().get("ownerId"))) {
                // display to user
                if((Boolean)todoListEntity.getProperties().get("privateTodo") == false) {
                    TodoList currTodo = new TodoList();
                    currTodo.setName((String)todoListEntity.getProperties().get("name"));
                    currTodo.setId(todoListEntity.getKey());
                    currTodo.setOwnerName((String)todoListEntity.getProperties().get("ownerName"));
                    currTodo.setPrivateTodo((Boolean)todoListEntity.getProperties().get("privateTodo"));
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
                    listOfTodos.add(currTodo);
                }
            }
        }
        model.addAttribute("todoList", listOfTodos);
        model.addAttribute("userId", userId);
        return "list_public";
    }

    @RequestMapping(value = "/view/{todoKey}", method = RequestMethod.GET)
    public String getViewByKey(HttpServletRequest request, Model model, @PathVariable String todoKey) {
        HttpSession session = request.getSession();
        if(session.getAttribute("userId") == null)
            return "index";

        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("TodoList");
        PreparedQuery pq = ds.prepare(query);
        for (Entity todoListEntity : pq.asIterable()) {
            Key todoListKey = todoListEntity.getKey();
            if (todoListKey.toString().equals(todoKey)) {

                model.addAttribute("ownerName", todoListEntity.getProperties().get("ownerName"));
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
                return "view";
            }
        }
        return "errpr";
    }

    @RequestMapping(value = "/edit/{todoKey}", method = RequestMethod.GET)
    public String getEditView(HttpServletRequest req, Model model, @PathVariable String todoKey) {
        HttpSession session = req.getSession();
        if(session.getAttribute("userId") == null)
            return "index";

        // Load parsed todokey and query the todolist
        String userId = (String) session.getAttribute("userId");
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("TodoList");
        query.setFilter(Query.FilterOperator.EQUAL.of("ownerId", userId));
        PreparedQuery preparedQuery = ds.prepare(query);
        /* get all todolist from current user */
        for (Entity todoListEntity : preparedQuery.asIterable()) {
            Key todoListKey = todoListEntity.getKey();
            if(todoListKey.toString().equals(todoKey)) {
                model.addAttribute("id", todoListKey);
                model.addAttribute("name", todoListEntity.getProperties().get("name"));
                model.addAttribute("privateTodo", todoListEntity.getProperties().get("privateTodo"));
                model.addAttribute("ownerName", todoListEntity.getProperties().get("ownerName"));
                ArrayList<TodoListRow> rowArrayList = new ArrayList<>();
                query = new Query("TodoListRow");
                query.setFilter(Query.FilterOperator.EQUAL.of("todoListId", todoListKey));
                preparedQuery = ds.prepare(query);
                for(Entity todoListRow : preparedQuery.asIterable()) {
                    Map<String, Object> properties = todoListRow.getProperties();
                    TodoListRow row = new TodoListRow();
                    row.setCategory((String)properties.get("category"));
                    row.setLevel((long)properties.get("level"));
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
        return "error";
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @RequestMapping(value = "/remove/{todoKey}", method = RequestMethod.GET)
    public String removeTodoList(HttpServletRequest req, Model model, @PathVariable String todoKey) {
        HttpSession session = req.getSession();
        if(session.getAttribute("userId") == null)
            return "index";

        // select todolist entity with given id to load name and privacy
        String userId = (String) session.getAttribute("userId");
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("TodoList");
        query.setFilter(Query.FilterOperator.EQUAL.of("ownerId", userId));
        PreparedQuery preparedQuery = ds.prepare(query);
        /* get all todolist from current user */
        for (Entity todoListEntity : preparedQuery.asIterable()) {
            Key todoListKey = todoListEntity.getKey();
            if(todoListKey.toString().equals(todoKey)) {
                if(!todoListEntity.getProperties().get("ownerId").equals(userId)) return "removeFailure";
                model.addAttribute("id", todoListKey);
                model.addAttribute("name", todoListEntity.getProperties().get("name"));
                model.addAttribute("privateTodo", todoListEntity.getProperties().get("privateTodo"));
                query = new Query("TodoListRow");
                query.setFilter(Query.FilterOperator.EQUAL.of("todoListId", todoListKey));
                preparedQuery = ds.prepare(query);
                for(Entity todoListRow : preparedQuery.asIterable()) {
                    ds.delete(todoListRow.getKey());
                }
                ds.delete(todoListEntity.getKey());
                return "removeSuccess";
            }
        }
        return "error";
    }

}
