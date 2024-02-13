package stou.act3.log4dev;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

  @RequestMapping("/t")
  public String showTimeDiff(Model model) {
    String value = "";
    String time1 = "15:16:04";
    String time2 = "17:20:01";

    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    try {
      Date date1 = timeFormat.parse(time1);
      Date date2 = timeFormat.parse(time2);

      // milliseconds between the two dates
      long difference = date2.getTime() - date1.getTime();

      timeFormat = new SimpleDateFormat("HH:mm");
      String formattedTime = timeFormat.format(new Date(difference));

      value = formattedTime;
    } catch (ParseException e) {
      // handle the exception, e.g., log an error or throw a runtime exception
      value = e.getMessage();
    }
    // model.addAttribute("value", value);
    model.addAttribute("value", taskService.lastTask());
    return "test";

  }

  @GetMapping("/edit/{id}")
  public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    Task foundTask = taskService.findTaskById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
    model.addAttribute("task", foundTask);
    return "edit";
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
