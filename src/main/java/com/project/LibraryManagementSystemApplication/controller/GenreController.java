package com.project.LibraryManagementSystemApplication.controller;

import com.project.LibraryManagementSystemApplication.exception.GenreException;
import com.project.LibraryManagementSystemApplication.payload.dto.GenreDto;
import com.project.LibraryManagementSystemApplication.payload.response.ApiResponse;
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

    @GetMapping("/{genreId}")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable("genreId") Long genreId) throws GenreException {
        GenreDto genre = genreService.getGenreById(genreId);
        return ResponseEntity.ok(genre);
    }

    @PutMapping("{genreId}")
    public ResponseEntity<GenreDto> updateGenre(
            @RequestParam("genreId") Long genreId,
            @RequestBody GenreDto genreDto
    ) throws GenreException {
        GenreDto genre = genreService.updateGenre(genreId, genreDto);
        return ResponseEntity.ok(genre);
    }

    @DeleteMapping("{genreId}")
    public ResponseEntity<ApiResponse> softDeleteGenre(@PathVariable Long genreId) throws GenreException {
        genreService.deleteGenre(genreId);
        ApiResponse response = new ApiResponse("Genre Deleted - Soft Delete", true);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("hard-delete/{genreId}")
    public ResponseEntity<ApiResponse> hardDeleteGenre(@PathVariable Long genreId) throws GenreException {
        genreService.hardDeleteGenre(genreId);
        ApiResponse response = new ApiResponse("Genre Deleted - Hard Delete", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/top-level")
    public ResponseEntity<?> getTopLevelGenres() {
        List<GenreDto> genres = genreService.getTopLevelGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getTotalActiveGenres() {
        Long genres = genreService.getTotalActiveGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{genreId}/book-count")
    public ResponseEntity<?> getBookCountByGenre(@PathVariable("genreId") Long genreId) {
        Long count = genreService.getBookCountByGenre(genreId);
        return ResponseEntity.ok(count);
    }
}
