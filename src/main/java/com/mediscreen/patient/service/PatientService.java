package com.mediscreen.patient.service;

import com.mediscreen.patient.exceptions.PatientErrorException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private static final Logger logger = LogManager.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    /**
     * Return all existing patients
     * @return
     */
    public List<Patient> getAllPatients()
    {
        logger.debug("Call to patientService.getAllPatients");
        return patientRepository.findAll();
    }

    /**
     * Add patient
     * @param patient patient to add
     * @return patient if adding was sucessfull
     */
    public Patient addPatient(Patient patient) throws PatientErrorException
    {
        logger.debug("Call to patientService.addPatient");
        try
        {
            return patientRepository.save(patient);
        }
        catch (Exception exception)
        {
            logger.error("Error when adding Patient");
            throw new PatientErrorException("Error occured when adding Patient");
        }
    }
}
