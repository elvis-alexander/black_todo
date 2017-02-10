package todolist.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;

/**
 * Created by elvis on 2/7/17.
 */

@Entity
public class TodoList {
    public @Id com.google.appengine.api.datastore.Key id;
    public String name;
    public boolean privateTodo;
    public ArrayList<TodoListRow> rows = new ArrayList<>();

    public TodoList() {}

    public com.google.appengine.api.datastore.Key getId() {
        return id;
    }

    public void setId(com.google.appengine.api.datastore.Key id) {
        this.id = id;
    }

    public boolean isPrivateTodo() {
        return privateTodo;
    }

    public void setPrivateTodo(boolean privateTodo) {
        this.privateTodo = privateTodo;
    }

    public ArrayList<TodoListRow> getRows() { return rows; }

    public void setRows(ArrayList<TodoListRow> rows) {
        this.rows = rows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TodoList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", privateTodo=" + privateTodo +
                ", rows=" + rows +
                '}';
    }

}
