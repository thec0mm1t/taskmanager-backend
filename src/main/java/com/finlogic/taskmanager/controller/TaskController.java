package com.finlogic.taskmanager.controller;

import com.finlogic.taskmanager.model.Task;
import com.finlogic.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional; // Import for Optional<Task>

@RestController // 1. Marks this class as a REST Controller
@RequestMapping("/api/tasks") // 2. Base URL path for all endpoints in this controller
@CrossOrigin(origins = "*") // 3. IMPORTANT: Allows your React app to make requests
public class TaskController {

    @Autowired // 4. Injects the TaskRepository
    private TaskRepository taskRepository;

    // 5. GET all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // 6. GET task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(ResponseEntity::ok) // If task found, return 200 OK with task
                .orElse(ResponseEntity.notFound().build()); // If not found, return 404 Not Found
    }

    // 7. POST (Create) a new task
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Sets the HTTP status to 201 Created
    public Task createTask(@RequestBody Task task) {
        task.setCompleted(false); // New tasks are initially not completed
        return taskRepository.save(task);
    }

    // 8. PUT (Update) an existing task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setDescription(taskDetails.getDescription());
                    existingTask.setCompleted(taskDetails.isCompleted());
                    // If you add more fields, update them here
                    return ResponseEntity.ok(taskRepository.save(existingTask));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 9. DELETE a task - REVISED CODE BLOCK
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id); // Get the Optional first

        if (taskOptional.isPresent()) {
            // If the task exists, delete it and return 204 No Content
            taskRepository.delete(taskOptional.get()); // .get() is safe here because we checked isPresent()
            return ResponseEntity.noContent().build(); // This returns ResponseEntity<Void>
        } else {
            // If the task does not exist, return 404 Not Found
            return ResponseEntity.notFound().build(); // This also implicitly returns ResponseEntity<Void> because build() infers from the context.
            // If it still complains, try ResponseEntity.<Void>notFound().build(); here too.
        }
    }
}
