package com.backend.notes.service;

import com.backend.notes.model.Category;

import java.util.List;

public interface ICategoryService {

    Category saveCategory(Category category);

    Category findCategoryById(Long id);

    List<Category> findAllCategories();

    Category updateCategory(Long idUpdate, Category updatedCategory);

    List<Category> findCategoryByName(String name);

    void deleteCategory(Long id);
}
