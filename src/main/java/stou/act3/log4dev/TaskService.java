package stou.act3.log4dev;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
  @Autowired
  private TaskRepository taskRepository;

  public TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public List<Task> getTask() {
    return taskRepository.findAll(Sort.by(Sort.Direction.DESC, "time"));
  }

  public Task lastTask() {
    return taskRepository.findFirstByOrderByIdDesc();
  }

  public Task addTask(Task task) {
    String currentTimeString = new Date().toString().substring(11, 19);
    task.setTime(currentTimeString);

    Task lastTask = taskRepository.findFirstByOrderByIdDesc();
    if (lastTask == null)
      return taskRepository.save(task);
    String lastTimeString = lastTask.getTime();

    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    try {
      Date date1 = timeFormat.parse(lastTimeString);
      Date date2 = timeFormat.parse(currentTimeString);

      // milliseconds between the two dates
      long duration = date2.getTime() - date1.getTime();
      timeFormat = new SimpleDateFormat("HH:mm");
      lastTask.setDuration(duration);
      taskRepository.save(lastTask);

      return taskRepository.save(task);

    } catch (ParseException e) {
      // handle the exception, e.g., log an error or throw a runtime exception
      throw new RuntimeException(e);
    }

  }

  @SuppressWarnings("null")
  public Optional<Task> findTaskById(Integer id) {
    return taskRepository.findById(id);
  }

  @SuppressWarnings("null")
  public void deleteTask(Task task) {
    taskRepository.delete(task);
  }

  @SuppressWarnings("null")
  public void saveTask(Task task) {
    taskRepository.save(task);
  }

  public List<Task> listAll(String keyword) {
    if (keyword != null) {
      return taskRepository.findByTaskContainingIgnoreCaseOrderByTimeDesc(keyword);
    }
    return this.getTask();
  }
}
