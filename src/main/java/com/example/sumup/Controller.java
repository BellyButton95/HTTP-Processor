package com.example.sumup;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Tasks test(@RequestBody Tasks tasks) {
        return tasks;
    }
}
