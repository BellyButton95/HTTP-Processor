package com.example.sumup;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class Controller {

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Map<String,Task> test(@RequestBody Tasks tasks) {
        return tasks.getTasksMap();
    }
}
