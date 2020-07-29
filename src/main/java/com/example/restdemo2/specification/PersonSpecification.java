package com.example.restdemo2.specification;

import com.example.restdemo2.domain.Person;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PersonSpecification {
    private final List<Specification<Person>> personSpecs = new ArrayList<>();

    public static PersonSpecification spec() {
        return new PersonSpecification();
    }

    public PersonSpecification withStatus(Person.Status status) {
        personSpecs.add(hasStatus(status));
        return this;
    }

    public PersonSpecification withName(String name) {
        personSpecs.add(likeName(name));
        return this;
    }

    public Specification<Person> build() {
        return Specification.where(personSpecs.stream().reduce(all(), Specification::and));
    }

    private Specification<Person> hasStatus(Person.Status status) {
        return StringUtils.isEmpty(status)
                ? all()
                : ((Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            query.distinct(true);
            return criteriaBuilder.equal(root.get("status"), status);
        });
    }

    private Specification<Person> likeName(String name) {
        return StringUtils.isEmpty(name)
                ? all()
                : ((Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            query.distinct(true);
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        });
    }

    /**
     * @return specification
     * @description khi không thỏa mãn đầu vào thì trả về search tất cả
     */
    public Specification<Person> all() {
        return (Root<Person> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> cb.equal(cb.literal(1), 1);
    }
}
