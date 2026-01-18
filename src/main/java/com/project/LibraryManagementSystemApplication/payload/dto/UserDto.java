package com.project.LibraryManagementSystemApplication.payload.dto;

import com.project.LibraryManagementSystemApplication.domain.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotNull(message = "Email Is Required")
    private String email;

    @NotNull(message = "Password Is Required")
    private String password;
    private String phone;

    @NotNull(message = "Full Name Is Required")
    private String fullName;
    private UserRole role;
    private String username;
    private LocalDateTime lastLogin;

}
