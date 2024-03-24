package com.javaApi.javaApi.middleware;

import com.javaApi.javaApi.model.PersonalAccessToken;
import com.javaApi.javaApi.repository.PersonalAccessTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthMiddleware implements HandlerInterceptor {

    private final PersonalAccessTokenRepository personalAccessTokenRepository;

    @Autowired
    public AuthMiddleware(PersonalAccessTokenRepository personalAccessTokenRepository) {
        this.personalAccessTokenRepository = personalAccessTokenRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Check if the request is for creating a user
        if (request.getRequestURI().equals("/api/users") && request.getMethod().equals("POST")) {
            // Allow creating users without authentication
            return true;
        }

        // Check for the presence of the Bearer token in the request header
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            // Extract the token value
            String accessToken = token.substring(7).trim(); // Remove "Bearer " prefix

            // Check if the token matches a valid personal access token
            if (isValidAccessToken(accessToken)) {
                return true; // Authorized
            }
        }

        // If no valid token is provided, return 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }

    // Method to validate the personal access token
    private boolean isValidAccessToken(String accessToken) {
        // Check if the token exists in the database
        PersonalAccessToken personalAccessToken = personalAccessTokenRepository.findByToken(accessToken);
        return personalAccessToken != null && personalAccessToken.isActive();
    }
}
