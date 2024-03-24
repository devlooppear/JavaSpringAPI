package com.javaApi.javaApi.service;

import com.javaApi.javaApi.dto.UserDTO;
import com.javaApi.javaApi.dto.UserUpdateDTO;
import com.javaApi.javaApi.exception.ErrorResponse;
import com.javaApi.javaApi.model.User;
import com.javaApi.javaApi.repository.PersonalAccessTokenRepository;
import com.javaApi.javaApi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PersonalAccessTokenRepository personalAccessTokenRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, PersonalAccessTokenRepository personalAccessTokenRepository,
            ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.personalAccessTokenRepository = personalAccessTokenRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserDTO> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(user -> modelMapper.map(user, UserDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw createResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving users", e);
        }
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO createUser(User user) {
        try {
            String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(hashedPassword);

            user = userRepository.save(user);
            return modelMapper.map(user, UserDTO.class);
        } catch (Exception e) {
            throw createResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating user", e);
        }
    }

    public UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Retrieve existing values from the database
        String existingName = existingUser.getName();
        String existingAddress = existingUser.getAddress();
        String existingCep = existingUser.getCep();

        // Update user information if the fields are provided in the DTO
        if (userUpdateDTO.getName() != null) {
            existingUser.setName(userUpdateDTO.getName());
        } else {
            // If the field is not provided in the DTO, retain the existing value
            existingUser.setName(existingName);
        }

        if (userUpdateDTO.getAddress() != null) {
            existingUser.setAddress(userUpdateDTO.getAddress());
        } else {
            existingUser.setAddress(existingAddress);
        }

        if (userUpdateDTO.getCep() != null) {
            existingUser.setCep(userUpdateDTO.getCep());
        } else {
            existingUser.setCep(existingCep);
        }

        // Check if email is provided and different from the existing email, then update
        // it
        if (userUpdateDTO.getEmail() != null && !userUpdateDTO.getEmail().equals(existingUser.getEmail())) {
            existingUser.setEmail(userUpdateDTO.getEmail());
        }

        // Check if password is provided and different from the existing password, then
        // update it
        if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().equals(existingUser.getPassword())) {
            String hashedPassword = new BCryptPasswordEncoder().encode(userUpdateDTO.getPassword());
            existingUser.setPassword(hashedPassword);
        }

        try {
            existingUser = userRepository.save(existingUser);
            return modelMapper.map(existingUser, UserDTO.class);
        } catch (Exception e) {
            throw createResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating user", e);
        }
    }

    @Transactional
    public void deleteUser(Long userId) {
        // Delete user
        userRepository.deleteById(userId);

        // Delete associated personal access tokens
        personalAccessTokenRepository.deleteByUserId(userId);
    }

    private ResponseStatusException createResponseStatusException(HttpStatus status, String message, Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(new Date(), status.value(), status.getReasonPhrase(), message,
                e.getMessage());
        return new ResponseStatusException(status, errorResponse.toString(), e);
    }
}
