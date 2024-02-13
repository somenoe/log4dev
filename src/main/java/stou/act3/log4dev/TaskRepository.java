package stou.act3.log4dev;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
  List<Task> findByTaskContainingIgnoreCaseOrderByTimeDesc(String task);

  Task findFirstByOrderByIdDesc();

  List<Task> findByOrderByIdAsc();
}
