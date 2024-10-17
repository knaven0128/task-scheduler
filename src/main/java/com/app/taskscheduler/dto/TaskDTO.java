package com.app.taskscheduler.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO implements Serializable {

    @NotNull(message = "Task name cannot be null")
    @NotEmpty(message = "Task name cannot be empty")
    private String taskName;

    @NotNull(message = "Duration cannot be null")
    @Min(value = 1, message = "Duration must be at least 1 day")
    private int duration;

    private List<String> dependencies;

    private LocalDate startDate;
    private LocalDate endDate;

    public TaskDTO(String taskName, int duration) {
        this.taskName = taskName;
        this.duration = duration;
    }
}
