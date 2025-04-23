package com.school.vaccination.repository;

import com.school.vaccination.entity.StudentVaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentVaccinationRepository extends JpaRepository<StudentVaccination, String> {

    @Query(value = """
    SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
    FROM student_vaccinations sv
    JOIN vaccine_drives_table vd ON sv.drive_id = vd.id
    JOIN student_table s ON sv.student_id = s.id
    WHERE s.studentIdentifier = :studentIdentifier AND vd.name = :vaccineName
    """, nativeQuery = true)
    Long existsByStudentIdAndVaccineName(@Param("studentIdentifier") String studentIdentifier,
                                            @Param("vaccineName") String vaccineName);

}
