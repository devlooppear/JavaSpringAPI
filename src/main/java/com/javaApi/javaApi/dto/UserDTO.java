package com.javaApi.javaApi.dto;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String cep;

    @Column(name = "first_telephone")
    private String firstTelephone;

    @Column(name = "second_telephone")
    private String secondTelephone;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
