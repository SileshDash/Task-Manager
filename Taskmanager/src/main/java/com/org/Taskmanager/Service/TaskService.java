package com.org.Taskmanager.Service;

import com.org.Taskmanager.Entity.TaskEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

@Service
public class TaskService {
    private ArrayList<TaskEntity> tasks = new ArrayList<>();
    private int taskid = 1;
    private final SimpleDateFormat deadlineFormater = new SimpleDateFormat("yyyy-MM-dd");


    public TaskEntity addTask(String title, String description, String deadline) throws ParseException {
        TaskEntity task = new TaskEntity();
        task.setId(taskid);
        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(deadlineFormater.parse(deadline));
        task.setCompleted(false);
        tasks.add(task);
        taskid++;
        return task;
    }


    public ArrayList<TaskEntity> getTasks() {
        return tasks;
    }

    public TaskEntity getTaskById(int id) {
        for (TaskEntity task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public TaskEntity updateTask(int id, String description, String deadline, Boolean completed) throws ParseException {
        TaskEntity task = getTaskById(id);
        if (task == null) {
            return null;
        }
        if (description != null) {
            task.setDescription(description);
        }
        if (deadline != null) {
            task.setDeadline(deadlineFormater.parse(deadline));
        }
        if (completed != null) {
            task.setCompleted(completed);
        }
        return task;
    }

    public TaskEntity deleteTaskById(int id) {
        Iterator<TaskEntity> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            TaskEntity task = iterator.next();
            if (task.getId() == id) {
                iterator.remove();
                return task;
            }
        }
        return null;
    }
}

