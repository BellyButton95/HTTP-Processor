package com.example.sumup.Task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskErrorTest {

    private final static String EXPECTED_JSON = "{\"errorMsg\":\"error\"}";
    private final static Task OBJECT = new TaskError("error");

    private final static ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void testJsonFormat() throws JsonProcessingException {
        assertEquals(EXPECTED_JSON, MAPPER.writeValueAsString(OBJECT));
    }
}