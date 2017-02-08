package todolist.model;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingfernandez on 2/7/17.
 */



@Entity
public class TodoList {
    @Id public Long id;
    boolean privateTodo;
    List<TodoListRow> rows = new ArrayList<>();


    public boolean isPrivateTodo() {
        return privateTodo;
    }

    public void setPrivateTodo(boolean privateTodo) {
        this.privateTodo = privateTodo;
    }

    public List<TodoListRow> getRows() {
        return rows;
    }

    public void setList(List<TodoListRow> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "TodoList{" +
                "privateTodo=" + privateTodo +
                ", rows=" + rows.toString() +
                '}';
    }
}
