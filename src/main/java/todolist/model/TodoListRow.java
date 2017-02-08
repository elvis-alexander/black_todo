package todolist.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.Date;

/**
 * Created by kingfernandez on 2/7/17.
 */

@Entity
public class TodoListRow {
    @Id public  long id;
    public int level;
    public String category;
    public String description;
    public boolean completed;
    public Date start;
    public Date end;

    public TodoListRow() {
    }

    public TodoListRow(long id, int level, String category, String description, boolean completed, Date start, Date end) {
        this.id = id;
        this.level = level;
        this.category = category;
        this.description = description;
        this.completed = completed;
        this.start = start;
        this.end = end;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
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
                '}';
    }

}

