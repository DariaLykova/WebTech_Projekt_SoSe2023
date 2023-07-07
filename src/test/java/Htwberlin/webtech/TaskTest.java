package Htwberlin.webtech;

import Htwberlin.webtech.Task.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TaskTest {


    Task task1 = new Task(1L,"Web Project", "Web application based on Spring Boot", LocalDate.now() , "Active" );
    Task task2 = new Task(1L,"Web Project", "Web application based on Spring Boot", LocalDate.now() , "Active" );
    Task task3 = new Task(2L,"Project management", "Creation of a website for a client", LocalDate.of(2023, 7, 20), "Completed");

    @Test
    @DisplayName("should check if the task is equal to itself")
    public void testEquals_SameObject() {

        Assertions.assertEquals(task1, task1);
    }

    @Test
    @DisplayName("should check if the tasks are equal")
    public void testEquals_EqualTasks() {

        Assertions.assertEquals(task1, task2);
    }

    @Test
    @DisplayName("should check if the tasks are not equal")
    public void testEquals_NotEqualTasks() {

        Assertions.assertNotEquals(task1, task3);
    }

    @Test
    @DisplayName("should check if the hash codes of the tasks are equal")
    public void testHashCode_EqualTasks() {

        Assertions.assertEquals(task1.hashCode(), task2.hashCode());
    }

    @Test
    @DisplayName("should check if the toString() method returns the expected string representation")
    public void testToString() {

        String expectedString = "Task{id=1, name='Web Project', description='Web application based on Spring Boot', deadline=" +
                LocalDate.now() + ", status=Active}";

        Assertions.assertEquals(expectedString, task1.toString());
    }
}
