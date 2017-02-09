package todolist.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingfernandez on 2/8/17.
 */
@Entity
public class User {
    @Index @Id public String id;
    private String firstName;
    private String lastName;
    private ArrayList<TodoList> todoListList = new ArrayList<TodoList>();

    public User() {
    }

    public User(String id, String firstName, String lastName) {
        this(id, "", firstName, lastName, new ArrayList<TodoList>());
    }

    public User(String id, String email, String firstName, String lastName, ArrayList<TodoList> todoListList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.todoListList = todoListList;
    }

    public String getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<TodoList> getTodoListList() {
        return todoListList;
    }

    public void setTodoListList(ArrayList<TodoList> todoListList) {
        this.todoListList = todoListList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", todoListList=" + todoListList +
                '}';
    }

}
