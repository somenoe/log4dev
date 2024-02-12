package stou.act3.log4dev;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
  @Autowired
  private MessageRepository messageRepository;

  public MessageService(MessageRepository messageRepository) {
    this.messageRepository = messageRepository;
  }

  public List<Message> getMessage() {
    return messageRepository.findAll();
  }

  public Message addMessage(Message message) {
    return messageRepository.save(message);
  }

  public Optional<Message> findMessageById(Integer id) {
    return messageRepository.findById(id);
  }

  public void deleteMessage(Message message) {
    messageRepository.delete(message);
  }

  public void save(Message message) {
    messageRepository.save(message);
  }

  public List<Message> listAll(String keyword) {
    if (keyword != null) {
      return messageRepository.findByMessageContainingIgnoreCase(keyword);
    }
    return messageRepository.findAll();
  }
}