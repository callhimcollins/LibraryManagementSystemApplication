package com.project.LibraryManagementSystemApplication.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
