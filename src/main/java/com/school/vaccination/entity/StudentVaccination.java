package com.school.vaccination.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_vaccinations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "drive_id"})
)
@Data
public class StudentVaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drive_id", nullable = false)
    private VaccineDrive drive;

    private LocalDateTime vaccinationDate;
}
