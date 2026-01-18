package com.project.LibraryManagementSystemApplication.controller;


import com.project.LibraryManagementSystemApplication.exception.BookException;
import com.project.LibraryManagementSystemApplication.payload.dto.BookDto;
import com.project.LibraryManagementSystemApplication.payload.request.BookSearchRequest;
import com.project.LibraryManagementSystemApplication.payload.response.ApiResponse;
import com.project.LibraryManagementSystemApplication.payload.response.PageResponse;
import com.project.LibraryManagementSystemApplication.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @PostMapping("/bulk")
    public ResponseEntity<BookDto> createBooksInBulk(
            @Valid @RequestBody List<BookDto> bookDtos) throws BookException {
        List<BookDto> createdBooks = bookService.createBooksInBulk(bookDtos);
        return ResponseEntity.ok((BookDto) createdBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long id) throws BookException {
        BookDto gottenBook = bookService.getBookById(id);
        return ResponseEntity.ok(gottenBook);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDto> getBookByISBN(@PathVariable("isbn") String isbn) throws BookException {
        BookDto book = bookService.getBookByISBN(isbn);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(
            @PathVariable("id") Long id,
            @RequestBody BookDto bookDto
            ) throws BookException {
        try {
            BookDto book = bookService.updateBook(id, bookDto);
            return ResponseEntity.ok(book);
        } catch (BookException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBook(
            @PathVariable Long id
    ) throws BookException {
        bookService.deleteBook(id);
        return ResponseEntity.ok(new ApiResponse("Book Deleted Successfully", true));
    }

    @DeleteMapping("/{id}/permanent")
    public ResponseEntity<ApiResponse> hardDeleteBook(
            @PathVariable Long id
    ) throws BookException {
        bookService.hardDeleteBook(id);
        return ResponseEntity.ok(new ApiResponse("Book Deleted Successfully", true));
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<BookDto>> advancedSearch(
            @RequestBody BookSearchRequest searchRequest) {
        PageResponse<BookDto> books = bookService.searchBooksWithFilters(searchRequest);
        return ResponseEntity.ok(books);
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookDto>> searchBooks(
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false, defaultValue = "false") boolean availableOnly,
            @RequestParam(defaultValue = "true") boolean activeOnly,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ) {
        // Build search request from query parameter
        BookSearchRequest searchRequest = new BookSearchRequest();
        searchRequest.setGenreId(genreId);
        searchRequest.setAvailableOnly(availableOnly);
        searchRequest.setPage(page);
        searchRequest.setSize(size);
        searchRequest.setSortBy(sortBy);
        searchRequest.setSortDirection(sortDirection);

        PageResponse<BookDto> books = bookService.searchBooksWithFilters(searchRequest);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/stats")
    public ResponseEntity<BookStatsResponse> getBookStats() {
        long totalActive = bookService.getTotalActiveBooks();
        long totalAvailable = bookService.getTotalAvailableBooks();

        BookStatsResponse stats = new BookStatsResponse(totalActive, totalAvailable);
        return ResponseEntity.ok(stats);
    }

    public static class BookStatsResponse {
        public long totalActiveBooks;
        public long totalAvailableBooks;

        public BookStatsResponse(long totalActiveBooks, long totalAvailableBooks) {
            this.totalActiveBooks = totalActiveBooks;
            this.totalAvailableBooks = totalAvailableBooks;
        }
    }

}
