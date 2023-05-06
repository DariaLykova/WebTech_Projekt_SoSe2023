package Htwberlin.webtech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TaskService {
    @Autowired
    TaskRepository repository;
    public Task save(Task task) {
        return repository.save(task);
    }

    public Task get(Long id) {
        return repository.findById(id).orElseThrow(()-> new RuntimeException());
    }
    public List<Task> getAll() {
        Iterable<Task> iterator = repository.findAll();
        List<Task> tasks = new ArrayList<Task>();
        for (Task task : iterator)  {
            tasks.add(task);
        }

        return tasks;
    }
}