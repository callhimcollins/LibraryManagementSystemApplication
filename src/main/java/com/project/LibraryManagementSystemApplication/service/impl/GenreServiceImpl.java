package com.project.LibraryManagementSystemApplication.service.impl;

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

    public final GenreRepository genreRepository;


    @Override
    public GenreDto createGenre(GenreDto genreDto) {
        Genre genre = Genre
                .builder()
                .code(genreDto.getCode())
                .name(genreDto.getName())
                .description(genreDto.getDescription())
                .displayOrder(genreDto.getDisplayOrder())
                .active(true)
                .build();

        if(genreDto.getParentGenreId() != null) {
            Genre parentGenre = genreRepository.findById(genreDto.getParentGenreId()).get();
            genre.setParentGenre(parentGenre);
        }
        Genre savedGenre = genreRepository.save(genre);

        GenreDto dto = GenreMapper.toDto(savedGenre);

        return dto;
    }

    @Override
    public List<GenreDto> getAllGenres() {
        return genreRepository
                .findAll()
                .stream()
                .map(genre -> GenreMapper.toDto(genre))
                .collect(Collectors.toList());
    }

}
