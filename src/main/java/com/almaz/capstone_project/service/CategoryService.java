package com.almaz.capstone_project.service;

import com.almaz.capstone_project.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();

    Category createCategory(Category category);

    Category findCategoryById(long id);

    void updateCategory(Category category);

    void delete(long id);

    List<Category> findCategoriesByIds(List<Long> categoryIds);
}
