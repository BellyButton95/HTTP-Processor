package com.example.sumup.Rest;

import com.example.sumup.Task.Task;
import com.example.sumup.Task.Tasks;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ControllerTest{

    private final static String EXPECTED_BASH = "#!/usr/bin/env bash \ncat /tmp/file1\necho 'Hello World!' > /tmp/file1\ntouch /tmp/file1\n";
    private final static String ERROR_BASH = "Circular dependency found between: task-3 and task-2";
    private final static Task TASK1 = new Task("task-1", "touch /tmp/file1", new String[]{"task-2"});
    private final static Task TASK2 = new Task("task-2", "echo 'Hello World!' > /tmp/file1", new String[]{"task-3"});
    private final static Task TASK3 = new Task("task-3", "cat /tmp/file1", null);
    private final static Task TASK3_CYCLE = new Task("task-3", "cat /tmp/file1", new String[]{"task-2"});
    private static final Tasks TASKS_CORRECT = new Tasks(Arrays.asList(TASK1, TASK2, TASK3));
    private static final Tasks TASKS_CYCLE_ERROR = new Tasks(Arrays.asList(TASK1, TASK2, TASK3_CYCLE));
    private static final Controller CONTROLLER = new Controller();

    @Test
    void jsonOutput() {
        Set<Task> sortedTasks = CONTROLLER.jsonOutput(TASKS_CORRECT);
        Iterator iterator = sortedTasks.iterator();
        assertEquals(TASK3, iterator.next());
        assertEquals(TASK2, iterator.next());
        assertEquals(TASK1, iterator.next());
    }

    @Test
    void bashOutput() {
        String bashCommands = CONTROLLER.bashOutput(TASKS_CORRECT);
        assertEquals(EXPECTED_BASH, bashCommands);

        String bashError = CONTROLLER.bashOutput(TASKS_CYCLE_ERROR);
        assertEquals(ERROR_BASH, bashError);
    }
}