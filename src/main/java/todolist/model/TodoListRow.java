package todolist.model;

import com.googlecode.objectify.annotation.Entity;

import java.util.Date;

/**
 * Created by kingfernandez on 2/7/17.
 */

@Entity
public class TodoListRow {
    String category;
    String description;
    boolean completed;
    Date start;
    Date end;


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
                "category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

}
