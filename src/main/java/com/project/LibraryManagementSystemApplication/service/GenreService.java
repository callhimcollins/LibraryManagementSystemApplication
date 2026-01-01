package com.project.LibraryManagementSystemApplication.service;


import com.project.LibraryManagementSystemApplication.model.Genre;
import com.project.LibraryManagementSystemApplication.payload.dto.GenreDto;
import org.springframework.stereotype.Service;


public interface GenreService {
    GenreDto createGenre(GenreDto genreDto);
}
