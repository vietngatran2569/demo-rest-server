package com.example.restdemo2.service;

import com.example.restdemo2.domain.Person;
import com.example.restdemo2.domain.rest.RESTPagination;
import com.example.restdemo2.domain.rest.RESTResponse;
import com.example.restdemo2.dto.PersonDTO;
import com.example.restdemo2.repository.PersonRepository;
import com.example.restdemo2.repository.TaskRepository;
import com.example.restdemo2.specification.PersonSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;
    @Autowired
    TaskRepository taskRepository;

    public ResponseEntity<Object> getAll(String keyword, String status, int page, int limit) {
        PersonSpecification specifications = PersonSpecification.spec();

        Optional.ofNullable(keyword).ifPresent(specifications::withName);
        Optional.ofNullable(status).ifPresent(s -> specifications.withStatus(Person.Status.valueOf(s)));

        Page<Person> pageP = personRepository.findAll(specifications.build(), PageRequest.of(page - 1, limit));

        return new ResponseEntity<>(
                RESTResponse.Builder()
                        .setStatus(HttpStatus.OK.value())
                        .setMessage("Lấy danh sách thành công!")
                        .setDatas(pageP.getContent().stream().map(PersonDTO::new).collect(Collectors.toList()))
                        .setPagination(new RESTPagination(page, limit, pageP.getTotalPages(), pageP.getTotalElements()))
                        .build(),
                HttpStatus.OK
        );
    }

    public ResponseEntity<Object> update(PersonDTO personDTO) {
        Person person = personDTO.toEntity();
        Long id = person.getId();

        RESTResponse response = RESTResponse.Builder();
        response.setStatus(HttpStatus.NOT_FOUND.value())
                .setMessage("Không tìm thấy person nào với ID =" + id);

        getExist(id).ifPresent(p -> {
            if (haveNotTask(p.getId()) || p.getStatus() == person.getStatus()) {
                response.setData(new PersonDTO(personRepository.save(person)))
                        .setMessage("Sửa thành công!")
                        .setStatus(HttpStatus.OK.value());
            } else {
                response.setMessage("Sửa thất bại. Vì Person đã có công việc(task) nên không thể thay đổi trạng thái!")
                        .setStatus(HttpStatus.BAD_REQUEST.value());
            }
        });

        return new ResponseEntity<>(
                response.build(),
                HttpStatus.OK
        );
    }

    private Optional<Person> getExist(Long id) {
        return personRepository.findById(id);
    }

    public ResponseEntity<Object> findOne(Long id) {
        ResponseEntity<Object> responseEntity;
        Person person = getExist(id).orElse(null);
        if (StringUtils.isEmpty(person)) {
            responseEntity = new ResponseEntity<>(
                    RESTResponse.Builder()
                            .setMessage("Không tìm thất Person nào với ID = " + id)
                            .setStatus(HttpStatus.NOT_FOUND.value())
                            .build(),
                    HttpStatus.NOT_FOUND
            );
        } else {
            responseEntity = new ResponseEntity<>(
                    RESTResponse.Builder()
                            .setData(new PersonDTO(person))
                            .setMessage("Lấy Person thành công!")
                            .setStatus(HttpStatus.OK.value())
                            .build(),
                    HttpStatus.OK
            );
        }
        return responseEntity;
    }

    public boolean haveNotTask(Long id) {
        return StringUtils.isEmpty(taskRepository.findFirstByPersonId(id));
    }

}
