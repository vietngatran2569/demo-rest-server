package com.example.restdemo2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    @Lob
    private String description;
    @Lob
    @NotNull
    private String image;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;
}
