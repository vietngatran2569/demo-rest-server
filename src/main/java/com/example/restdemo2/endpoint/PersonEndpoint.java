package com.example.restdemo2.endpoint;

import com.example.restdemo2.dto.PersonDTO;
import com.example.restdemo2.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api_v1/person")
public class PersonEndpoint {

    @Autowired
    PersonService personService;

    @GetMapping
    public ResponseEntity<Object> persons(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int limit,
            @RequestParam(value = "status", required = false) String status
    ) {
        return personService.getAll(keyword, status, page, limit);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody PersonDTO personDTO) {
        return personService.update(personDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> show(@PathVariable("id") Long id) {
        return personService.findOne(id);
    }

}
