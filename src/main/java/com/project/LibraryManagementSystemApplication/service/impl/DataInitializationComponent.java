package com.project.LibraryManagementSystemApplication.service.impl;


import com.project.LibraryManagementSystemApplication.domain.UserRole;
import com.project.LibraryManagementSystemApplication.model.User;
import com.project.LibraryManagementSystemApplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializationComponent implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args){
        initializeAdminUser();
    }

    private void initializeAdminUser() {
        String adminEmail = "admin@gmail.com";
        String adminPassword = "1234567890";



        if(userRepository.findByEmail(adminEmail) == null) {
            User user = User.builder()
                    .password(passwordEncoder.encode(adminPassword))
                    .email(adminEmail)
                    .fullName("Collins Michael")
                    .role(UserRole.ADMIN)
                    .build();

            User admin = userRepository.save(user);
        }
    }

}
