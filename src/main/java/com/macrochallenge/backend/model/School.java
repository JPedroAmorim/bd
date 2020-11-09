package com.macrochallenge.backend.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class School {
    @Id
    @SequenceGenerator(name = "id", sequenceName = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    private Integer id;

    @OneToMany(mappedBy = "school")
    private List<Test> tests;

    @NonNull
    private String name;

    @NonNull
    private String location;

    @NonNull
    private String schoolLogoUrl;
}
