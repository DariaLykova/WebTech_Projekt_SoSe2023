package Htwberlin.webtech;

import Htwberlin.webtech.Task.Task;
import Htwberlin.webtech.Task.TaskController;
import Htwberlin.webtech.Task.TaskRepository;
import Htwberlin.webtech.Task.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskController taskController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    Task task1 = new Task(1L,"Web Project", "Web application based on Spring Boot", LocalDate.of(2023, 7, 12) , "Active" );
    Task task2 = new Task(2L,"Project management", "Creation of a website for a client", LocalDate.of(2023, 7, 20), "Completed");

    @Test
    @DisplayName("should return found persons from person service")
    void should_return_found_task_from_task_service() throws Exception {

        var tasks = List.of(task1, task2);

        doReturn(tasks).when(taskService).getAll();

        mockMvc.perform(get("/tasks"))
                // then
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.size()").value(2))
                .andExpect((ResultMatcher) jsonPath("$[0].id").value(1))
                .andExpect((ResultMatcher) jsonPath("$[0].name").value("Web Project"))
                .andExpect((ResultMatcher) jsonPath("$[0].description").value("Web application based on Spring Boot"))
                .andExpect((ResultMatcher) jsonPath("$[0].deadline").value(LocalDate.of(2023, 7, 12)))
                .andExpect((ResultMatcher) jsonPath("$[0].status").value("Active"))
                .andExpect((ResultMatcher) jsonPath("$[1].id").value(2))
                .andExpect((ResultMatcher) jsonPath("$[1].name").value("Project management"))
                .andExpect((ResultMatcher) jsonPath("$[1].description").value("Creation of a website for a client"))
                .andExpect((ResultMatcher) jsonPath("$[1].deadline").value(LocalDate.of(2023, 7, 20)))
                .andExpect((ResultMatcher) jsonPath("$[1].status").value("Completed"));
    }


    @Test
    @DisplayName("should return 404 if person is not found")
    void should_return_404_if_person_is_not_found() throws Exception {
        // given
        doReturn(null).when(taskService).get(anyLong());

        // when
        mockMvc.perform(get("/task/123"))
                // then
                .andExpect(status().isNotFound());
    }


    @Test
    public void testCreateTask() throws Exception {
        Task task = new Task("Task Name", "Task Description", LocalDate.now());
        Task savedTask = new Task(1L, "Task Name", "Task Description", LocalDate.now(), "Pending");

        Mockito.when(taskService.save(Mockito.any(Task.class))).thenReturn(savedTask);

        mockMvc.perform(MockMvcRequestBuilders.post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(task)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Task Name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Task Description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deadline", Matchers.is(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("Pending")));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testEditTask() throws Exception {
        Long taskId = 1L;
        Task existingTask = new Task(taskId, "Task Name", "Task Description", LocalDate.now(), "Pending");
        Task updatedTask = new Task(taskId, "Updated Task Name", "Updated Task Description", LocalDate.now().plusDays(1), "Completed");

        Mockito.when(taskService.get(taskId)).thenReturn(existingTask);
        Mockito.when(taskService.update(Mockito.any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(MockMvcRequestBuilders.put("/edit/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedTask)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Updated Task Name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Updated Task Description")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.deadline", Matchers.is(LocalDate.now().plusDays(1).toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("Completed")));
    }


    }



