package com.project.LibraryManagementSystemApplication.service.impl;

import com.project.LibraryManagementSystemApplication.exception.BookException;
import com.project.LibraryManagementSystemApplication.mapper.BookMapper;
import com.project.LibraryManagementSystemApplication.model.Book;
import com.project.LibraryManagementSystemApplication.payload.dto.BookDto;
import com.project.LibraryManagementSystemApplication.payload.request.BookSearchRequest;
import com.project.LibraryManagementSystemApplication.payload.response.PageResponse;
import com.project.LibraryManagementSystemApplication.repository.BookRepository;
import com.project.LibraryManagementSystemApplication.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto createBook(BookDto bookDto) throws BookException {

        if(bookRepository.existsByIsbn(bookDto.getIsbn())) {
            throw new BookException("Book with ISBN " + bookDto.getIsbn() + " already exists");
        }

        Book book = bookMapper.toBookEntity(bookDto);
        book.isAvailableCopiesValid();
        Book savedBook = bookRepository.save(book);
        return bookMapper.toBookDto(savedBook);
    }

    @Override
    public List<BookDto> createBooksInBulk(List<BookDto> bookDtos) throws BookException {
        List<BookDto> createdBooks = new ArrayList<>();
        for(BookDto bookDto:bookDtos) {
            BookDto book = createBook(bookDto);
            createdBooks.add(book);
        }

        return createdBooks;
    }

    @Override
    public BookDto getBookById(Long bookId) throws BookException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(
                        () -> new BookException("Book not found")
                );
        return bookMapper.toBookDto(book);
    }

    @Override
    public BookDto getBookByISBN(String isbn) throws BookException {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(
                        () -> new BookException("Book not found")
                );
        return bookMapper.toBookDto(book);
    }

    @Override
    public BookDto updateBook(Long id, BookDto bookDto) throws BookException {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(
                        () -> new BookException("Book not found")
                );

        bookMapper.updateEntityFromDto(bookDto, existingBook);
        existingBook.isAvailableCopiesValid();
        Book updatedBook = bookRepository.save(existingBook);

        return bookMapper.toBookDto(updatedBook);
    }

    @Override
    public void deleteBook(Long id) throws BookException {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(
                        () -> new BookException("Book not found")
                );

        existingBook.setActive(false);
        bookRepository.save(existingBook);
    }

    @Override
    public void hardDeleteBook(Long id) throws BookException {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(
                        () -> new BookException("Book not found")
                );

        bookRepository.delete(existingBook);
    }

    @Override
    public PageResponse<BookDto> searchBooksWithFilters(BookSearchRequest searchRequest) {
        Pageable pageable = createPageable(
                searchRequest.getPage(),
                searchRequest.getSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDirection()
        );
        Page<Book> bookPage = bookRepository.searchBooksWithFilters(
                searchRequest.getSearchTerm(),
                searchRequest.getGenreId(),
                searchRequest.getAvailableOnly(),
                pageable
        );
        return convertToPageResponse(bookPage);
    }

    @Override
    public long getTotalActiveBooks() {
        return bookRepository.countByActiveTrue();
    }

    @Override
    public long getTotalAvailableBooks() {
        return bookRepository.countAvailableBooks();
    }

    private Pageable createPageable(
            int currentPage,
            int size,
            String sortBy,
            String sortDirection
    ) {
        size = Math.min(size, 10);
        size = Math.max(size, 1);

        Sort sort = sortDirection.equalsIgnoreCase("ASC")
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return PageRequest.of(currentPage, size, sort);
    }

    private PageResponse<BookDto> convertToPageResponse(Page<Book> books) {
        List<BookDto> bookDtos = books.getContent()
                .stream()
                .map(bookMapper::toBookDto)
                .collect(Collectors.toList());

        return new PageResponse<>(
                bookDtos,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isLast(),
                books.isFirst(),
                books.isEmpty()
        );
    }
}
