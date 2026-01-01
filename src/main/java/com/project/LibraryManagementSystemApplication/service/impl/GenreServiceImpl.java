package com.project.LibraryManagementSystemApplication.service.impl;

import com.project.LibraryManagementSystemApplication.model.Genre;
import com.project.LibraryManagementSystemApplication.payload.dto.GenreDto;
import com.project.LibraryManagementSystemApplication.repository.GenreRepository;
import com.project.LibraryManagementSystemApplication.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        GenreDto dto = GenreDto.builder()
                .id(savedGenre.getId())
                .name(savedGenre.getName())
                .description(savedGenre.getDescription())
                .displayOrder(savedGenre.getDisplayOrder())
                .active(savedGenre.getActive())
                .createdDate(savedGenre.getCreatedAt())
                .updatedAt(savedGenre.getUpdatedAt())
                .build();

        if(savedGenre.getParentGenre() != null) {
            dto.setParentGenreId(savedGenre.getParentGenre().getId());
            dto.setParentGenreName(savedGenre.getParentGenre().getName());
        }

//        dto.setSubGenre(savedGenre
//                .getSubGenres()
//                .stream()
//                .filter(subGenre -> subGenre.getActive())
//                .map(subGenre -> )
//        );


        return dto;
    }

}
