package com.project.LibraryManagementSystemApplication.repository;

import com.project.LibraryManagementSystemApplication.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
