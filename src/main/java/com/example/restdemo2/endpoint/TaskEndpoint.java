package com.example.restdemo2.endpoint;

import com.example.restdemo2.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api_v1/task")
public class TaskEndpoint {
    @Autowired
    TaskService taskService;

    @GetMapping
    public ResponseEntity<Object> tasks(@RequestParam("personId") Long personId) {
        return taskService.getAll(personId);
    }
}
