package com.backend.notes.repository;

import com.backend.notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface INoteRepository extends JpaRepository<Note, Long> {

    List<Note> findDistinctByCategoriesName(String name);

    List<Note> findByArchivedFalse();

    List<Note> findByArchivedTrue();

    List<Note> findByCategories_Id(Long categoryId);

    List<Note> findByCategories_IdAndArchivedFalse(Long categoryId);

    boolean existsByTitleAndIdNot(String title, Long id);

    boolean existsByTitle(String title);

    List<Note> findByTitleContainingIgnoreCase(String title);
}
