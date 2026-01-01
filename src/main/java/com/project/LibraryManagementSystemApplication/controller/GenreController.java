package com.project.LibraryManagementSystemApplication.controller;

import com.project.LibraryManagementSystemApplication.model.Genre;
import com.project.LibraryManagementSystemApplication.payload.dto.GenreDto;
import com.project.LibraryManagementSystemApplication.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    @PostMapping("/create")
    public ResponseEntity<GenreDto> createGenre(@RequestBody GenreDto genreDto) {
        GenreDto createdGenreDto = genreService.createGenre(genreDto);
        return ResponseEntity.ok(createdGenreDto);
    }

    @GetMapping
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        List<GenreDto> genres = genreService.getAllGenres();
        return ResponseEntity.ok(genres);
    }
}
