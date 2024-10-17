package com.app.taskscheduler.service;

import com.app.taskscheduler.dto.ProjectPlanDTO;
import com.app.taskscheduler.dto.TaskDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @InjectMocks
    private ScheduleService scheduleService;

    private ProjectPlanDTO projectPlan;

    @BeforeEach
    public void setUp() {
        projectPlan = new ProjectPlanDTO();
        projectPlan.setProjectName("Project 1");

        List<TaskDTO> tasks = new ArrayList<>();
        TaskDTO task1 = new TaskDTO("Task 1", 4);
        TaskDTO task2 = new TaskDTO("Task 2", 3);
        task2.setDependencies(List.of("Task 3"));  // Task 2 depends on Task 3

        TaskDTO task3 = new TaskDTO("Task 3", 5);
        task3.setDependencies(List.of("Task 1"));  // Task 3 depends on Task 1

        TaskDTO task4 = new TaskDTO("Task 4", 4);
        task4.setDependencies(List.of("Task 2", "Task 3"));  // Task 4 depends on Task 2 and Task 3

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        projectPlan.setTasks(tasks);
    }

    @Test
    public void testCalculateSchedule_WithMultipleDependencies() {
        // Act: Call the method to calculate the schedule
        List<TaskDTO> result = scheduleService.calculateSchedule(projectPlan);

        // Assert: Validate the calculated start and end dates for each task
        TaskDTO resultTask1 = result.stream().filter(t -> t.getTaskName().equals("Task 1")).findFirst().orElse(null);
        TaskDTO resultTask2 = result.stream().filter(t -> t.getTaskName().equals("Task 2")).findFirst().orElse(null);
        TaskDTO resultTask3 = result.stream().filter(t -> t.getTaskName().equals("Task 3")).findFirst().orElse(null);
        TaskDTO resultTask4 = result.stream().filter(t -> t.getTaskName().equals("Task 4")).findFirst().orElse(null);

        // Validate Task 1 dates
        assertEquals(LocalDate.now(), resultTask1.getStartDate(), "Task 1 start date is incorrect");
        assertEquals(LocalDate.now().plusDays(4), resultTask1.getEndDate(), "Task 1 end date is incorrect");

        // Validate Task 3 dates (depends on Task 1)
        assertEquals(resultTask1.getEndDate(), resultTask3.getStartDate(), "Task 3 start date is incorrect");
        assertEquals(resultTask3.getStartDate().plusDays(5), resultTask3.getEndDate(), "Task 3 end date is incorrect");

        // Validate Task 2 dates (depends on Task 3)
        assertEquals(resultTask3.getEndDate(), resultTask2.getStartDate(), "Task 2 start date is incorrect");
        assertEquals(resultTask2.getStartDate().plusDays(3), resultTask2.getEndDate(), "Task 2 end date is incorrect");

        // Validate Task 4 dates (depends on Task 2 and Task 3)
        assertEquals(Math.max(resultTask2.getEndDate().toEpochDay(), resultTask3.getEndDate().toEpochDay()),
                resultTask4.getStartDate().toEpochDay(), "Task 4 start date is incorrect");
        assertEquals(resultTask4.getStartDate().plusDays(4), resultTask4.getEndDate(), "Task 4 end date is incorrect");
    }

    // Additional tests can be added to handle other scenarios
}
