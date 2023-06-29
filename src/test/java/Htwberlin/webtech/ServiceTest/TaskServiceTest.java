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


}

