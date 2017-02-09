package todolist.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;

/**
 * Created by elvis on 2/7/17.
 */

@Entity
public class TodoList {
    @Id private Long id;
    public boolean privateTodo;
    private ArrayList<TodoListRow> rows = new ArrayList<>();

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

    @Override
    public String toString() {
        return "TodoList{" +
                "privateTodo=" + privateTodo +
                ", rows=" + rows.toString() +
                '}';
    }
}
