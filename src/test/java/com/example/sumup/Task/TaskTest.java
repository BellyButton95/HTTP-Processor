package com.example.sumup.Task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private final static String JSON = "{\"name\":\"task-2\",\"command\":\"cat /tmp/file1\",\"requires\":[\"task-3\"]}";
    private final static String EXPECTED_JSON = "{\"name\":\"task-2\",\"command\":\"cat /tmp/file1\"}";
    private final static Task EXPECTED_OBJECT = new Task("task-2", "cat /tmp/file1", new String[]{"task-3"});
    private final static Task EXPECTED_OBJECT_EQUALS = new Task("task-2", "cat /tmp/file1", new String[]{"task-3"});
    private final static Task DIFF_OBJECT = new Task("task-3", "cat /tmp/file1", new String[]{"task-3"});
    private final static Task DIFF_OBJECT_NULL = new Task("task-3", "cat /tmp/file1", null);
    private final static Task DIFF_OBJECT_NULL1 = new Task("task-2", "cat /tmp/file1", null);

    private final static ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void testJsonFormat() throws JsonProcessingException {
        assertEquals(EXPECTED_JSON, MAPPER.writeValueAsString(EXPECTED_OBJECT));
        assertEquals(EXPECTED_OBJECT, MAPPER.readValue(JSON, Task.class));
    }

    @Test
    void testEquals() {
        assertEquals(EXPECTED_OBJECT, EXPECTED_OBJECT);
        assertEquals(EXPECTED_OBJECT, EXPECTED_OBJECT_EQUALS);
        assertNotEquals(EXPECTED_OBJECT, DIFF_OBJECT);
        assertNotEquals(EXPECTED_OBJECT, DIFF_OBJECT_NULL);
        assertNotEquals(EXPECTED_OBJECT, null);
        assertNotEquals(DIFF_OBJECT_NULL, DIFF_OBJECT_NULL1);
        assertNotEquals(EXPECTED_OBJECT, JSON);
    }

    @Test
    void testHashCode(){
        assertEquals(EXPECTED_OBJECT.hashCode(), EXPECTED_OBJECT.hashCode());
        assertEquals(EXPECTED_OBJECT.hashCode(), EXPECTED_OBJECT_EQUALS.hashCode());
        assertNotEquals(EXPECTED_OBJECT.hashCode(), DIFF_OBJECT.hashCode());
        assertNotEquals(EXPECTED_OBJECT.hashCode(), DIFF_OBJECT_NULL.hashCode());
    }
}