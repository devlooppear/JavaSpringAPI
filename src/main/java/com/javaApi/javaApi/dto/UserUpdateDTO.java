package com.javaApi.javaApi.dto;

import java.sql.Timestamp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserUpdateDTO {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String password;
    private String cep;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        // Hash the password using BCryptPasswordEncoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    @Column(name = "first_telephone")
    private String firstTelephone;

    @Column(name = "second_telephone")
    private String secondTelephone;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
