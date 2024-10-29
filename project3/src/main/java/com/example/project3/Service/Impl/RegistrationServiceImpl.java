package com.example.project3.Service.Impl;
import java.io.IOException;
import com.example.project3.Controller.RegistrationController;
import com.example.project3.Dto.StudentDto;
import com.example.project3.Entity.Student;
import com.example.project3.Repository.StudentRepository;
import com.example.project3.Service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public ResponseEntity<?> createRegistration(StudentDto registrationDto) {
        if (studentRepository.findByEmail(registrationDto.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new RegistrationController.RegistrationResponse("Email is already registered."));
        }

        // Check for duplicate phone
        if (studentRepository.findByPhone(registrationDto.getPhone()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new RegistrationController.RegistrationResponse("Phone number is already registered."));
        }

        Student student = new Student();
        student.setPrefix(registrationDto.getPrefix());
        student.setFirstname(registrationDto.getFirstname());
        student.setLastname(registrationDto.getLastname());
        student.setAddressline1(registrationDto.getAddressline1());
        student.setAdressline2(registrationDto.getAdressline2());
        student.setCity(registrationDto.getCity());
        student.setState(registrationDto.getState());
        student.setZipcode(registrationDto.getZipcode());
        student.setCountrycode(registrationDto.getCountrycode());
        student.setPhone(registrationDto.getPhone());
        student.setEmail(registrationDto.getEmail());
        student.setPassword(registrationDto.getPassword());
        student.setBachelorDegree(registrationDto.getBachelorDegree());
        student.setBachelorGPA(registrationDto.getBachelorGPA());
        student.setMd(registrationDto.getMd());
        student.setMdGPA(registrationDto.getMdGPA());
        student.setLookingForInternship(registrationDto.getLookingForInternship());
        student.setToken(registrationDto.getToken());
        try {
            byte[] resumebyte = registrationDto.getResume().getBytes();
            student.setResume(resumebyte);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new RegistrationController.RegistrationResponse("Error processing resume file"));
        }

        studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegistrationController.RegistrationResponse("Registered Successfully"));
    }
}
