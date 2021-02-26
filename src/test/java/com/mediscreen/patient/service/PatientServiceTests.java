package com.mediscreen.patient.service;

import com.mediscreen.patient.exceptions.PatientErrorException;
import com.mediscreen.patient.exceptions.PatientNotFoundException;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
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
        when(patientRepository.findPatientByFirstnameAndLastnameAndBirthDateAllIgnoreCase(anyString(),anyString(),any())).thenReturn(Optional.empty());
        when(patientRepository.save(patient)).thenReturn(patient);

        assertThat(patientService.addPatient(patient)).isNotNull();
    }

    @Test
    void addPatient_AlreadyExist()
    {
        Patient patient = new Patient("firstname","lastname","M", LocalDate.of(2000,11,15),"address","phone");
        when(patientRepository.findPatientByFirstnameAndLastnameAndBirthDateAllIgnoreCase(anyString(),anyString(),any())).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);

        Exception exception = assertThrows(PatientErrorException.class, () -> {
            patientService.addPatient(patient);
        });
        assertThat(exception.getMessage()).contains("Patient is already present");
    }

    @Test
    void updatePatient_OK()
    {
        Patient patient = new Patient(1,"firstname","lastname","M", LocalDate.of(2000,11,15),"address","phone");
        when(patientRepository.findById(anyInt())).thenReturn(Optional.of(patient));
        when(patientRepository.findPatientByFirstnameAndLastnameAndBirthDateAllIgnoreCase(anyString(),anyString(),any())).thenReturn(Optional.of(patient));
        when(patientRepository.save(patient)).thenReturn(patient);

        assertThat(patientService.updatePatient(patient)).isNotNull();
    }

    @Test
    void updatePatient_NotExist()
    {
        Patient patient = new Patient("firstname","lastname","M", LocalDate.of(2000,11,15),"address","phone");
        when(patientRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(patientRepository.save(patient)).thenReturn(patient);

        Exception exception = assertThrows(PatientNotFoundException.class, () -> {
            patientService.updatePatient(patient);
        });
        assertThat(exception.getMessage()).contains("Patient not found");
    }

    @Test
    void updatePatient_AnotherPatientIsTheSame()
    {
        Patient patient = new Patient(1,"firstname","lastname","M", LocalDate.of(2000,11,15),"address","phone");

        Patient otherPatient = new Patient(2,patient.getFirstname(),patient.getLastname(),"F", patient.getBirthDate(),"address 2","phone 2");

        when(patientRepository.findById(anyInt())).thenReturn(Optional.of(patient));
        when(patientRepository.findPatientByFirstnameAndLastnameAndBirthDateAllIgnoreCase(anyString(),anyString(),any())).thenReturn(Optional.of(otherPatient));

        Exception exception = assertThrows(PatientErrorException.class, () -> {
            patientService.updatePatient(patient);
        });
        assertThat(exception.getMessage()).contains("Patient with same firstname, lastname and birthdate already exist");
    }

    @Test
    void getPatient_OK()
    {
        Patient patient = new Patient(1,"firstname","lastname","M", LocalDate.of(2000,11,15),"address","phone");
        when(patientRepository.findById(anyInt())).thenReturn(Optional.of(patient));

        assertThat(patientService.getPatient(patient.getId())).isEqualTo(patient);
    }

    @Test
    void getPatient_NotExist()
    {
        Patient patient = new Patient("firstname","lastname","M", LocalDate.of(2000,11,15),"address","phone");
        when(patientRepository.findById(anyInt())).thenReturn(Optional.empty());

        Exception exception = assertThrows(PatientNotFoundException.class, () -> {
            patientService.getPatient(patient.getId());
        });
        assertThat(exception.getMessage()).contains("Patient not found");
    }

}
