package com.project.LibraryManagementSystemApplication.repository;

import com.project.LibraryManagementSystemApplication.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findByActiveTrueOrderByDisplayOrderAsc();
    List<Genre> findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();
    List<Genre> findByParentGenreIdAndActiveOrderByDisplayOrderAsc(Long parentGenreId, Boolean active);
    long countByActiveTrue();

//    @Query("select count(b) from book b where b.genre.id=:genreId")
//    long countBooksByGenre(@Param("genreId") Long genreId);

}
