package com.example.restdemo2.specification;

import com.example.restdemo2.domain.Person;
import com.example.restdemo2.domain.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {
    private final List<Specification<Task>> taskSpecs = new ArrayList<>();

    public static TaskSpecification spec() {
        return new TaskSpecification();
    }

    public TaskSpecification withPerson(Long id) {
        taskSpecs.add(hasPerson(id));
        return this;
    }

    public Specification<Task> build() {
        return Specification.where(taskSpecs.stream().reduce(all(), Specification::and));
    }

    private Specification<Task> hasPerson(Long id) {
        return StringUtils.isEmpty(id)
                ? all()
                : ((Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            Join<Task, Person> itemNode = root.join("person");
            query.distinct(true);
            return criteriaBuilder.equal(itemNode.get("id"), id);
        });
    }

    /**
     * @return specification
     * @description khi không thỏa mãn đầu vào thì trả về search tất cả
     */
    public Specification<Task> all() {
        return (Root<Task> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> cb.equal(cb.literal(1), 1);
    }
}
