package com.project.back_end.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
 import com.fasterxml.jackson.annotation.JsonProperty;



@Entity
@Table(name="admin")
public class Admin{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "username cannot be null")
    private String username;

    @NotNull(message = "Password cannot be null")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

}