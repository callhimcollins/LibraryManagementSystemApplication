package com.project.LibraryManagementSystemApplication.repository;

import com.project.LibraryManagementSystemApplication.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
}
