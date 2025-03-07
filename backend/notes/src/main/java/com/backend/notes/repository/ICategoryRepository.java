package com.backend.notes.repository;

import com.backend.notes.model.Category;
import com.backend.notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String title);

    List<Category> findByNameContainingIgnoreCase(String title);

    boolean existsByNameAndIdNot(String name, Long id);
}
