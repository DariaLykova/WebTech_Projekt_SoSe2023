package Htwberlin.webtech.ServiceTest;

import Htwberlin.webtech.Task;
import Htwberlin.webtech.TaskRepository;
import Htwberlin.webtech.TaskService;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest implements WithAssertions {

    @Mock
    private TaskRepository taskRep;
    TaskService taskService;
    Task task1 = new Task(1L,"Web Project", "Web application based on Spring Boot", LocalDate.of(2023, 7, 12) , "Active" );
    Task task2 = new Task(2L,"Project management", "Creation of a website for a client", LocalDate.of(2023, 7, 20), "Completed");

    @InjectMocks
    private TaskService IdUnderTest;



    @Test
    public void save_ValidTask_ReturnsSavedTask() {

        Task savedTask = IdUnderTest.save(task1);
        verify(taskRep).save(task1);

        assertNotNull(savedTask);
    }

    @Test
    public void get_ValidId_ReturnsTask() {
        Long id = task1.getId();

        Task retrievedTask = IdUnderTest.get(id);


        assertNotNull(retrievedTask);
    }

    @Test
    public void getAll_TasksExist_ReturnsListOfTasks() {

        List<Task> allTasks = IdUnderTest.getAll();

        assertNotNull(allTasks);
        assertTrue(allTasks.size() > 0);
    }

    @Test
    @DisplayName("should return true if delete was successful")
    public void getAll_NoTasksExist_ReturnsEmptyList() {

        Long id1 = task1.getId();
        Long id2 = task2.getId();
        IdUnderTest.delete(id1);
        IdUnderTest.delete(id2);

        List<Task> allTasks = IdUnderTest.getAll();

        assertNotNull(allTasks);
        assertEquals(0, allTasks.size());
    }



    @Test
    @DisplayName("should return true if delete was successful")
    void successfulDelete() {

        Long id = task1.getId();
        doReturn(true).when(taskRep).existsById(id);

        boolean taskResult = IdUnderTest.delete(id);

        verify(taskRep).deleteById(id);
        assertThat(taskResult).isTrue();
    }

    @Test
    @DisplayName("should return false if task to delete does not exist")
    void taskDoesNotExist() {

        Long id = task1.getId();
        doReturn(false).when(taskRep).existsById(id);

        boolean taskResult = IdUnderTest.delete(id);

        verifyNoMoreInteractions(taskRep);
        assertThat(taskResult).isFalse();
    }


}

