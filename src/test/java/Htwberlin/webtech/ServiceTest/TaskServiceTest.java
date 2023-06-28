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
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest implements WithAssertions {

    @Mock
    private TaskRepository taskRep;


    TaskService taskService;

    @InjectMocks
    private TaskService IdUnderTest;

    @Test
    @DisplayName("should return true if delete was successful")
    void successfulDelete() {
        Long id = 54L;
        doReturn(true).when(taskRep).existsById(id);

        boolean taskResult = IdUnderTest.delete(id);

        verify(taskRep).deleteById(id);
        assertThat(taskResult).isTrue();
    }

    @Test
    @DisplayName("should return false if task to delete does not exist")
    void personDoesNotExist() {

        Long id = 9L;
        doReturn(false).when(taskRep).existsById(id);

        boolean taskResult = IdUnderTest.delete(id);

        verifyNoMoreInteractions(taskRep);
        assertThat(taskResult).isFalse();
    }

    @Test
    @DisplayName("should return the quantity of saved tasks")
    void getAllTasksTest() {
        List<Task> tasks = List.of(
                new Task("DBTech", "Übungsblatt3", LocalDate.of(2023, 05, 20)),
                new Task("DBTech", "Übungsblatt2", LocalDate.of(2023, 04, 15)),
                new Task("DBTech", "Übungsblatt1", LocalDate.of(2023, 04, 10))
        );
        when(taskRep.findAll()).thenReturn(tasks);
        TaskService taskService = new TaskService();

        int actualQuantity = taskService.getAll().size();
        int expectedQuantity = tasks.size();
        assertEquals(expectedQuantity, actualQuantity);
    }


}

