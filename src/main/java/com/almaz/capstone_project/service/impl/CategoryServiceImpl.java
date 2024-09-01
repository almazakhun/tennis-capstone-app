package com.almaz.capstone_project.service.impl;

import com.almaz.capstone_project.model.Category;
import com.almaz.capstone_project.repository.CategoryRepository;
import com.almaz.capstone_project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findCategoryById(long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void delete(long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findCategoriesByIds(List<Long> categoryIds) {
        List<Category> categories = new ArrayList<>();
        for (long id : categoryIds) {
            Optional<Category> category = categoryRepository.findById(id);
            category.ifPresent(categories::add);
        }
        return categories;
    }
}
