package com.project.LibraryManagementSystemApplication.mapper;

import com.project.LibraryManagementSystemApplication.model.Genre;
import com.project.LibraryManagementSystemApplication.payload.dto.GenreDto;

import java.util.stream.Collectors;

public class GenreMapper {

    public static GenreDto toDto(Genre savedGenre) {
        if(savedGenre == null) {
            return null;
        }
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


        if(savedGenre.getSubGenres() != null && !savedGenre.getSubGenres().isEmpty()) {
            dto.setSubGenre(savedGenre
                    .getSubGenres()
                    .stream()
                    .filter(subGenre -> subGenre.getActive())
                    .map(subGenre -> toDto(subGenre)).collect(Collectors.toList())
            );
        }

        return dto;
    }

}
