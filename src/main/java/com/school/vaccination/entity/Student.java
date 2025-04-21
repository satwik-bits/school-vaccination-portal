package com.school.vaccination.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "student")
@Data
@Builder
@AllArgsConstructor // <- provided by Lombok
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentIdentifier;

    private String name;

    private int age;

    private int classId;

    private String mobileNo;

    private boolean isVaccinated;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentVaccination> vaccinations;

}
