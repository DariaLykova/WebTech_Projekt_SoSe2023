package Htwberlin.webtech.Task;

import Htwberlin.webtech.Task.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

}
