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
  private TaskService taskService;

  @GetMapping("/")
  public String getIndex(Model model, @Param("keyword") String keyword) {
    List<Task> tasks = taskService.listAll(keyword);
    model.addAttribute("tasks", tasks);
    return "index";
  }

  @GetMapping("/add")
  public String showCreatePage() {
    return "form/add";
  }

  @GetMapping("/edit/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    Task foundTask = taskService.findTaskById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
    model.addAttribute("task", foundTask);
    return "form/edit";
  }

  @PostMapping("/add")
  public String postAddForm(@ModelAttribute("tasks") Task task) {
    taskService.addTask(task);
    return "redirect:/";
  }

  @PostMapping("/update/{id}")
  public String postUpdateForm(@PathVariable("id") Integer id, @ModelAttribute("tasks") Task task) {
    taskService.saveTask(task);
    return "redirect:/";
  }

  @GetMapping("/delete/{id}")
  public String deleteMessage(@PathVariable("id") Integer id, Model model) {
    Task foundTask = taskService.findTaskById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
    taskService.deleteTask(foundTask);
    return "redirect:/";
  }

}
