package com.app.taskscheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPlanDTO implements Serializable {

    @NotNull(message = "Project name cannot be null")
    @NotEmpty(message = "Project name cannot be empty")
    private String projectName;

    @NotNull(message = "Task list cannot be null")
    @NotEmpty(message = "Task list cannot be empty")
    private List<TaskDTO> tasks;
}
