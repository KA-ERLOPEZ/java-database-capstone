package com.project.back_end.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name ="appointments")
public class Appointment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    private Doctor doctor;

    @NotNull
    @ManyToOne
    private Patient patient;

    @NotNull
    @Future(message = "Appointment time must be in the future")
    private LocalDateTime appointmentTime;

    @Size(min= 0, max= 1)
    private int status;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public Doctor getDoctor(){
        return doctor;
    }

    public void setDoctor(Doctor doctor){
        this.doctor = doctor;
    }

    public Patient getPatient(){
        return patient;
    }

    public void setPatient(Patient patient){
        this.patient = patient;
    }

    public LocalDateTime getAppointmentTime(){
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmenTime){
        this.appointmentTime = appointmenTime;
    }

    @Transient 
    public LocalTime getEndTime(){
        return appointmentTime.toLocalTime().plusHours(1);
    }

    @Transient 
    public LocalDate getAppointmentDate(){
        return appointmentTime.toLocalDate();

    }

    @Transient 
    public LocalTime getAppointmentTimeOnly(){
        return appointmentTime.toLocalTime();
    }
}

