package com.mediscreen.patient.controller;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    /**
     * Get all patients
     * @return List of Patients
     */
    @GetMapping("/patients")
    public List<Patient> getAllPatients()
    {
        return patientService.getAllPatients();
    }
}
