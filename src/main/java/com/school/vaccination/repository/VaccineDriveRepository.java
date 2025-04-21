package com.school.vaccination.repository;

import com.school.vaccination.entity.VaccineDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineDriveRepository extends JpaRepository<VaccineDrive, String> {

    VaccineDrive findByDriveIdentifier(String driveIdentifier);

    void deleteByDriveIdentifier(String driveIdentifier);

}
