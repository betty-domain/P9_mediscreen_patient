package com.mediscreen.patient.controller;

import com.mediscreen.patient.exceptions.PatientErrorException;
import com.mediscreen.patient.exceptions.PatientNotFoundException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientController.class)
public class PatientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @Test
    void getAllPatients() throws Exception
    {
        List<Patient> patientList = new ArrayList<>();
        patientList.add(new Patient());

        when(patientService.getAllPatients()).thenReturn(patientList);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/patients").
                contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).
                andExpect(status().isOk()).
                andExpect(jsonPath("$").isArray());
    }

    @Test
    void addPatient_StatusOk() throws Exception
    {
        Patient patient = new Patient("firstname","lastname","M", LocalDate.of(2000,1,15),"address","phone");

        when(patientService.addPatient(patient)).thenReturn(patient);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/patient/add").
                contentType(MediaType.APPLICATION_JSON).param("family",patient.getLastname())
                .param("given",patient.getFirstname())
                .param("dob","2000-01-15")
                .param("sex",patient.getSex())
                .param("address",patient.getAddress())
                .param("phone",patient.getPhone());

        mockMvc.perform(builder).
                andExpect(status().isOk());
    }

    @Test
    void addPatient_BadRequest() throws Exception
    {
        Patient patient = new Patient("firstname","lastname","M", LocalDate.of(2000,1,15),"address","phone");

        given(patientService.addPatient(patient)).willThrow(new PatientErrorException("Exception Message"));


        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/patient/add").
                contentType(MediaType.APPLICATION_JSON).param("family",patient.getLastname())
                .param("given",patient.getFirstname())
                .param("dob","2000-01-15")
                .param("sex",patient.getSex())
                .param("address",patient.getAddress())
                .param("phone",patient.getPhone());

        mockMvc.perform(builder).
                andExpect(status().isBadRequest());
    }

    @Test
    void updatePatient_StatusOk() throws Exception
    {
        Patient patient = new Patient(1,"firstname","lastname","M", LocalDate.of(2000,1,15),"address","phone");

        when(patientService.updatePatient(patient)).thenReturn(patient);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/patient/update").
                contentType(MediaType.APPLICATION_JSON).param("family",patient.getLastname())
                .param("id",patient.getId().toString())
                .param("given",patient.getFirstname())
                .param("dob","2000-01-15")
                .param("sex",patient.getSex())
                .param("address",patient.getAddress())
                .param("phone",patient.getPhone());

        mockMvc.perform(builder).
                andExpect(status().isOk());
    }

    @Test
    void updatePatient_BadRequest() throws Exception
    {
        Patient patient = new Patient(1,"firstname","lastname","M", LocalDate.of(2000,1,15),"address","phone");

        given(patientService.updatePatient(patient)).willThrow(new PatientErrorException("Exception Message"));


        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/patient/update").
                contentType(MediaType.APPLICATION_JSON).param("family",patient.getLastname())
                .param("id",patient.getId().toString())
                .param("given",patient.getFirstname())
                .param("dob","2000-01-15")
                .param("sex",patient.getSex())
                .param("address",patient.getAddress())
                .param("phone",patient.getPhone());

        mockMvc.perform(builder).
                andExpect(status().isBadRequest());
    }
    @Test
    void getPatient_StatusOk() throws Exception
    {
        Patient patient = new Patient(1,"firstname","lastname","M", LocalDate.of(2000,1,15),"address","phone");

        when(patientService.getPatient(patient.getId())).thenReturn(patient);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/patient").
                contentType(MediaType.APPLICATION_JSON)
                .param("id",patient.getId().toString());

        mockMvc.perform(builder).
                andExpect(status().isOk());
    }

    @Test
    void getPatient_NotFound() throws Exception
    {
        Patient patient = new Patient(1,"firstname","lastname","M", LocalDate.of(2000,1,15),"address","phone");

        given(patientService.getPatient(patient.getId())).willThrow(new PatientNotFoundException("Exception Message"));


        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/patient").
                contentType(MediaType.APPLICATION_JSON)
                .param("id",patient.getId().toString());

        mockMvc.perform(builder).
                andExpect(status().isNotFound());
    }
}
