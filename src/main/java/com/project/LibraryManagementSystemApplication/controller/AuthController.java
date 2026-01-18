package com.project.LibraryManagementSystemApplication.controller;


import com.project.LibraryManagementSystemApplication.exception.UserException;
import com.project.LibraryManagementSystemApplication.payload.dto.UserDto;
import com.project.LibraryManagementSystemApplication.payload.request.ForgotPasswordRequest;
import com.project.LibraryManagementSystemApplication.payload.request.LoginRequest;
import com.project.LibraryManagementSystemApplication.payload.request.ResetPasswordRequest;
import com.project.LibraryManagementSystemApplication.payload.response.ApiResponse;
import com.project.LibraryManagementSystemApplication.payload.response.AuthResponse;
import com.project.LibraryManagementSystemApplication.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUpHandler(
            @RequestBody @Valid UserDto req
    ) throws UserException {
        AuthResponse res = authService.signup(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHandler(
            @RequestBody @Valid LoginRequest req
    ) throws UserException {
        AuthResponse res = authService.login(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(res);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(
            @RequestBody ForgotPasswordRequest req
    ) throws UserException {
        authService.createPasswordResetToken(req.getEmail());

        ApiResponse res = new ApiResponse("A Reset Link Was Sent To Your Email", true);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestBody ResetPasswordRequest req
    ) throws Exception {
        authService.resetPassword(req.getToken(), req.getPassword());
        ApiResponse res = new ApiResponse(
                "Password Reset Successful",
                true
        );

        return ResponseEntity.ok(res);
    }
}
