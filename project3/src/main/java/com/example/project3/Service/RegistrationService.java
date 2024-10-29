package com.example.project3.Service;

import com.example.project3.Dto.StudentDto;
import com.example.project3.Entity.Student;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface RegistrationService {
    ResponseEntity<?> createRegistration(StudentDto studentDto);

}
