package com.mediscreen.patient.repository;

import com.mediscreen.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> findPatientByFirstnameAndLastnameAndBirthDateAllIgnoreCase(String firstname, String lastname, LocalDate birthdate);

    Optional<Patient> findByLastnameIgnoreCase(String lastname);
}
