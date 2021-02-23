package com.mediscreen.patient.controller;

import com.mediscreen.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;


}
