package todolist.model;

import com.google.appengine.repackaged.com.google.api.client.util.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;

/**
 * Created by elvis on 2/7/17.
 */

@Entity
public class TodoListRow {
    @Key @Id private Long id;
    public Long level;
    public String category;
    public String description;
    public boolean completed;
    public Date start;
    public Date end;
    public com.google.appengine.api.datastore.Key todoListId;


    public TodoListRow() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public com.google.appengine.api.datastore.Key getTodoListId() {
        return todoListId;
    }

    public void setTodoListId(com.google.appengine.api.datastore.Key todoListId) {
        this.todoListId = todoListId;
    }

    @Override
    public String toString() {
        return "TodoListRow{" +
                "id=" + id +
                ", level=" + level +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", start=" + start +
                ", end=" + end +
                ", todoListId=" + todoListId +
                '}';
    }

}

