package com.project.LibraryManagementSystemApplication.service;

import com.project.LibraryManagementSystemApplication.payload.dto.BookDto;
import com.project.LibraryManagementSystemApplication.payload.request.BookSearchRequest;
import com.project.LibraryManagementSystemApplication.payload.response.PageResponse;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto bookDto);
    List<BookDto> createBooksInBulk();
    BookDto getBookById(Long bookId);
    BookDto getBookByISBN(String isbn);
    BookDto updateBook(Long id, BookDto bookDto);
    void deleteBook(Long id);
    void hardDeleteBook(Long id);
    PageResponse<BookDto> searchBooksWithFilters(
            BookSearchRequest searchRequest
    );

    long getTotalActiveBooks();
    long getTotalAvailableBooks();
}
