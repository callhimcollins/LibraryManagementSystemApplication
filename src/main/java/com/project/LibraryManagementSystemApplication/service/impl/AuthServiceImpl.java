package com.project.LibraryManagementSystemApplication.service.impl;

import com.project.LibraryManagementSystemApplication.configurations.JwtProvider;
import com.project.LibraryManagementSystemApplication.domain.UserRole;
import com.project.LibraryManagementSystemApplication.exception.UserException;
import com.project.LibraryManagementSystemApplication.mapper.UserMapper;
import com.project.LibraryManagementSystemApplication.model.PasswordResetToken;
import com.project.LibraryManagementSystemApplication.model.User;
import com.project.LibraryManagementSystemApplication.payload.dto.UserDto;
import com.project.LibraryManagementSystemApplication.payload.response.AuthResponse;
import com.project.LibraryManagementSystemApplication.repository.PasswordResetTokenRepository;
import com.project.LibraryManagementSystemApplication.repository.UserRepository;
import com.project.LibraryManagementSystemApplication.service.AuthService;
import com.project.LibraryManagementSystemApplication.service.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserServiceImplementation customUserServiceImplementation;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;

    @Override
    public AuthResponse login(String username, String password) throws UserException {
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        User user = userRepository.findByEmail(username);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        AuthResponse response = new AuthResponse();
        response.setTitle("Login Success");
        response.setMessage("Welcome back" + username);
        response.setJwt(token);
        response.setUser(UserMapper.toDto(user));

        return response;
    }

    private Authentication authenticate(String username, String password) throws UserException {
        UserDetails userDetails = customUserServiceImplementation.loadUserByUsername(username);

//        if(userDetails == null) {
//            throw new UserException("User Not Found With Email +" +password);
//        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UserException("Password Incorrect");
        }

        return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
    }

    @Override
    public AuthResponse signup(UserDto req) throws UserException {
        User user = userRepository.findByEmail(req.getEmail());

        if(user != null) {
            throw new UserException("Email ID already registered");
        }

        User createdUser = new User();
        createdUser.setEmail(req.getEmail());
        createdUser.setPassword(passwordEncoder.encode(req.getPassword()));
        createdUser.setPhone(req.getPhone());
        createdUser.setFullName(req.getFullName());
        createdUser.setLastLogin(LocalDateTime.now());
        createdUser.setRole(UserRole.USER);

        User savedUser = userRepository.save(createdUser);
        Authentication auth = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = jwtProvider.generateToken(auth);
        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setTitle("Welcome " + createdUser.getFullName());
        response.setMessage("Register Success");
        response.setUser(UserMapper.toDto(savedUser));
        return response;
    }

    @Override
    public AuthResponse logout() {
        return null;
    }

    @Transactional
    public void createPasswordResetToken(String email) throws UserException {
        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new UserException("User With Given Email Not Found");
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = PasswordResetToken
                .builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(5))
                .build();

        passwordResetTokenRepository.save(resetToken);
        String frontendUrl="";
        String resetLink=frontendUrl+token;
        String subject = "Password Reset Request";
        String body = "You requested to reset your password. Use this link (valid for 5 minutes): " + resetLink;


        // Send Email
        emailService.sendEmail(user.getEmail(), subject, body);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) throws Exception {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(
                        () -> new Exception("Invalid Token")
                );

        if (resetToken.isExpired()) {
            passwordResetTokenRepository.delete(resetToken);
            throw new Exception("Token Expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetTokenRepository.delete(resetToken);
    }
}