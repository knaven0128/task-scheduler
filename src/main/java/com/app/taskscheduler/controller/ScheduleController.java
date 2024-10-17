package com.app.taskscheduler.controller;

import com.app.taskscheduler.dto.ProjectPlanDTO;
import com.app.taskscheduler.dto.TaskDTO;
import com.app.taskscheduler.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/schedule")
@Validated
public class ScheduleController {

    private final ScheduleService scheduleService;
    private List<TaskDTO> generatedSchedule;
    private String projectName;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> createProjectTasks(@RequestBody @Validated ProjectPlanDTO projectPlanDTO) {
        try {
            this.generatedSchedule = scheduleService.calculateSchedule(projectPlanDTO);
            this.projectName = projectPlanDTO.getProjectName();
        } catch (Exception e) {
            throw new ScheduleException("Error processing project plan", e);
        }

        // This will return a detailed response containing the project name
        return Map.of(
                "projectName", projectPlanDTO.getProjectName(),
                "message", "Project plan submitted and schedule generated."
        );
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> generateSchedule() {
        if (generatedSchedule == null || generatedSchedule.isEmpty()) {
            throw new ScheduleException("No schedule has been generated yet.");
        }

        // This will return a detailed response containing the project name and list of task
        return Map.of(
                "projectName", projectName,
                "schedule", generatedSchedule
        );
    }
}
