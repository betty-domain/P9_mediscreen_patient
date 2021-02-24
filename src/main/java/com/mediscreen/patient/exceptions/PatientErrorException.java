package com.mediscreen.patient.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class PatientErrorException extends  RuntimeException{
    public PatientErrorException(String message)
    {
        super(message);
    }

}
