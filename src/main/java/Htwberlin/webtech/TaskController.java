package Htwberlin.webtech;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    Logger logger = LoggerFactory.getLogger(TaskController.class);

    @PostMapping("/add")
    public Task createTask(@RequestBody Task task) {
        return taskService.save(task);
    }

    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable String id) {
        logger.info("GET request on route tasks with {}", id);
        Long taskId = Long.parseLong(id);
        return taskService.get(taskId);
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskService.getAll();
    }

}
