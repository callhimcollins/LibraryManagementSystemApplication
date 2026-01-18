package com.project.LibraryManagementSystemApplication.model;

import com.project.LibraryManagementSystemApplication.domain.AuthProvider;
import com.project.LibraryManagementSystemApplication.domain.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String fullName;

    private UserRole role;

    private String phone;

    private AuthProvider authProvider=AuthProvider.LOCAL;

    private String googleId;

    private String profileImage;

    private String password;

    private LocalDateTime lastLogin;

    @CreationTimestamp
    private LocalDateTime createdAt;


    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
