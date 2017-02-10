package todolist.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;

/**
 * Created by elvis on 2/7/17.
 */

@Entity
public class TodoList {
    @Id public Long id;
    public String name;
    public boolean privateTodo;
    public ArrayList<TodoListRow> rows = new ArrayList<>();

    public TodoList() {}


    public TodoList(Long id, boolean privateTodo) {
        this(id, privateTodo, new ArrayList<TodoListRow>());
    }

    public TodoList(Long id, boolean privateTodo, ArrayList<TodoListRow> rows) {
        this.id = id;
        this.privateTodo = privateTodo;
        this.rows = rows;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
