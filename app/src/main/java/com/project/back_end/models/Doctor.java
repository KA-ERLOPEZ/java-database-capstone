package com.project.back_end.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3, max = 100)
    @NotNull
    private String name;

    @Size(min = 3, max = 50)
    @NotNull
    private String specialty;

    @Email
    @NotNull
    private String email;

    @Size(min = 6)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Pattern(regexp = "\\d{10}", message = "El número de teléfono debe tener 10 dígitos")
    private String phone;

    @ElementCollection
    private List<String> availableTimes;

    private int failedAttempts = 0;

    private boolean accountLocked = false;

    public int getFailedAttempts(){
        return failedAttempts;
    }

    public void setFailedAttemps(int failedAttempts){
        this.failedAttempts = failedAttempts;
    }

    public boolean isAccountLocked(){
        return accountLocked;
    }

    public void setAccountLocked( boolean accountLocked){
        this.accountLocked = accountLocked;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpeciality(String specialty) {
        this.specialty = specialty;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        ;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getAllAvailableTimes() {
        return availableTimes;
    }

    public void addAllAvailableTimes(List<String> availableTimes) {
        this.availableTimes.addAll(availableTimes);
    }

}
