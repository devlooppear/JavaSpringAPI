package com.javaApi.javaApi.model;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "personal_access_token")
@Data
public class PersonalAccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private String token;
}
