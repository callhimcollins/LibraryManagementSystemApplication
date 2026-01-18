package com.project.LibraryManagementSystemApplication.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordRequest {

    @NotNull(message = "Username Or Email Cannot Be Null")
    private String email;
}
