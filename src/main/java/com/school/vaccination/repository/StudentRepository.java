package com.school.vaccination.repository;

import com.school.vaccination.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    @Query("SELECT COUNT(s) FROM Student s")
    long countTotalStudents();

    @Query("SELECT COUNT(s) FROM Student s WHERE s.isVaccinated = true")
    long countVaccinatedStudents();

    Student findByStudentIdentifier(String studentIdentifier);

    void deleteByStudentIdentifier(String studentIdentifier);

    @Query("SELECT s FROM Student s WHERE s.studentIdentifier = :studentIdentifier")
    Page<Student> findByAnyIdentifier(@Param("studentIdentifier") String studentIdentifier, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.name = :name")
    Page<Student> findByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.classId = :classId")
    Page<Student> findByClassId(@Param("classId") int classId, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.isVaccinated = true")
    Page<Student> findByIsVaccinated(@Param("isVaccinated") boolean isVaccinated, Pageable pageable);

}
