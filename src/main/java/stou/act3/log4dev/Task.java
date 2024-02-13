package stou.act3.log4dev;

import jakarta.persistence.*;

@Entity
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String time;
  private String task;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getTask() {
    return task;
  }

  public void setTask(String task) {
    this.task = task;
  }

  @Override
  public String toString() {
    return "Task [id=" + id + ", time=" + time + ", task=" + task + "]";
  }
}