package com.mediscreen.patient.service;

import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class PatientServiceTests {

    @MockBean
    PatientRepository patientRepository;

    @Autowired
    PatientService patientService;

    @Test
    void getAllPatients()
    {
        List<Patient> patientList = new ArrayList<>();
        patientList.add(new Patient());

        when(patientRepository.findAll()).thenReturn(patientList);
        assertThat(patientService.getAllPatients().size()).isEqualTo(1);
    }

    @Test
    void addPatient_OK()
    {
        Patient patient = new Patient("firstname","lastname","M", LocalDate.of(2000,11,15),"address","phone");
        when(patientRepository.save(patient)).thenReturn(patient);

        assertThat(patientService.addPatient(patient)).isNotNull();
    }

}
