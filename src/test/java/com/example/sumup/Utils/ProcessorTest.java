package com.example.sumup.Utils;

import com.example.sumup.Task.Task;
import com.example.sumup.Task.TaskError;
import com.example.sumup.Task.Tasks;
import org.apache.tomcat.jni.Proc;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProcessorTest {

    private final static String CYCLE_ERROR = "Circular dependency found between: task-3 and task-2";
    private final static String UNKNOWN_ERROR = "Unknown task found: task-23";
    private final static String DUPLICATE_ERROR = "Found duplicate task: task-3";
    private final static Task TASK1 = new Task("task-1", "touch /tmp/file1", new String[]{"task-2"});
    private final static Task TASK2 = new Task("task-2", "echo 'Hello World!' > /tmp/file1", new String[]{"task-3"});
    private final static Task TASK3 = new Task("task-3", "cat /tmp/file1", null);
    private final static Task TASK3_CYCLE = new Task("task-3", "cat /tmp/file1", new String[]{"task-2"});
    private final static Task TASK3_UKNOWN = new Task("task-3", "cat /tmp/file1", new String[]{"task-23"});
    private final static Task TASK3_DUPLICATE = new Task("task-3", "cat /tmp/file1", null);
    private static final Tasks TASKS_CORRECT = new Tasks(Arrays.asList(TASK1, TASK2, TASK3));
    private static final Tasks TASKS_CYCLE_ERROR = new Tasks(Arrays.asList(TASK1, TASK2, TASK3_CYCLE));
    private static final Tasks TASKS_UNKNOWN_ERROR = new Tasks(Arrays.asList(TASK1, TASK2, TASK3_UKNOWN));
    private static final Tasks TASKS_DUPLICATE_ERROR = new Tasks(Arrays.asList(TASK1, TASK2, TASK3, TASK3_DUPLICATE));


    @Test
    void process() {
        Set<Task> sortedTasks = Processor.process(TASKS_CORRECT);
        Iterator iterator = sortedTasks.iterator();
        assertEquals(TASK3, iterator.next());
        assertEquals(TASK2, iterator.next());
        assertEquals(TASK1, iterator.next());

        Set<Task> errorTasksCycle = Processor.process(TASKS_CYCLE_ERROR);
        assertEquals(1, errorTasksCycle.size());
        assertTrue(errorTasksCycle.iterator().next().isError());
        assertEquals(CYCLE_ERROR, errorTasksCycle.iterator().next().getName());

        Set<Task> errorTasksUnknown = Processor.process(TASKS_UNKNOWN_ERROR);
        assertEquals(1, errorTasksUnknown.size());
        assertTrue(errorTasksUnknown.iterator().next().isError());
        assertEquals(UNKNOWN_ERROR, errorTasksUnknown.iterator().next().getName());

        Set<Task> errorTasksDuplicate = Processor.process(TASKS_DUPLICATE_ERROR);
        assertEquals(1, errorTasksDuplicate.size());
        assertTrue(errorTasksDuplicate.iterator().next().isError());
        assertEquals(DUPLICATE_ERROR, errorTasksDuplicate.iterator().next().getName());
    }
}