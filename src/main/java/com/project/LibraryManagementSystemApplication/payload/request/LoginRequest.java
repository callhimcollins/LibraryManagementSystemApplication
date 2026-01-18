package com.project.LibraryManagementSystemApplication.payload.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull(message = "Username Or Email Is Required")
    private String email;

    @NotNull(message = "Password Is Required")
    private String password;
}
