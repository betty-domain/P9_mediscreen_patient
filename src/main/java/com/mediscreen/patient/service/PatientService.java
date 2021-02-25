package com.mediscreen.patient.service;

import com.mediscreen.patient.exceptions.PatientErrorException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private static final Logger logger = LogManager.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    /**
     * Return all existing patients
     *
     * @return
     */
    public List<Patient> getAllPatients() {
        logger.debug("Call to patientService.getAllPatients");
        return patientRepository.findAll();
    }

    /**
     * Add patient
     *
     * @param patient patient to add
     * @return patient if adding was sucessfull
     */
    public Patient addPatient(Patient patient) {
        logger.debug("Call to patientService.addPatient");
        Optional<Patient> existingPatient = patientRepository.findPatientByFirstnameAndLastnameAndBirthDateAllIgnoreCase(patient.getFirstname(), patient.getLastname(), patient.getBirthDate());
        if (existingPatient.isPresent()) {
            logger.debug("addPatient : patient already present");
            throw new PatientErrorException("Patient is already present");
        } else {
            return patientRepository.save(patient);
        }
    }
}
