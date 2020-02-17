package com.example.sumup;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Tasks {

    private List<Task> tasks;

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Map<String,Task> getTasksMap() {
        return tasks.stream().collect(Collectors.toMap(Task::getName, Function.identity()));
    }
}
