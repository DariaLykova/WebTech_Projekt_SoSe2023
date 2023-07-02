package Htwberlin.webtech.Task;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate deadline;
    private String description;
    private String status;
    private Boolean completed = false;


    public Task() {}
    public Task(String name, String description, LocalDate deadline) {

        this.name=name;
        this.description=description;
        this.deadline=deadline;
        setStatus();
    }

    public Task(Long id,String name, String description, LocalDate deadline, String status) {

        this.id= id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
        setStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getStatus() {return status; }

    public void setStatus() {
        LocalDate currentDate = LocalDate.now();

        if (deadline.isBefore(currentDate)) {
            this.completed = false;
        } else if (deadline.isAfter(currentDate)) {
            this.completed = false;
        } else {
            this.completed = true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(name, task.name) && Objects.equals(deadline, task.deadline) && Objects.equals(description, task.description) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, deadline, description, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", status=" + status +
                '}';
    }
}
