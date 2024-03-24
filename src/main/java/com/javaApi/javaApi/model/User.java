package com.javaApi.javaApi.model;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PersonalAccessToken> personalAccessTokens;

    public String getUsername() {
        return this.name;
    }

    private String name;
    private String email;
    private String password;
    private String address;
    private String cep;

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
