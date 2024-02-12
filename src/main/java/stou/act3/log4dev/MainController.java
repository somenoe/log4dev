package stou.act3.log4dev;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
  @Autowired
  private MessageService messageService;

  @GetMapping("/")
  public String getIndex(Model model, @Param("keyword") String keyword) {
    List<Message> listMessages = messageService.listAll(keyword);
    model.addAttribute("messages", listMessages);
    return "index";
  }

  @GetMapping("/newmessage")
  public String showMessageform() {
    return "message/add";
  }

  @GetMapping("/editmessage/{id}")
  public String showUpdateMessageForm(@PathVariable("id") Integer id, Model model) {
    Message foundMessage = messageService.findMessageById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    model.addAttribute("message", foundMessage);
    return "message/edit";
  }

  @PostMapping("/addmessage")
  public String postMessageForm(@ModelAttribute("messages") Message message) {
    messageService.addMessage(message);
    return "redirect:/";
  }

  @PostMapping("/updatemessage/{id}")
  public String updateMessage(@PathVariable("id") Integer id, @ModelAttribute("messages") Message message) {
    messageService.save(message);
    return "redirect:/";
  }

  @GetMapping("/deletemessage/{id}")
  public String deleteMessage(@PathVariable("id") Integer id, Model model) {
    Message foundMessage = messageService.findMessageById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    messageService.deleteMessage(foundMessage);
    return "redirect:/";
  }

}
