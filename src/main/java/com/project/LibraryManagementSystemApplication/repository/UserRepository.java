package com.project.LibraryManagementSystemApplication.repository;

import com.project.LibraryManagementSystemApplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
