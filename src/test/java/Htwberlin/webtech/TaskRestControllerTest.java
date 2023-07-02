package Htwberlin.webtech;

import Htwberlin.webtech.Task.Task;
import Htwberlin.webtech.Task.TaskController;
import Htwberlin.webtech.Task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

@WebMvcTest(TaskController.class)
public class TaskRestControllerTest {



        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private TaskService taskService;

    Task task1 = new Task(1L,"Web Project", "Web application based on Spring Boot", LocalDate.of(2023, 7, 12) , "Active" );
    Task task2 = new Task(2L,"Project management", "Creation of a website for a client", LocalDate.of(2023, 7, 20), "Completed");
    //@Test
    //@DisplayName("should return 404 if person is not found")
    //void should_return_404_if_person_is_not_found() throws Exception {
        // given
       // doReturn(null).when(taskService).findById(anyLong());

        // when
       // mockMvc.perform(get("/api/v1/persons/123"))
                // then
               // .andExpect(status().isNotFound());
    }
//}
