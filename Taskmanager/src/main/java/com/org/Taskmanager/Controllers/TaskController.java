package com.org.Taskmanager.Controllers;

import com.org.Taskmanager.DTO.CreateTaskDTO;
import com.org.Taskmanager.DTO.ErrorResponseDTO;
import com.org.Taskmanager.DTO.UpdateTaskDTO;
import com.org.Taskmanager.Entity.TaskEntity;
import com.org.Taskmanager.Service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("")
    public ResponseEntity<List<TaskEntity>> getTask(){
      var tasks= taskService.getTasks();
      return  ResponseEntity.ok(tasks);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable("id") Integer id){
        var task=taskService.getTaskById(id);
        if (task==null){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(task);
    }
    @PostMapping("")
    public ResponseEntity<TaskEntity> addTask(@RequestBody CreateTaskDTO body) throws ParseException {
        var task= taskService.addTask(body.title,body.description,body.deadline);
        return ResponseEntity.ok(task);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable("id") Integer id,@RequestBody UpdateTaskDTO body) throws ParseException {
        var task = taskService.updateTask(id,body.getDescription(),body.getDeadline(),body.getCompleted());
        if (task==null){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(task);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<TaskEntity> deleteTaskById(@PathVariable("id") Integer id){
        var task=  taskService.deleteTaskById(id);
        if (task== null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @ExceptionHandler(Exception.class)
    public  ResponseEntity<ErrorResponseDTO> handelErrors(Exception e){
        if (e instanceof ParseException){
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("invalid date format"));
        }
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO("Internal Server Error"));
    }

}
