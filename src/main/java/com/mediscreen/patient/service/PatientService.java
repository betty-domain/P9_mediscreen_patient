package com.mediscreen.patient.service;

import com.mediscreen.patient.exceptions.PatientErrorException;
import com.mediscreen.patient.exceptions.PatientNotFoundException;
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
     * Get patient by id
     * @param id id of patient
     * @return optional patient
     */
    public Patient getPatient(Integer id) {
        logger.debug("Call to patientService.getPatient");
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found"));
    }

    /**
     * Get patient by id
     * @param familyName familyName of patient
     * @return optional patient
     */
    public Patient getPatient(String familyName) {
        logger.debug("Call to patientService.getPatient by familyName");
        return patientRepository.findByLastnameIgnoreCase(familyName).orElseThrow(() -> new PatientNotFoundException("Patient not found"));
    }

    /**
     * Add patient
     *
     * @param patient patient to add
     * @return patient if adding was sucessfull
     */
    public Patient addPatient(Patient patient) throws PatientErrorException{
        logger.debug("Call to patientService.addPatient");
        Optional<Patient> existingPatient = patientRepository.findPatientByFirstnameAndLastnameAndBirthDateAllIgnoreCase(patient.getFirstname(), patient.getLastname(), patient.getBirthDate());
        if (existingPatient.isPresent()) {
            logger.debug("addPatient : patient already present");
            throw new PatientErrorException("Patient is already present");
        } else {
            return patientRepository.save(patient);
        }
    }

    /**
     * update patient
     * @param patient
     * @return updated patient
     */
    public Patient updatePatient(Patient patient) throws PatientErrorException, PatientNotFoundException{
        logger.debug("Call to patientService.updatePatient");
        Optional<Patient> patientToUpdate = patientRepository.findById(patient.getId());
        if (patientToUpdate.isPresent()) {
            Optional<Patient> existingPatient = patientRepository.findPatientByFirstnameAndLastnameAndBirthDateAllIgnoreCase(patient.getFirstname(),patient.getLastname(),patient.getBirthDate());
            if (existingPatient.isPresent() && !existingPatient.get().getId().equals(patient.getId()))
            {
                logger.debug("updatePatient : Patient with same firstname, lastname and birthdate already exist");
                throw new PatientErrorException("Patient with same firstname, lastname and birthdate already exist");
            }
            else {
                return patientRepository.save(patient);
            }
        } else {
            logger.debug("updatePatient : patient doesn't exist");
            throw new PatientNotFoundException("Patient not found");
        }
    }
}
