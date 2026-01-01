package com.project.LibraryManagementSystemApplication.service;


import com.project.LibraryManagementSystemApplication.model.Genre;
import com.project.LibraryManagementSystemApplication.payload.dto.GenreDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface GenreService {
    GenreDto createGenre(GenreDto genreDto);
    List<GenreDto> getAllGenres();
}
