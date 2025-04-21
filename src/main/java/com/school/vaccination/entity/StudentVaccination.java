package com.school.vaccination.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_vaccinations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "vaccine_drive_id"}))
@Data
public class StudentVaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Prevent same student being vaccinated in same drive twice
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccine_drive_id", nullable = false)
    private VaccineDrive drive;

    private LocalDateTime vaccinationDate;
}
