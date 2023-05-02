package Htwberlin.webtech;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class TaskController {

    @Autowired
    TaskService service;

    Logger logger = LoggerFactory.getLogger(TaskController.class);

    @PostMapping("/things")
    public Task createThing(@RequestBody Task task) {
        return service.save(task);
    }

    @GetMapping("/task/{id}")
    public Task getThing(@PathVariable String id) {
        logger.info("GET request on route things with {}", id);
        Long thingId = Long.parseLong(id);
        return service.get(thingId);
    }

    @GetMapping("/task")
    public List<Task> getAllThings() {
        return service.getAll();
    }

}
