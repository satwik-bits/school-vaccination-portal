package com.school.vaccination.entity;

import com.school.vaccination.converter.IntegerListToStringConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vaccine_drives_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VaccineDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String driveIdentifier;

    private String name;

    private int availableDozes;

    @Convert(converter = IntegerListToStringConverter.class)
    private List<Integer> applicableClasses;

    private String title;

    private LocalDateTime scheduledDate;

    private String location;

    @OneToMany(mappedBy = "drive", cascade = CascadeType.ALL)
    private List<StudentVaccination> studentVaccinations;

}
