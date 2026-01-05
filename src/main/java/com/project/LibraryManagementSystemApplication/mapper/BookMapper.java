package com.project.LibraryManagementSystemApplication.mapper;

import com.project.LibraryManagementSystemApplication.exception.BookException;
import com.project.LibraryManagementSystemApplication.model.Book;
import com.project.LibraryManagementSystemApplication.model.Genre;
import com.project.LibraryManagementSystemApplication.payload.dto.BookDto;
import com.project.LibraryManagementSystemApplication.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final GenreRepository genreRepository;

    public BookDto toBookDto(Book book) {
        if (book == null) {
            return null;
        }


        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .genreId(book.getGenre().getId())
                .genreName(book.getGenre().getName())
                .genreCode(book.getGenre().getCode())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .publicationDate(book.getPublicationDate())
                .language(book.getLanguage())
                .description(book.getDescription())
                .pages(book.getPages())
                .totalCopies(book.getTotalCopies())
                .availableCopies(book.getAvailableCopies())
                .price(book.getPrice())
                .coverImageUrl(book.getCoverImageUrl())
                .active(book.getActive())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }


    public Book toBookEntity(BookDto dto) throws BookException {
        if(dto == null) {
            return null;
        }

        Book book = new Book();
        book.setId(dto.getId());
        book.setIsbn(dto.getIsbn());

        // Map genre - fetch from database using genreId
        if(dto.getGenreId() != null) {
            Genre genre = genreRepository.findById(dto.getGenreId())
                    .orElseThrow(
                            () -> new BookException(
                                    "Genre with ID " + dto.getGenreId() + " not found"
                            )
                    );

            book.setGenre(genre);
        }


        getAndSetFields(dto, book);
        book.setActive(true);

        return book;
    }

    public void updateEntityFromDto(BookDto dto, Book book) throws BookException {
        if (dto == null || book == null) {
            return;
        }

        // ISBN should not be updated

        // Update genre if provided
        if(dto.getGenreId() != null) {
            Genre genre = genreRepository.findById(dto.getGenreId()).orElseThrow(
                    () -> new BookException("Genre with ID " + dto.getGenreId() + " not found")
            );

            book.setGenre(genre);
        }

        getAndSetFields(dto, book);

        if(dto.getActive() != null) {
            book.setActive(dto.getActive());
        }

    }

    private void getAndSetFields(BookDto dto, Book book) {
        book.setAuthor(dto.getAuthor());
        book.setTitle(dto.getTitle());
        book.setPublisher((dto.getPublisher()));
        book.setPublicationDate(dto.getPublicationDate());
        book.setLanguage(dto.getLanguage());
        book.setPages(dto.getPages());
        book.setDescription(dto.getDescription());
        book.setTotalCopies(dto.getTotalCopies());
        book.setAvailableCopies(dto.getAvailableCopies());
        book.setPrice(dto.getPrice());
        book.setCoverImageUrl(dto.getCoverImageUrl());
    }
}
