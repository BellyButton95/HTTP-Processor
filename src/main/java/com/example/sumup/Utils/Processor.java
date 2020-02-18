package com.example.sumup.Utils;

import com.example.sumup.Task.Task;
import com.example.sumup.Task.TaskError;
import com.example.sumup.Task.Tasks;
import com.google.common.collect.Sets;

import java.util.*;

public class Processor {
    
    public static Set<Task> process(Tasks tasks){
        if(tasks.getError().isPresent()){
            return tasks.getError().get();
        }
        LinkedHashSet<Task> output = Sets.newLinkedHashSet();
        Set<Task> visited = Sets.newHashSet();
        Map<String, Task> tasksMap = tasks.getTasksMap();
        for(Task task : tasks.getTasks()){
            visited.add(task);
            if(!addTask(task, output, visited, tasksMap)){
                return output;
            }
            visited.clear();
        }
        return output;
    }

    private static boolean addTask(Task task, Set<Task> output, Set<Task> visited, Map<String, Task> tasksMap){
        if(task.getRequires() != null){
            for(String taskKey : task.getRequires()){
                Task taskEntry = tasksMap.get(taskKey);
                if(taskEntry == null || visited.contains(taskEntry)) {
                    String errorMessage = taskEntry == null ? "Unknown task found: " + taskKey : "Circular dependency found between: " + task.getName() + " and " + taskEntry.getName();
                    output.clear();
                    output.add(new TaskError(errorMessage));
                    return false;
                }
                visited.add(taskEntry);
                if(!addTask(taskEntry, output, visited, tasksMap)){
                    return false;
                }
            }
        }
        visited.remove(task);
        output.add(task);
        return true;
    }
}
