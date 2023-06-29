package Htwberlin.webtech;


import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository repository;

    Logger logger = LoggerFactory.getLogger(TaskController.class);


    @PostMapping("/add")
    public Task createTask(@RequestBody Task task) {
        return taskService.save(task);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Task> editTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task updateTask = repository.findById(id)
                .orElseThrow(() -> new ServiceException("Task does not exist with id: " + id));
        updateTask.setName(updatedTask.getName());
        updateTask.setDescription(updatedTask.getDescription());
        updateTask.setDeadline(updatedTask.getDeadline());
        updateTask.setCompleted(updatedTask.getCompleted());

        repository.save(updateTask);

        return ResponseEntity.ok(updateTask);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable String id){
        taskService.delete(Long.valueOf(id));
        return "The task with id " + id + " deleted";
    }

    @GetMapping("/task/{id}")
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
