package com.example.sumup.Task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TasksTest {
    private final static String JSON = "{\"tasks\":[{\"name\":\"task-1\",\"command\":\"touch /tmp/file1\",\"requires\":[\"task-2\"]},{\"name\":\"task-2\",\"command\":\"cat /tmp/file1\",\"requires\":[\"task-3\"]},{\"name\":\"task-3\",\"command\":\"echo 'Hello World!' > /tmp/file1\"}]}";
    private final static ObjectMapper MAPPER = new ObjectMapper();
    private final static Task TASK1 = new Task("task-1", "touch /tmp/file1", new String[]{"task-2"});
    private final static Task TASK2 = new Task("task-2", "echo 'Hello World!' > /tmp/file1", new String[]{"task-3"});
    private final static Task TASK3 = new Task("task-3", "cat /tmp/file1", null);
    private final static Task TASK3_DUPLICATE = new Task("task-3", "cat /tmp/file1", null);
    private static final Tasks TASKS_CORRECT = new Tasks(Arrays.asList(TASK1, TASK2, TASK3));
    private static final Tasks TASKS_ERROR = new Tasks(Arrays.asList(TASK1, TASK2, TASK3, TASK3_DUPLICATE));
    private static final Map<String, Task> EXPECTED_MAP = Arrays.asList(TASK1, TASK2, TASK3).stream().collect(Collectors.toMap(Task::getName, Function.identity()));

    @Test
    void testFormat() throws JsonProcessingException {
        Tasks tasks = MAPPER.readValue(JSON, Tasks.class);
        assertEquals(TASKS_CORRECT.getTasksMap().size(), tasks.getTasksMap().size());
        TASKS_CORRECT.getTasksMap().keySet().forEach(k -> assertTrue(tasks.getTasksMap().containsKey(k)));
    }

    @Test
    void getTasksMap() {
        assertEquals(EXPECTED_MAP.size(), TASKS_CORRECT.getTasksMap().size());
        TASKS_CORRECT.getTasksMap().keySet().forEach(k -> assertTrue(EXPECTED_MAP.containsKey(k)));
        assertEquals(1, TASKS_ERROR.getTasksMap().size());
    }

    @Test
    void getError() {
        assertFalse(TASKS_CORRECT.getError().isPresent());
        assertTrue(TASKS_ERROR.getError().isPresent());
        assertTrue(TASKS_ERROR.getError().get().iterator().next().isError());
    }
}