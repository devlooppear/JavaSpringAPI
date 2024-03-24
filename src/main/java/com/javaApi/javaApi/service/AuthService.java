package com.javaApi.javaApi.service;

import com.javaApi.javaApi.model.PersonalAccessToken;
import com.javaApi.javaApi.model.User;
import com.javaApi.javaApi.repository.PersonalAccessTokenRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

@Service
public class AuthService {
    // You can define methods for authentication logic here

    @Autowired
    private PersonalAccessTokenRepository personalAccessTokenRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public void generatePersonalAccessToken(User user) {
        // Generate a token
        String token = generateToken(user);

        // Save token in personal_access_token table
        PersonalAccessToken pat = new PersonalAccessToken();
        pat.setUser(user);
        pat.setToken(token);
        pat.setActive(true);
        personalAccessTokenRepository.save(pat);
    }

    // Logic to generate a JWT token
    private String generateToken(User user) {
        // Generate a secret key (Note: In a real application, it's better to store this
        // key securely and not generate a new one each time)
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        // Generate a JWT token
        String jws = Jwts.builder()
                .claim("userId", user.getId())
                .claim("username", user.getUsername())
                .signWith(key, Jwts.SIG.HS256)
                .compact();

        return jws;
    }
}
