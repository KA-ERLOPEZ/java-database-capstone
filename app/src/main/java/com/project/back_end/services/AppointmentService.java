package com.project.back_end.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;


import com.project.back_end.models.Appointment;
import com.project.back_end.models.Doctor;
import com.project.back_end.models.Patient;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.DoctorRepository;
import com.project.back_end.repo.PatientRepository;

@Service
public class AppointmentService {
    // 1. **Add @Service Annotation**:
    // - To indicate that this class is a service layer class for handling business
    // logic.
    // - The `@Service` annotation should be added before the class declaration to
    // mark it as a Spring service component.
    // - Instruction: Add `@Service` above the class definition.

    // 2. **Constructor Injection for Dependencies**:
    // - The `AppointmentService` class requires several dependencies like
    // `AppointmentRepository`, `Service`, `TokenService`, `PatientRepository`, and
    // `DoctorRepository`.
    // - These dependencies should be injected through the constructor.
    // - Instruction: Ensure constructor injection is used for proper dependency
    // management in Spring.
    private final AppointmentRepository appointmentRepository;
    private final Service service;
    private final TokenService tokenService;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
            Service service,
            TokenService tokenService,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository) {
        this.service = service;
        this.appointmentRepository = appointmentRepository;
        this.tokenService = tokenService;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    // 3. **Add @Transactional Annotation for Methods that Modify Database**:
    // - The methods that modify or update the database should be annotated with
    // `@Transactional` to ensure atomicity and consistency of the operations.
    // - Instruction: Add the `@Transactional` annotation above methods that
    // interact with the database, especially those modifying data.

    // 4. **Book Appointment Method**:
    // - Responsible for saving the new appointment to the database.
    // - If the save operation fails, it returns `0`; otherwise, it returns `1`.
    // - Instruction: Ensure that the method handles any exceptions and returns an
    // appropriate result code.
    public int bookAppointment(Appointment appointment) {
        try {
            appointmentRepository.save(appointment);
            return 1;
        } catch (Exception e) {
            // TODO: handle exception
            return 0;
        }
    }

    // 5. **Update Appointment Method**:
    // - This method is used to update an existing appointment based on its ID.
    // - It validates whether the patient ID matches, checks if the appointment is
    // available for updating, and ensures that the doctor is available at the
    // specified time.
    // - If the update is successful, it saves the appointment; otherwise, it
    // returns an appropriate error message.
    // - Instruction: Ensure proper validation and error handling is included for
    // appointment updates.
    @Transactional
    public ResponseEntity <Map<String, String>> updateAppointment (Appointment appointment){

        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointment.getId());

        if(optionalAppointment.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Message", "Appointment not found"));
        }

        int validateAppointment= service.validateAppointment(appointment);

        if(validateAppointment == 0){

        }

        return Map.of("appointments", appointments);
        
    }

    // 8. **Change Status Method**:
    // - This method updates the status of an appointment by changing its value in
    // the database.
    // - It should be annotated with `@Transactional` to ensure the operation is
    // executed in a single transaction.
    // - Instruction: Add `@Transactional` before this method to ensure atomicity
    // when updating appointment status.
    @Transactional
    public Map<String, Boolean> changeStatus(int status, Long id){
        try {
            appointmentRepository.updateStatus(status, id);
            return Map.of("Success", true);
        } catch (Exception e) {
            return Map.of("Success", false);
        }
    }

    public Map<String, Object> getAppointment(String pname, LocalDate date, String token){

        String emailToken = tokenService.extractEmail(token);

        Doctor doctor = doctorRepository.findByEmail(emailToken);

        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctor.getId(), date.atStartOfDay().plusHours(7), date.atStartOfDay().plusHours(17));

        return Map.of("Appointments", appointments);
    }

    public ResponseEntity<Map<String,String>>cancelAppointment(Long id, String token){

        boolean isValidToken = service.validateToken(token, "USER").getStatusCode() == 200 ? true : false;

        if (!isValidToken) {
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("Message", "UNAUTHORIZED"));
        }

    }

}
