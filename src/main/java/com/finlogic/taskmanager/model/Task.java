package com.finlogic.taskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // 1. Marks this class as a JPA entity
@Data // 2. Lombok: Generates getters, setters, toString(), equals(), hashCode()
@NoArgsConstructor // 3. Lombok: Generates a no-argument constructor
@AllArgsConstructor // 4. Lombok: Generates a constructor with all fields
public class Task {

    @Id // 5. Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 6. Configures ID generation
    private Long id; // The unique identifier for each task

    private String description; // The content/description of the task
    private boolean completed;  // Status of the task (true if completed, false otherwise)

}
