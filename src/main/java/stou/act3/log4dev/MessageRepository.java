package stou.act3.log4dev;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
  List<Message> findByMessageContainingIgnoreCase(String message);
}
