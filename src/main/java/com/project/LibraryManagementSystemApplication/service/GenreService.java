package com.project.LibraryManagementSystemApplication.service;
import com.project.LibraryManagementSystemApplication.exception.GenreException;
import com.project.LibraryManagementSystemApplication.payload.dto.GenreDto;

import java.util.List;


public interface GenreService {
    GenreDto createGenre(GenreDto genreDto);
    List<GenreDto> getAllGenres();
    GenreDto getGenreById(Long id) throws GenreException;
    GenreDto updateGenre(Long genreId, GenreDto genreDto) throws GenreException;
    void deleteGenre(Long genreId) throws GenreException;
    void hardDeleteGenre(Long genreId) throws GenreException;
    List<GenreDto> getAllActiveGenresWithSubGenres();
    List<GenreDto> getTopLevelGenres();
//    Page<GenreDto> searchGenres(String searchTerm, Pageable pageable);
    long getTotalActiveGenres();
    long getBookCountByGenre(Long genreId);
}
