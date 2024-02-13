package stou.act3.log4dev;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
  @Autowired
  private TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public List<Task> getTask() {
    return taskRepository.findAll();
  }

  public Task addTask(Task task) {
    return taskRepository.save(task);
  }

  public Optional<Task> findTaskById(Integer id) {
    return taskRepository.findById(id);
  }

  public void deleteTask(Task task) {
    taskRepository.delete(task);
  }

  public void saveTask(Task task) {
    taskRepository.save(task);
  }

  public List<Task> listAll(String keyword) {
    if (keyword != null) {
      return taskRepository.findByTaskContainingIgnoreCase(keyword);
    }
    return taskRepository.findAll();
  }
}
