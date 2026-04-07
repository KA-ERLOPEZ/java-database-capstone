package com.project.back_end.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name= "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy= GenarationType.IDENTITY)
    private long id;

    @Size(min = 3, max = 100)
    @NotNull
    private String name;

    @Size(min = 3, max = 50)
    @NotNull
    private String speciality;

    @Email
    @NotNull
    private String email;

    @Size(min= 6)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    private String password;

    @Pattern(regexp = "\\d{10}", message = "El número de teléfono debe tener 10 dígitos")
    private String phone;

    @ElementCollection
    private List<String> availableTimes;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName(){
        return name;    
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSpeciality(){
        return speciality;
    }

    public void setSpeciality(String speciality){
        this.speciality = speciality;
    }

    public String getEmail(){
        return email;
    }
    
    
    public void setEmail(String email){
        this.email = email;;
    }

    public String getPassword(){
        return password;
    }

    private void setPassword(Stirng password){
        this.password = password;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public List<String> getAllAvailableTimes(){
        return availableTimes;
    }

    public void addAllAvailableTimes(List<String> availableTimes){
        this.availableTimes.addAll(availableTimes);
    }


}
