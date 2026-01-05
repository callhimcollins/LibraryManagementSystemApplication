package com.project.LibraryManagementSystemApplication.service.impl;

import com.project.LibraryManagementSystemApplication.payload.dto.BookDto;
import com.project.LibraryManagementSystemApplication.payload.request.BookSearchRequest;
import com.project.LibraryManagementSystemApplication.payload.response.PageResponse;
import com.project.LibraryManagementSystemApplication.repository.BookRepository;
import com.project.LibraryManagementSystemApplication.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookDto createBook(BookDto bookDto) {
        return null;
    }

    @Override
    public List<BookDto> createBooksInBulk() {
        return List.of();
    }

    @Override
    public BookDto getBookById(Long bookId) {
        return null;
    }

    @Override
    public BookDto getBookByISBN(String isbn) {
        return null;
    }

    @Override
    public BookDto updateBook(Long id, BookDto bookDto) {
        return null;
    }

    @Override
    public void deleteBook(Long id) {

    }

    @Override
    public void hardDeleteBook(Long id) {

    }

    @Override
    public PageResponse<BookDto> searchBooksWithFilters(BookSearchRequest searchRequest) {
        return null;
    }

    @Override
    public long getTotalActiveBooks() {
        return 0;
    }

    @Override
    public long getTotalAvailableBooks() {
        return 0;
    }
}
