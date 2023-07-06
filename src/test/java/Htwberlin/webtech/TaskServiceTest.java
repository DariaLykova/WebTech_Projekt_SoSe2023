package Htwberlin.webtech;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @DisplayName("should save task")
    public void testSave() {

        // Mock the save() method of the repository
        Mockito.when(taskRep.save(task1)).thenReturn(task1);
        Task savedTask = taskService.save(task1);

        Assertions.assertEquals(task1, savedTask);

        // Verify that the save() method of the repository was called
        Mockito.verify(taskRep, Mockito.times(1)).save(task1);
    }

    @Test
    @DisplayName("should find a task with certain id")
    public void testGet1() {

        Mockito.when(taskRep.findById(task1.getId())).thenReturn(Optional.of(task1));
        Task savedTask = taskService.get(task1.getId());

        Assertions.assertEquals(task1, savedTask);

        Mockito.verify(taskRep, Mockito.times(1)).findById(task1.getId());
    }

    @Test
    @DisplayName("should find a task by its id")
    public void testGet2() {

        doReturn(Optional.of(task1)).when(taskRep).findById(1L);
        doReturn(Optional.of(task2)).when(taskRep).findById(2L);

        Task actual1 = taskService.get(1L);
        Task actual2 = taskService.get(2L);

        assertEquals(actual1.getName(), "Web Project");
        assertEquals(actual2.getName(), "Project management");

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
    @DisplayName("should find all tasks in repository")
    public void testGetAll() {

        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

        Mockito.when(taskRep.findAll()).thenReturn(tasks);
        List<Task> savedTask = taskService.getAll();

        Assertions.assertEquals(tasks, savedTask);

        Mockito.verify(taskRep, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("should return empty list if there are no tasks in the repository")
    public void testGetAll_EmptyList() {

        Mockito.when(taskRep.findAll()).thenReturn(new ArrayList<>());
        List<Task> savedTask = taskService.getAll();

        Assertions.assertTrue(savedTask.isEmpty());

        Mockito.verify(taskRep, Mockito.times(1)).findAll();
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

    @Test
    @DisplayName("should update task if it exists")
    public void testUpdate_ExistingTask() {

        Mockito.when(taskRep.existsById(task1.getId())).thenReturn(true);
        Mockito.when(taskRep.save(task1)).thenReturn(task1);
        Task updatedTask = taskService.update(task1);

        Assertions.assertEquals(task1, updatedTask);

        Mockito.verify(taskRep, Mockito.times(1)).existsById(1L);
        Mockito.verify(taskRep, Mockito.times(1)).save(task1);
    }

    @Test
    @DisplayName("should return IllegalArgumentException if task to update does not exist")
    public void testUpdate_NonExistingTask() {

        Task nonExistingTask = new Task();
        nonExistingTask.setId(2L);

        Mockito.when(taskRep.existsById(2L)).thenReturn(false);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            taskService.update(nonExistingTask);
        });

        Mockito.verify(taskRep, Mockito.times(1)).existsById(2L);
        Mockito.verify(taskRep, Mockito.never()).save(nonExistingTask);
    }

}

