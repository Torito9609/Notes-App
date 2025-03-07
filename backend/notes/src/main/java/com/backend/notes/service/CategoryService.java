package com.backend.notes.service;

import com.backend.notes.model.Category;
import com.backend.notes.repository.ICategoryRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CategoryService implements ICategoryService{

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category saveCategory(Category category) {
        if(categoryRepository.existsByName(category.getName())){
            throw  new EntityExistsException("A category with name: " + category.getName() + "already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("The category with id: " + id + " does not exists."));
    }

    @Override
    public List<Category> findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.isEmpty() ? Collections.emptyList() : categories;
    }

    @Override
    @Transactional
    public Category updateCategory(Long idUpdate, Category updatedCategory) {
        Category existentCategory = categoryRepository.findById(idUpdate).orElseThrow(()->
                new EntityNotFoundException("The category with id: " + idUpdate + " does not exists."));

        if(existentCategory.getIsDefault()){
            throw new UnsupportedOperationException("Default categories can not be modified.");
        }

        boolean nameExistent = categoryRepository.existsByNameAndIdNot(updatedCategory.getName(), idUpdate);

        if(nameExistent){
            throw new EntityExistsException("The category with name " + updatedCategory.getName() + " already exists");
        }

        existentCategory.setName(updatedCategory.getName());
        existentCategory.setDefault(updatedCategory.getIsDefault());

        return categoryRepository.save(existentCategory);
    }

    @Override
    public List<Category> findCategoryByName(String name) {
        List<Category> categories = categoryRepository.findByNameContainingIgnoreCase(name);
        return categories.isEmpty() ? Collections.emptyList() : categories;
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category categoryDelete = categoryRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("The category with id: " + id + " does not exists."));

        if(categoryDelete.getIsDefault()){
            throw new UnsupportedOperationException("Default categories can not be deleted.");
        }

        categoryRepository.deleteById(id);
    }
}
