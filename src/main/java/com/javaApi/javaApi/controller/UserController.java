package com.javaApi.javaApi.controller;

import com.javaApi.javaApi.dto.UserDTO;
import com.javaApi.javaApi.dto.UserUpdateDTO;
import com.javaApi.javaApi.exception.ErrorResponse;
import com.javaApi.javaApi.model.User;
import com.javaApi.javaApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers();
        return userDTOs;
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return userDTO;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody User user) {
        UserDTO createdUserDTO = userService.createUser(user);
        return createdUserDTO;
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        UserDTO updatedUserDTO = userService.updateUser(id, userUpdateDTO);
        return updatedUserDTO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User and associated tokens deleted successfully");
        } catch (Exception e) {
            // Log the exception for debugging purposes
            logger.error("Error deleting user with ID " + id, e);
            // Return an error response with a meaningful message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user");
        }
    }

    // Exception handling for specific exceptions
    @ExceptionHandler({ RuntimeException.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException(Exception ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }
}
