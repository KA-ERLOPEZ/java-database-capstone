package com.project.back_end.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.project.back_end.models.Appointment;
import com.project.back_end.services.AppointmentService;
import com.project.back_end.services.Service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    // 1. Set Up the Controller Class:
    // - Annotate the class with `@RestController` to define it as a REST API
    // controller.
    // - Use `@RequestMapping("/appointments")` to set a base path for all
    // appointment-related endpoints.
    // - This centralizes all routes that deal with booking, updating, retrieving,
    // and canceling appointments.

    // 2. Autowire Dependencies:
    // - Inject `AppointmentService` for handling the business logic specific to
    // appointments.
    // - Inject the general `Service` class, which provides shared functionality
    // like token validation and appointment checks.
    private final AppointmentService appointmentService;
    private final Service service;

    public AppointmentController(AppointmentService appointmentService, Service service) {
        this.appointmentService = appointmentService;
        this.service = service;
    }

    // 3. Define the `getAppointments` Method:
    // - Handles HTTP GET requests to fetch appointments based on date and patient
    // name.
    // - Takes the appointment date, patient name, and token as path variables.
    // - First validates the token for role `"doctor"` using the `Service`.
    // - If the token is valid, returns appointments for the given patient on the
    // specified date.
    // - If the token is invalid or expired, responds with the appropriate message
    // and status code.
    @GetMapping("/{date}/{patientName}/{token}")
    public ResponseEntity<?> getAppointments(@PathVariable LocalDate date, @PathVariable String patientName,
            @PathVariable String token) {

        ResponseEntity<?> responseValidateToken = this.service.validateToken(token, "doctor");
        boolean isValidToken = responseValidateToken.getStatusCode().value() == 200 ? true : false;

        if (!isValidToken) {
            return responseValidateToken;
        }

        return ResponseEntity.ok(appointmentService.getAppointments(patientName, date, token));
    }

    // 4. Define the `bookAppointment` Method:
    // - Handles HTTP POST requests to create a new appointment.
    // - Accepts a validated `Appointment` object in the request body and a token as
    // a path variable.
    // - Validates the token for the `"patient"` role.
    // - Uses service logic to validate the appointment data (e.g., check for doctor
    // availability and time conflicts).
    // - Returns success if booked, or appropriate error messages if the doctor ID
    // is invalid or the slot is already taken.

    @PostMapping("/{token}")
    public ResponseEntity<?> bookAppointment(@RequestBody Appointment appointment, @PathVariable String token) {

        int validateAppointment = service.validateAppointment(appointment);
        boolean isValidToken = service.validateToken(token, "patient").getStatusCode().value() == 200 ? true : false;

        if (validateAppointment == -1) {
            return new ResponseEntity<>(Map.of("Error", "Doctor not found"), HttpStatus.BAD_REQUEST);
        }

        if (validateAppointment == 0) {
            return new ResponseEntity<>(Map.of("Error", "Invalid time"), HttpStatus.BAD_REQUEST);
        }

        if (isValidToken) {
            return new ResponseEntity<>(Map.of("Error", "You do not have permission"), HttpStatus.UNAUTHORIZED);
        }

        int saveStatus = appointmentService.bookAppointment(appointment);

        if (saveStatus == 0) {
            return new ResponseEntity<>(Map.of("Error", "The appointment could not be saved"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("Succes", "Appointment saved"));
    }

    // 5. Define the `updateAppointment` Method:
    // - Handles HTTP PUT requests to modify an existing appointment.
    // - Accepts a validated `Appointment` object and a token as input.
    // - Validates the token for `"patient"` role.
    // - Delegates the update logic to the `AppointmentService`.
    // - Returns an appropriate success or failure response based on the update
    // result.

    @PutMapping("/{token}")
    public ResponseEntity<?> updateAppointment(@PathVariable String token, @RequestBody Appointment appointment) {

        ResponseEntity<?> responseToken = service.validateToken(token, "patient");
        boolean isValidToken = responseToken.getHttpStatusCode().value() == 200 ? true : false;

        if (!isValidToken) {
            return responseToken;
        }

        return new ResponseEntity<>(appointmentService.updateAppointment(appointment));
    }

    // 6. Define the `cancelAppointment` Method:
    // - Handles HTTP DELETE requests to cancel a specific appointment.
    // - Accepts the appointment ID and a token as path variables.
    // - Validates the token for `"patient"` role to ensure the user is authorized
    // to cancel the appointment.
    // - Calls `AppointmentService` to handle the cancellation process and returns
    // the result.
    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<?> cancelAppointment(
            @PathVariable Long appointmentId,
            @PathVariable String token) {
       
        boolean isValid = service.validateToken(token, "patient")
                .getHttpStatusCode().value() == 200 ? true : false;

        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid token or unauthorized access to cancel this appointment.");
        }

        return new ResponseEntity<>(appointmentService.cancelAppointment(appointmentId));
    }

}
