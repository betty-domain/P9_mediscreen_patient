package com.mediscreen.patient.integration;

import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import javax.transaction.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:insert_TestData.sql")
})
public class PatientServiceIT {

    @Autowired
    PatientService patientService;

    @Test
    void getAllPatients()
    {
        assertThat(patientService.getAllPatients().size()).isEqualTo(2);
    }

    @Test
    void getPatient()
    {
        assertThat(patientService.getPatient(1)).isNotNull();
    }

    @Test
    void addPatient()
    {
        Patient patient = new Patient("my new patient","lastname","F", LocalDate.of(1975,5,8),"address","phone");
        assertThat(patientService.addPatient(patient)).isNotNull();
    }

    @Test
    void updatePatient()
    {
        Patient patient = new Patient(1,"updated patient","lastname","F", LocalDate.of(1985,10,8),"address","phone");
        assertThat(patientService.updatePatient(patient)).isNotNull();
    }
}
