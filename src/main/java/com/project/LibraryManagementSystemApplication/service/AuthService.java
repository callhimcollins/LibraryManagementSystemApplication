package com.project.LibraryManagementSystemApplication.service;


import com.project.LibraryManagementSystemApplication.exception.UserException;
import com.project.LibraryManagementSystemApplication.payload.dto.UserDto;
import com.project.LibraryManagementSystemApplication.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String username, String password) throws UserException;
    AuthResponse signup(UserDto req) throws UserException;
    AuthResponse logout();

    void createPasswordResetToken(String email) throws UserException;
    void resetPassword(String token, String newPassword) throws Exception;


}
