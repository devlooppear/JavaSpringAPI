package com.javaApi.javaApi.seed;

import com.github.javafaker.Faker;
import com.javaApi.javaApi.model.User;
import com.javaApi.javaApi.service.AuthService;
import com.javaApi.javaApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final Faker faker;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserSeeder(UserRepository userRepository, AuthService authService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.faker = new Faker(new Locale("pt", "BR"));
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Delete all existing users and their associated personal access tokens from the database
        userRepository.deleteAll();

        // Generate a specified number of random users
        int numberOfUsersToCreate = 10;
        List<User> users = generateRandomUsers(numberOfUsersToCreate);

        // Save users to the database
        userRepository.saveAll(users);

        // Generate personal access token for each user
        for (User user : users) {
            authService.generatePersonalAccessToken(user);
        }
    }

    // Method to generate random users
    private List<User> generateRandomUsers(int numberOfUsers) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < numberOfUsers; i++) {
            User user = new User();
            user.setName(faker.name().fullName());
            user.setEmail(faker.internet().emailAddress());
            // Hash the generated password before setting it to the user
            String password = faker.internet().password();
            user.setPassword(passwordEncoder.encode(password));
            user.setAddress(faker.address().fullAddress());
            user.setCep(faker.address().zipCode());
            user.setFirstTelephone(faker.phoneNumber().cellPhone());
            user.setSecondTelephone(faker.phoneNumber().cellPhone());
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            users.add(user);
        }
        return users;
    }
}
