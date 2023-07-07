package Htwberlin.webtech;

import Htwberlin.webtech.Task.Task;
import Htwberlin.webtech.Task.TaskController;
import Htwberlin.webtech.Task.TaskRepository;
import Htwberlin.webtech.Task.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;



    Task task1 = new Task(1L,"Web Project", "Web application based on Spring Boot", LocalDate.of(2023, 7, 12) , "Active" , false);
    Task task2 = new Task(2L,"Project management", "Creation of a website for a client", LocalDate.of(2023, 7, 20), "Completed", false);


    @Test
    @DisplayName("should return found task ")
    public void testGetRouteOneTask() throws Exception {

        when(taskService.get(1L)).thenReturn(task1);

        String expected = "{\"id\":1,\"name\":\"Web Project\",\"deadline\":[2023,7,12],\"description\":\"Web application based on Spring Boot\",\"status\":\"Active\",\"completed\":false}";

        this.mockMvc.perform(get("/task/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(expected)));
    }



    @Test
    @DisplayName("should return found tasks")
    void testGetRouteAllTasks() throws Exception {

        var tasks = List.of(task1, task2);

        doReturn(tasks).when(taskService).getAll();

        String expected = "{\"id\":1,\"name\":\"Web Project\",\"deadline\":[2023,7,12],\"description\":\"Web application based on Spring Boot\",\"status\":\"Active\",\"completed\":false},{\"id\":2,\"name\":\"Project management\",\"deadline\":[2023,7,20],\"description\":\"Creation of a website for a client\",\"status\":\"Completed\",\"completed\":false}";

        this.mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(expected)));
    }

    @Test
    @DisplayName("should delete an existing task")
    void testDeleteTask() throws Exception {
        when(taskService.delete(1L)).thenReturn(true);

        String expected = "The task with id 1 deleted";

        this.mockMvc.perform(delete("/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    @DisplayName("should return 200 if task is found")
    void taskIsFound() throws Exception {
        // given
        doReturn(task1).when(taskService).get(task1.getId());

        // when
        mockMvc.perform(get("/task/1"))
                // then
                .andExpect(status().isOk());
    }




 }



