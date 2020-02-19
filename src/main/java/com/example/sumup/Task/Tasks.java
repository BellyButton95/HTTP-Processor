package com.example.sumup.Task;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;

import java.util.*;

public class Tasks {

    private List<Task> tasks;
    private Map<String, Task> tasksMap;
    private boolean isError;

    @JsonCreator
    public Tasks(@JsonProperty(value="tasks", required=true) List<Task> tasks){
        this.tasks = tasks;
        this.isError = false;
        this.tasksMap = getTasksMap();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Map<String, Task> getTasksMap() {
        if(tasksMap != null){
            return tasksMap;
        }
        Map<String, Task> map = Maps.newHashMap();
        for(Task task : tasks){
            if(map.containsKey(task.getName())){
                map.clear();
                map.put("error", new TaskError("Found duplicate task: " + task.getName()));
                isError = true;
                return map;
            }
            map.put(task.getName(), task);
        }
        return map;
    }

    public Optional<Set<Task>> getError(){
        return isError ? Optional.of(new HashSet<>(tasksMap.values())) : Optional.empty();
    }
}
