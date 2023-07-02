package Htwberlin.webtech.ServiceTest;

import Htwberlin.webtech.Task.Task;
import Htwberlin.webtech.Task.TaskRepository;
import Htwberlin.webtech.Task.TaskService;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest implements WithAssertions {

    @Mock
    private TaskRepository taskRep;

    @InjectMocks
    private TaskService taskService;

    Task task1 = new Task(1L,"Web Project", "Web application based on Spring Boot", LocalDate.of(2023, 7, 12) , "Active" );
    Task task2 = new Task(2L,"Project management", "Creation of a website for a client", LocalDate.of(2023, 7, 20), "Completed");




    @Test
    @DisplayName("should return true if save was successful")
    public void testSave() {

        // Mock the save() method of the repository
        Mockito.when(taskRep.save(task1)).thenReturn(task1);
        Task savedTask = taskService.save(task1);

        assertEquals(task1, savedTask);

        // Verify that the save() method of the repository was called
        Mockito.verify(taskRep, Mockito.times(1)).save(task1);
    }

    @Test
    @DisplayName("should return true if task with this id  was found")
    public void testGet() {

        Mockito.when(taskRep.findById(task1.getId())).thenReturn(Optional.of(task1));
        Task savedTask = taskService.get(task1.getId());

        Assertions.assertEquals(task1, savedTask);

        Mockito.verify(taskRep, Mockito.times(1)).findById(task1.getId());
    }

    @Test
    @DisplayName("should return RuntimeException if task with this id does not exist")
    public void testGet_InvalidId() {

        // Mock the findById() method of the repository to return an empty optional
        Mockito.when(taskRep.findById(3L)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> {
            taskService.get(3L);
        });

        Mockito.verify(taskRep, Mockito.times(1)).findById(3L);
    }


    @Test
    public void get_ValidId_ReturnsTask() {
        Long id = task1.getId();

        Task retrievedTask = taskService.get(id);


        assertNotNull(retrievedTask);
    }

    @Test
    public void getAll_TasksExist_ReturnsListOfTasks() {

        List<Task> allTasks = taskService.getAll();

        assertNotNull(allTasks);
        assertTrue(allTasks.size() > 0);
    }

    @Test
    @DisplayName("should return true if delete was successful")
    public void getAll_NoTasksExist_ReturnsEmptyList() {

        Long id1 = task1.getId();
        Long id2 = task2.getId();
        taskService.delete(id1);
        taskService.delete(id2);

        List<Task> allTasks = taskService.getAll();

        assertNotNull(allTasks);
        assertEquals(0, allTasks.size());
    }



    @Test
    @DisplayName("should return true if delete was successful")
    void successfulDelete() {

        Long id = task1.getId();
        doReturn(true).when(taskRep).existsById(id);

        boolean taskResult = taskService.delete(id);

        verify(taskRep).deleteById(id);
        assertThat(taskResult).isTrue();
    }

    @Test
    @DisplayName("should return false if task to delete does not exist")
    void taskDoesNotExist() {

        Long id = task1.getId();
        doReturn(false).when(taskRep).existsById(id);

        boolean taskResult = taskService.delete(id);

        verifyNoMoreInteractions(taskRep);
        assertThat(taskResult).isFalse();
    }

}

