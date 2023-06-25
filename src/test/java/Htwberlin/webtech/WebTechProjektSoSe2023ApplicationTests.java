package Htwberlin.webtech;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


//@RunWith(SpringRunner.class)
@SpringBootTest

class WebTechProjektSoSe2023ApplicationTests {
	@Autowired
	private TaskService taskService;


	private Task task1 = new Task("DBTech", "Übungsblatt3", LocalDate.of(2023, 05, 20));
	private Task task2 = new Task("DBTech", "Übungsblatt2", LocalDate.of(2023, 04, 10));

	@Test
	void contextLoads() {
	}
	@Test
	@DisplayName("should return the quantity of saved tasks")
	void getAllTasksTest() throws Exception {
		List<Task> tasks = List.of(task1, task2);
		taskService.save(task1);
		taskService.save(task2);

		int expected = 2;
		int actual = tasks.size();

		assertEquals(expected, actual);
	}
	//@Test
	//@DisplayName("")




}
