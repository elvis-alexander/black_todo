package todolist.controller;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.googlecode.objectify.ObjectifyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import todolist.model.TodoList;
import todolist.model.TodoListRow;

/**
 * Created by kingfernandez on 2/6/17.
 */

@Controller
@RequestMapping("/todolist")
public class TodoListController {


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddTodoList() {
        return "create";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveNewTodoList(@RequestBody TodoList todoList) {
        System.out.println("->" + todoList);

        Entity entityTodoList = new Entity("TodoList");
        entityTodoList.setProperty("privateTodo", todoList.isPrivateTodo());
        entityTodoList.setProperty("rows", todoList.getRows());

        ObjectifyService.register(TodoList.class);
        ObjectifyService.ofy().save().entity(entityTodoList).now();


        // for(int i = 0; i < todoList.getRows().size(); ++i) {
            // TodoListRow curr = todoList.getRows().get(i);   // curr -> {category, description, completed, start, end }
        //}

        /* save to data store */


        return "create";

    }


}

