package com.almaz.capstone_project.controller;

import com.almaz.capstone_project.model.Category;
import com.almaz.capstone_project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {
    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories/new")
    public String createCategoryForm(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "categories-create";
    }

    @PostMapping("/categories/new")
    public String saveCategory(@ModelAttribute Category category) {
        categoryService.createCategory(category);
        return "redirect:/tournaments";
    }
}
