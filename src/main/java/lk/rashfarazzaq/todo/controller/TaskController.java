package lk.rashfarazzaq.todo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lk.rashfarazzaq.todo.exception.ResourceNotFoundException;
import lk.rashfarazzaq.todo.model.Task;
import lk.rashfarazzaq.todo.repository.TaskRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @PostMapping("/tasks")
    public Task createTask(@Valid @RequestBody Task task) {
        return taskRepository.save(task);
    }

    @GetMapping("/tasks/{taskId}")
    public Task getTaskById(@PathVariable(value = "taskId") Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "taskId", taskId));
    }

    @PutMapping("/tasks/{taskId}")
    public Task updateTask(@PathVariable(value = "taskId") Long taskId,
                                           @Valid @RequestBody Task taskDetails) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "taskId", taskId));

        task.setTaskName(taskDetails.getTaskName());
        task.setTaskDescription(taskDetails.getTaskDescription());
        task.setTaskStatus(taskDetails.getTaskStatus());

        Task updatedTask = taskRepository.save(task);
        return updatedTask;
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable(value = "taskId") Long taskId) {
        Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException("Task", "taskId", taskId));

        taskRepository.delete(task);

        return ResponseEntity.ok().build();
    }
}
