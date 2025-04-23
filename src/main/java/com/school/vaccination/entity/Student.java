package com.school.vaccination.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "student_table")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "studentIdentifier")
    private String studentIdentifier;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "classId")
    private int classId;

    @Column(name = "mobileNo")
    private String mobileNo;

    @Column(name = "isVaccinated")
    private boolean isVaccinated;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<StudentVaccination> vaccinations;

}
