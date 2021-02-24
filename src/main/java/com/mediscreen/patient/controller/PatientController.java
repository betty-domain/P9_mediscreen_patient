package com.mediscreen.patient.controller;

import com.mediscreen.patient.exceptions.PatientErrorException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
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

    @PostMapping("/patient/add")
    public ResponseEntity<Patient> addPatient(@RequestParam String family, @RequestParam String given, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate birthdate, @RequestParam String sex, @RequestParam String address, @RequestParam String phone)
    {
        Patient patient = new Patient(given,family,sex,birthdate,address,phone);

        try {
            Patient patientAdded = patientService.addPatient(patient);
            URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/patient/{id}").buildAndExpand(patientAdded.getId()).toUri();
            return  ResponseEntity.created(location).body(patientAdded);
        }
        catch (PatientErrorException e)
        {
            return ResponseEntity.badRequest().build();
        }
    }
}
