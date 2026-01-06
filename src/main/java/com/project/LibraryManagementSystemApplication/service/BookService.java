package com.project.LibraryManagementSystemApplication.service;

import com.project.LibraryManagementSystemApplication.exception.BookException;
import com.project.LibraryManagementSystemApplication.payload.dto.BookDto;
import com.project.LibraryManagementSystemApplication.payload.request.BookSearchRequest;
import com.project.LibraryManagementSystemApplication.payload.response.PageResponse;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookDto) throws BookException;
    List<BookDto> createBooksInBulk(List<BookDto> bookDtos) throws BookException;
    BookDto getBookById(Long bookId) throws BookException;
    BookDto getBookByISBN(String isbn) throws BookException;
    BookDto updateBook(Long id, BookDto bookDto) throws BookException;
    void deleteBook(Long id) throws BookException;
    void hardDeleteBook(Long id) throws BookException;
    PageResponse<BookDto> searchBooksWithFilters(
            BookSearchRequest searchRequest
    );

    long getTotalActiveBooks();
    long getTotalAvailableBooks();
}
