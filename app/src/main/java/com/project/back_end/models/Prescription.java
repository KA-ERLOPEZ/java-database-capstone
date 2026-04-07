package com.project.back_end.models;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;

@Document(collection = "prescriptions")
public class Prescription {

    @Id
    private long id;

    @Size(min = 3, max = 100)
    private String patientName;
    
    @Positive
    @NotNull
    private long appointmentId;

    @Size(min = 3, max = 100)
    private String medication;

    @Size(min = 3, max = 20)
    private String dosage;

    @Size(max = 200)
    private String doctorNotes;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getPatientName(){
        return patientName;
    }

    public void setPatientName(String patientName){
        this.patientName = patientName;
    }

    public long getAppointmentId(){
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId){
        this.appointmentId = appointmentId;
    }

    public String getMedication(){
        return medication;
    }

    public void setMedication(String medication){
        this.medication = medication;
    }

    public String getDosage(){
        return dosage;
    }

    public void setDosage(String dosage){
        this.dosage = dosage;
    }

    public String getDoctorNotes(){
        return doctorNotes;
    }

    public void setDoctorNotes(String doctorNotes){
        this.doctorNotes = doctorNotes;
    }

    
}
