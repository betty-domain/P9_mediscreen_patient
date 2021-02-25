package com.mediscreen.patient.controller;

import com.mediscreen.patient.exceptions.PatientErrorException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    /**
     * Get all patients
     *
     * @return List of Patients
     */
    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PostMapping("/patient/add")
    public Patient addPatient(@RequestParam String family, @RequestParam String given, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthdate, @RequestParam String sex, @RequestParam String address, @RequestParam String phone) throws PatientErrorException{
        Patient patient = new Patient(given, family, sex, birthdate, address, phone);
        return patientService.addPatient(patient);
    }
}
