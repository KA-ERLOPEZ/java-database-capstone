package com.project.back_end.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.print.Doc;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.back_end.DTO.Login;
import com.project.back_end.models.Appointment;
import com.project.back_end.models.Doctor;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.DoctorRepository;

@Service
public class DoctorService {

    // 1. **Add @Service Annotation**:
    // - This class should be annotated with `@Service` to indicate that it is a
    // service layer class.
    // - The `@Service` annotation marks this class as a Spring-managed bean for
    // business logic.
    // - Instruction: Add `@Service` above the class declaration.

    // 2. **Constructor Injection for Dependencies**:
    // - The `DoctorService` class depends on `DoctorRepository`,
    // `AppointmentRepository`, and `TokenService`.
    // - These dependencies should be injected via the constructor for proper
    // dependency management.
    // - Instruction: Ensure constructor injection is used for injecting
    // dependencies into the service.
    private DoctorRepository doctorRepository;
    private AppointmentRepository appointmentRepository;
    private TokenService tokenService;

    public DoctorService(
            DoctorRepository doctorRepository,
            AppointmentRepository appointmentRepository,
            TokenService tokenService) {
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.tokenService = tokenService;
    }
    // 3. **Add @Transactional Annotation for Methods that Modify or Fetch Database
    // Data**:
    // - Methods like `getDoctorAvailability`, `getDoctors`, `findDoctorByName`,
    // `filterDoctorsBy*` should be annotated with `@Transactional`.
    // - The `@Transactional` annotation ensures that database operations are
    // consistent and wrapped in a single transaction.
    // - Instruction: Add the `@Transactional` annotation above the methods that
    // perform database operations or queries.

    // 4. **getDoctorAvailability Method**:
    // - Retrieves the available time slots for a specific doctor on a particular
    // date and filters out already booked slots.
    // - The method fetches all appointments for the doctor on the given date and
    // calculates the availability by comparing against booked slots.
    // - Instruction: Ensure that the time slots are properly formatted and the
    // available slots are correctly filtered.
    @Transactional(readOnly = true)
    public List<String> getDoctorAvailability(Long doctorId, LocalDate date) {

        Optional<Doctor> doctor = doctorRepository.findById(doctorId);

        // Retornamos una lista vacia si no se encuentra el docor
        if (doctor.isEmpty()) {
            return Collections.emptyList();

        }

        List<Appointment> appointements = appointmentRepository
                .findByDoctorIdAndAppointmentTimeBetween(doctorId,
                        date.atStartOfDay().plusHours(8),
                        date.atStartOfDay().plusHours(17));
        Set<String> takenTimes = appointements.stream().map(Appointment::getAppointmentTimeOnly)
                .map(dateTime -> dateTime.toString()).collect(Collectors.toSet());

        return doctor.get().getAvailableTimes().stream().filter(time -> !takenTimes.contains(time)).toList();
    }

    // 5. **saveDoctor Method**:
    // - Used to save a new doctor record in the database after checking if a doctor
    // with the same email already exists.
    // - If a doctor with the same email is found, it returns `-1` to indicate
    // conflict; `1` for success, and `0` for internal errors.
    // - Instruction: Ensure that the method correctly handles conflicts and
    // exceptions when saving a doctor.
    public int saveDoctor(Doctor doctor) {
        try {
            Doctor findDoctorByEmail = doctorRepository.findByEmail(doctor.getEmail());
            if (!Objects.isNull(findDoctorByEmail)) {
                return -1;
            }

            doctorRepository.save(doctor);
            return 1;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            // TODO: handle exception
            return 0;

        }
    }

    // 6. **updateDoctor Method**:
    // - Updates an existing doctor's details in the database. If the doctor doesn't
    // exist, it returns `-1`.
    // - Instruction: Make sure that the doctor exists before attempting to save the
    // updated record and handle any errors properly.
    public int updateDoctor(Doctor doctor) {
        try {
            Optional<Doctor> existsDoctor = doctorRepository.findById(doctor.getId());

            if (existsDoctor.isEmpty()) {
                return -1;
            }

            doctorRepository.save(doctor);
            return 1;

        } catch (Exception e) {
            // TODO: handle exception
            return 0;
        }
    }

    // 7. **getDoctors Method**:
    // - Fetches all doctors from the database. It is marked with `@Transactional`
    // to ensure that the collection is properly loaded.
    // - Instruction: Ensure that the collection is eagerly loaded, especially if
    // dealing with lazy-loaded relationships (e.g., available times).
    @Transactional(readOnly = true)
    public List<Doctor> getDoctors() {

        List<Doctor> doctors = doctorRepository.findAll();
        doctors.forEach(doctor -> doctor.getAvailableTimes().size());
        return doctors;
    }

    // 8. **deleteDoctor Method**:
    // - Deletes a doctor from the system along with all appointments associated
    // with that doctor.
    // - It first checks if the doctor exists. If not, it returns `-1`; otherwise,
    // it deletes the doctor and their appointments.
    // - Instruction: Ensure the doctor and their appointments are deleted properly,
    // with error handling for internal issues.
    public int delecteDoctor(Long id) {
        try {
            Optional<Doctor> existDoctor = doctorRepository.findById(id);
            if (existDoctor.isEmpty()) {
                return -1;
            }

            doctorRepository.delete(existDoctor.get());
            return 1;

        } catch (Exception e) {
            // TODO: handle exception
            return 0;
        }
    }

    // 9. **validateDoctor Method**:
    // - Validates a doctor's login by checking if the email and password match an
    // existing doctor record.
    // - It generates a token for the doctor if the login is successful, otherwise
    // returns an error message.
    // - Instruction: Make sure to handle invalid login attempts and password
    // mismatches properly with error responses.
    public Map<String, String> validateDoctor(Login login) {

        Doctor doctor = doctorRepository.findByEmail(login.getEmail());

        if (Objects.isNull(doctor)) {
            return Map.of("message", "Email or password invalid");
        }

        if (doctor.isAccountLocked()) {
            return Map.of("message", "User locked");
        }

        if (!doctor.getPassword().equals(login.getPassword())) {

            int updatedAttempts = doctor.getFailedAttempts() + 1;
            doctor.setFailedAttempts(updatedAttempts);

            if (updatedAttempts >= 3) {
                doctor.setAccountLocked(true);
            }

            doctorRepository.save(doctor);

            return Map.of("message", "Email or password invalid");
        }

        doctor.setFailedAttempts(0);
        doctorRepository.save(doctor);

        String token = tokenService.generateToken(doctor.getEmail());

        return Map.of("token", token);
    }

    // 10. **findDoctorByName Method**:
    // - Finds doctors based on partial name matching and returns the list of
    // doctors with their available times.
    // - This method is annotated with `@Transactional` to ensure that the database
    // query and data retrieval are properly managed within a transaction.
    // - Instruction: Ensure that available times are eagerly loaded for the
    // doctors.

    public Map<String, Object> findDoctorByName(String name) {
        List<Doctor> doctors = doctorRepository.findByNameLike(name);
        return Map.of("Doctors", doctors);
    }

    // 11. **filterDoctorsByNameSpecilityandTime Method**:
    // - Filters doctors based on their name, specialty, and availability during a
    // specific time (AM/PM).
    // - The method fetches doctors matching the name and specialty criteria, then
    // filters them based on their availability during the specified time period.
    // - Instruction: Ensure proper filtering based on both the name and specialty
    // as well as the specified time period.
    public Map<String, Object> filterDoctorsByNameSpecialtyAndTime(String name, String specialty, String amOrPm) {
        // 1. Buscar doctores por nombre y especialidad en la Base de Datos
        List<Doctor> doctors = doctorRepository.findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(name, specialty);

        // 2. Filtrar en memoria según el bloque horario seleccionado (AM o PM)
        List<Doctor> filteredDoctors = doctors.stream()
                .filter(doctor -> matchesTimeSlot(doctor, amOrPm))
                .toList();

        // 3. Construir la respuesta estructurada en el mapa
        Map<String, Object> response = new HashMap<>();
        response.put("doctors", filteredDoctors);
        response.put("total", filteredDoctors.size());
        response.put("timeSlotRequested", amOrPm);

        return response;
    }

    // 12. **filterDoctorByTime Method**:
    // - Filters a list of doctors based on whether their available times match the
    // specified time period (AM/PM).
    // - This method processes a list of doctors and their available times to return
    // those that fit the time criteria.
    // - Instruction: Ensure that the time filtering logic correctly handles both AM
    // and PM time slots and edge cases.

    // 13. **filterDoctorByNameAndTime Method**:
    // - Filters doctors based on their name and the specified time period (AM/PM).
    // - Fetches doctors based on partial name matching and filters the results to
    // include only those available during the specified time period.
    // - Instruction: Ensure that the method correctly filters doctors based on the
    // given name and time of day (AM/PM).

    public Map<String, Object> filterDoctorByNameAndTime(String name, String amOrPm) {
        // 1. Buscar doctores por nombre en la Base de Datos (Ignorando
        // mayúsculas/minúsculas)
        List<Doctor> doctors = doctorRepository.findByNameLike(name);

        // 2. Filtrar en memoria según el bloque horario (AM o PM)
        List<Doctor> filteredDoctors = doctors.stream()
                .filter(doctor -> matchesTimeSlot(doctor, amOrPm))
                .toList();

        // 3. Construir el mapa de retorno con los resultados
        Map<String, Object> response = new HashMap<>();
        response.put("doctors", filteredDoctors);
        return response;
    }

    // 14. **filterDoctorByNameAndSpecility Method**:
    // - Filters doctors by name and specialty.
    // - It ensures that the resulting list of doctors matches both the name
    // (case-insensitive) and the specified specialty.
    // - Instruction: Ensure that both name and specialty are considered when
    // filtering doctors.
    public Map<String, Object> filterDoctorByNameAndSpecility(String name, String speciality) {

        List<Doctor> doctors = doctorRepository
                .findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(name, speciality);

        Map<String, Object> doctorMap = new HashMap<>();

        if (doctors.isEmpty()) {
            doctorMap.put("Message", "No doctors found.");
            return doctorMap;
        }

        doctorMap.put("Doctors", doctors);

        return doctorMap;
    }

    // 15. **filterDoctorByTimeAndSpecility Method**:
    // - Filters doctors based on their specialty and availability during a specific
    // time period (AM/PM).
    // - Fetches doctors based on the specified specialty and filters them based on
    // their available time slots for AM/PM.
    // - Instruction: Ensure the time filtering is accurately applied based on the
    // given specialty and time period (AM/PM).
    public Map<String, Object> filterDoctorByTimeAndSpecility(String specialty, String amOrPm) {
        // 1. Buscar doctores por especialidad en la Base de Datos
        List<Doctor> doctors = doctorRepository.findBySpecialtyIgnoreCase(specialty);
    
        // 2. Filtrar en memoria según el bloque horario (AM o PM)
        List<Doctor> filteredDoctors = doctors.stream()
                .filter(doctor -> matchesTimeSlot(doctor, amOrPm))
                .toList();
    
        // 3. Construir el mapa de retorno con los resultados
        Map<String, Object> response = new HashMap<>();
        response.put("doctors", filteredDoctors);

    
        return response;
    }

    // 16. **filterDoctorBySpecility Method**:
    // - Filters doctors based on their specialty.
    // - This method fetches all doctors matching the specified specialty and
    // returns them.
    // - Instruction: Make sure the filtering logic works for case-insensitive
    // specialty matching.
    public Map<String, Object> filterDoctorBySpecialty(String specialty) {

        Map<String, Object> response = new HashMap<>();
    
        String normalizedSpecialty =
                specialty == null ? "" : specialty.trim();
    
        List<Doctor> doctors =
                doctorRepository.findBySpecialtyIgnoreCase(normalizedSpecialty);
    
        if (doctors.isEmpty()) {
            response.put("Message", "No doctors found for this specialty.");
            response.put("Doctors", doctors);
            return response;
        }
    
        response.put("Doctors", doctors);
    
        return response;
    }

    // 17. **filterDoctorsByTime Method**:
    // - Filters all doctors based on their availability during a specific time
    // period (AM/PM).
    // - The method checks all doctors' available times and returns those available
    // during the specified time period.
    // - Instruction: Ensure proper filtering logic to handle AM/PM time periods.
    public Map<String, Object> filterDoctorsByTime(String amOrPm) {
        // 1. Obtener todos los doctores de la Base de Datos
        List<Doctor> doctors = doctorRepository.findAll();
    
        // 2. Filtrar en memoria según el bloque horario (AM o PM)
        List<Doctor> filteredDoctors = doctors.stream()
                .filter(doctor -> matchesTimeSlot(doctor, amOrPm))
                .toList();
    
        // 3. Construir el mapa de retorno con los resultados
        Map<String, Object> response = new HashMap<>();
        response.put("doctors", filteredDoctors);
    
        return response;
    }

    /**
     * Método auxiliar para verificar si el doctor tiene al menos una hora
     * disponible
     * en el bloque horario solicitado (AM o PM).
     */
    private boolean matchesTimeSlot(Doctor doctor, String amOrPm) {
        // Si el parámetro viene vacío o nulo, no filtramos por hora (pasan todos)
        if (amOrPm == null || amOrPm.isBlank()) {
            return true;
        }

        List<String> availableTimes = doctor.getAvailableTimes();
        if (availableTimes == null || availableTimes.isEmpty()) {
            return false;
        }

        return availableTimes.stream().anyMatch(time -> {
            // Comparamos el formato "HH:mm" contra las 12:00 para determinar AM/PM
            boolean isAmTime = time.compareTo("12:00") < 0;

            if ("AM".equalsIgnoreCase(amOrPm)) {
                return isAmTime;
            } else if ("PM".equalsIgnoreCase(amOrPm)) {
                return !isAmTime;
            }

            return false;
        });
    }

}
