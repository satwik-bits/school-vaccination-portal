package com.school.vaccination.repository;

import com.school.vaccination.entity.StudentVaccination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentVaccinationRepository extends JpaRepository<StudentVaccination, String> {

    @Query(value = """
    SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
    FROM student_vaccinations sv
    JOIN vaccine_drives vd ON sv.drive_id = vd.id
    JOIN student s ON sv.student_id = s.id
    WHERE s.identifier = :studentIdentifier AND vd.vaccine_name = :vaccineName
    """, nativeQuery = true)
    boolean existsByStudentIdAndVaccineName(@Param("studentIdentifier") String studentIdentifier,
                                            @Param("vaccineName") String vaccineName);

}
