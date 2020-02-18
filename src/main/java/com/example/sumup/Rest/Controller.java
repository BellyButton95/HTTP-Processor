package com.example.sumup.Rest;

import com.example.sumup.Task.Task;
import com.example.sumup.Task.Tasks;
import com.example.sumup.Utils.Processor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class Controller {

    private static final String BASH_HEADER = "#!/usr/bin/env bash \n";

    @RequestMapping(value = "/jsonOutput", method = RequestMethod.POST)
    public Set<Task> jsonOutput(@RequestBody Tasks tasks) {
        return Processor.process(tasks);
    }

    @RequestMapping(value = "/bashOutput", method = RequestMethod.POST)
    public String bashOutput(@RequestBody Tasks tasks) {
        Set<Task> sortedTasks = Processor.process(tasks);
        if(sortedTasks.iterator().next().isError()){
            return sortedTasks.iterator().next().getName();
        }
        StringBuilder sb = new StringBuilder(BASH_HEADER);
        sortedTasks.forEach(t -> sb.append(t.getCommand()).append("\n"));
        return sb.toString();
    }
}
