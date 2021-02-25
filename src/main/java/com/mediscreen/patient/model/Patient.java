package com.mediscreen.patient.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Firstname is mandatory")
    private String firstname;

    @NotBlank(message = "Lastname is mandatory")
    private String lastname;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Birth Date is mandatory")
    @Past(message = "Birth Date must be a past date")
    private LocalDate birthDate;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Phone is mandatory")
    private String phone;

    @NotBlank
    @Size(min = 1, max = 1, message = "Please type F or M for patient's sex")
    private String sex;

    public Patient()
    {

    }

    /**
     * Constructor with params
     * @param firstname firstname
     * @param lastname lastname
     * @param sex sex
     * @param birthdate birthdate
     * @param address address
     * @param phone phone
     */
    public Patient(String firstname, String lastname, String sex, LocalDate birthdate, String address, String phone)
    {
        this.firstname =  firstname;
        this.lastname = lastname;
        this.sex = sex;
        this.address= address;
        this.phone = phone;
        this.birthDate = birthdate;
    }
}
