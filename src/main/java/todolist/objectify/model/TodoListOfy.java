package todolist.objectify.model;

import com.googlecode.objectify.ObjectifyService;
import todolist.model.TodoList;
import todolist.model.TodoListRow;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by kingfernandez on 2/7/17.
 */

public class TodoListOfy implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        // This will be invoked as part of a warmup request, or the first user request if no warmup
        // request.
        ObjectifyService.register(TodoList.class);
        ObjectifyService.register(TodoListRow.class);
    }

    public void contextDestroyed(ServletContextEvent event) {
        // App Engine does not currently invoke this method.
    }
}