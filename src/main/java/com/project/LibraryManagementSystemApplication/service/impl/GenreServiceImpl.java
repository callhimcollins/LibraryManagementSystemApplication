package com.project.LibraryManagementSystemApplication.service.impl;

import com.project.LibraryManagementSystemApplication.exception.GenreException;
import com.project.LibraryManagementSystemApplication.mapper.GenreMapper;
import com.project.LibraryManagementSystemApplication.model.Genre;
import com.project.LibraryManagementSystemApplication.payload.dto.GenreDto;
import com.project.LibraryManagementSystemApplication.repository.GenreRepository;
import com.project.LibraryManagementSystemApplication.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public GenreDto createGenre(GenreDto genreDto) {
        Genre genre = genreMapper.toEntity(genreDto);
        Genre savedGenre = genreRepository.save(genre);

        return genreMapper.toDto(savedGenre);
    }

    @Override
    public List<GenreDto> getAllGenres() {
        return genreRepository
                .findAll()
                .stream()
                .map(genre -> genreMapper.toDto(genre))
                .collect(Collectors.toList());
    }

    @Override
    public GenreDto getGenreById(Long genreId) throws GenreException {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(
                        () -> new GenreException("Genre not found")
                );
        return genreMapper.toDto(genre);
    }

    @Override
    public GenreDto updateGenre(Long genreId, GenreDto genreDto) throws GenreException {
        Genre existingGenre = genreRepository.findById(genreId)
                .orElseThrow(
                        () -> new GenreException("Genre not found")
                );

        genreMapper.updateEntityFromDto(genreDto, existingGenre);
        Genre updatedGenre = genreRepository.save(existingGenre);

        return genreMapper.toDto(updatedGenre);
    }

    @Override
    public void deleteGenre(Long genreId) throws GenreException {
        Genre existingGenre = genreRepository.findById(genreId)
                .orElseThrow(
                        () -> new GenreException("Genre not found")
                );
        existingGenre.setActive(false);
        genreRepository.save(existingGenre);
    }

    @Override
    public void hardDeleteGenre(Long genreId) throws GenreException {
        Genre existingGenre = genreRepository.findById(genreId)
                .orElseThrow(
                        () -> new GenreException("Genre not found")
                );
        genreRepository.delete(existingGenre);
    }

    @Override
    public List<GenreDto> getAllActiveGenresWithSubGenres() {
        List<Genre> topLevelGenres=genreRepository
                .findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();

        return genreMapper.toDtoList(topLevelGenres);
    }

    @Override
    public List<GenreDto> getTopLevelGenres() {
        List<Genre> topLevelGenres=genreRepository
                .findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();

        return genreMapper.toDtoList(topLevelGenres);
    }

    @Override
    public long getTotalActiveGenres() {
        return genreRepository.countByActiveTrue();
    }

    @Override
    public long getBookCountByGenre(Long genreId) {
        return 0;
    }
}
