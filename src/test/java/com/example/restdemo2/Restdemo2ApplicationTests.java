package com.example.restdemo2;

import com.example.restdemo2.domain.Person;
import com.example.restdemo2.repository.PersonRepository;
import com.example.restdemo2.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class Restdemo2ApplicationTests {

    @Autowired
    TaskRepository repository;

    @Test
    void contextLoads() {
        Assert.isNull(repository.findFirstByPersonId(5L));
    }

}
