package com.javaApi.javaApi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testGettersAndSetters() {
        // Create a User object
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setAddress("123 Main St");
        user.setCep("12345");
        user.setFirstTelephone("(123) 456-7890");
        user.setSecondTelephone("(987) 654-3210");

        // Test getters
        assertEquals(1L, user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("123 Main St", user.getAddress());
        assertEquals("12345", user.getCep());
        assertEquals("(123) 456-7890", user.getFirstTelephone());
        assertEquals("(987) 654-3210", user.getSecondTelephone());

        // Test password hashing
        assertTrue(user.getPassword().startsWith("$2a$"));
    }

}
