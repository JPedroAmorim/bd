package com.macrochallenge.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Test {
    @Id
    @SequenceGenerator(name = "id", sequenceName = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    private Integer id;

    @ManyToOne
    private School school;

    @OneToMany(mappedBy = "test")
    @JsonIgnore
    private List<Question> questions;

    @OneToMany(mappedBy = "test")
    @JsonIgnore
    private List<Results> results;

    @NonNull
    private String name;

    @NonNull
    private String year;
}
