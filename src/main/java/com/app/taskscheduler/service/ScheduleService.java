package com.app.taskscheduler.service;

import com.app.taskscheduler.dto.ProjectPlanDTO;
import com.app.taskscheduler.dto.TaskDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ScheduleService {

    public List<TaskDTO> calculateSchedule(ProjectPlanDTO projectPlan) {

        Map<String, TaskDTO> taskMap = new HashMap<>();
        Map<String, LocalDate> completionDates = new HashMap<>();
        Set<String> visitedTasks = new HashSet<>();

        // Populate task map
        projectPlan.getTasks().forEach(task -> taskMap.put(task.getTaskName(), task));

        // Process the tasks and calculate schedule
        projectPlan.getTasks().forEach(task -> {
            if (!visitedTasks.contains(task.getTaskName())) {
                calculateTaskSchedule(task, taskMap, completionDates, visitedTasks);
            }
        });

        return new ArrayList<>(taskMap.values());
    }

    private void calculateTaskSchedule(TaskDTO task, Map<String, TaskDTO> taskMap,
                                       Map<String, LocalDate> completionDates, Set<String> visitedTasks) {
        if (visitedTasks.contains(task.getTaskName())) return;

        // Safely handle null dependencies using Optional
        LocalDate startDate = Optional.ofNullable(task.getDependencies())
                .orElse(Collections.emptyList())  // Treat null dependencies as an empty list
                .stream()
                .map(taskMap::get)
                .filter(Objects::nonNull)
                .peek(depTask -> {
                    if (!visitedTasks.contains(depTask.getTaskName())) {
                        calculateTaskSchedule(depTask, taskMap, completionDates, visitedTasks);
                    }
                })
                .map(depTask -> completionDates.get(depTask.getTaskName()))
                .max(LocalDate::compareTo)
                .orElse(LocalDate.now());

        LocalDate endDate = startDate.plusDays(task.getDuration());
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        completionDates.put(task.getTaskName(), endDate);
        visitedTasks.add(task.getTaskName());
    }
}
