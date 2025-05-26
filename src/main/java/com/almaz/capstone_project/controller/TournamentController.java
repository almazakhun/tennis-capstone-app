package com.almaz.capstone_project.controller;

import com.almaz.capstone_project.model.Category;
import com.almaz.capstone_project.model.Tournament;
import com.almaz.capstone_project.model.User;
import com.almaz.capstone_project.security.SecurityUtil;
import com.almaz.capstone_project.service.CategoryService;
import com.almaz.capstone_project.service.RegistrationService;
import com.almaz.capstone_project.service.TournamentService;
import com.almaz.capstone_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TournamentController {
    private TournamentService tournamentService;
    private CategoryService categoryService;
    private UserService userService;
    private RegistrationService registrationService;

    public TournamentController(TournamentService tournamentService, CategoryService categoryService, UserService userService, RegistrationService registrationService) {
        this.tournamentService = tournamentService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @GetMapping("/tournaments")
    public String listTournaments(Model model) {
        List<Tournament> tournaments = tournamentService.findAllTournaments();
        model.addAttribute("tournaments", tournaments);
        model.addAttribute("page", "tournaments-list");
        return "tournaments-list";
    }

    @GetMapping("/tournaments/{id}")
    public String tournamentDetail(@PathVariable long id, Model model) {
        Tournament tournament = tournamentService.findTournamentById(id);

        model.addAttribute("tournament", tournament);

        User currentUser = userService.findByUsername(SecurityUtil.getSessionUser());
        if (currentUser != null) {
            boolean isRegistered = registrationService.isUserRegisteredForTournament(currentUser, tournament);
            model.addAttribute("isRegistered", isRegistered);
        }

        return "tournaments-detail";
    }

    @GetMapping("/tournaments/{id}/delete")
    public String deleteTournament(@PathVariable long id) {
        tournamentService.delete(id);
        return "redirect:/tournaments";
    }

    @GetMapping("/tournaments/new")
    public String createTournamentForm(Model model) {
        Tournament tournament = new Tournament();
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("tournament", tournament);
        model.addAttribute("categories", categories);
        model.addAttribute("page", "tournaments-create");
        return "tournaments-create";
    }

    @PostMapping("/tournaments/new")
    public String saveTournament(@ModelAttribute Tournament tournament, @RequestParam(required = false) List<Long> categoryIds, Model model) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            model.addAttribute("error", "Please select at least one category.");
            model.addAttribute("tournament", tournament);
            model.addAttribute("categories", categoryService.findAllCategories());
            return "tournaments-create";
        }
        // If categoryIds is null, initialize it as an empty list
        List<Category> categories = categoryService.findCategoriesByIds(categoryIds);

        tournament.setCategories(categories);
        tournamentService.createTournament(tournament);
        return "redirect:/tournaments";
    }

    @GetMapping("/tournaments/{id}/edit")
    public String editTournamentForm(@PathVariable long id, Model model) {
        Tournament tournament = tournamentService.findTournamentById(id);
        List<Category> categories = categoryService.findAllCategories();
        List<Category> selectedCategories = tournament.getCategories();

        model.addAttribute("tournament", tournament);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategoryIds", selectedCategories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));
        return "tournaments-edit";
    }

    @PostMapping("/tournaments/{id}/edit")
    public String updateTournament(@PathVariable long id,
                                   @ModelAttribute Tournament tournament,
                                   @RequestParam(required = false) List<Long> categoryIds,
                                   Model model) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            model.addAttribute("error", "Please select at least one category.");
            model.addAttribute("tournament", tournament);
            model.addAttribute("categories", categoryService.findAllCategories());
            model.addAttribute("selectedCategoryIds", new HashSet<>());
            return "tournaments-edit"; // Return to the edit form with error message
        }

        // Process the valid data
        List<Category> categories = categoryService.findCategoriesByIds(categoryIds);
        tournament.setCategories(categories);
        tournamentService.updateTournament(tournament);

        return "redirect:/tournaments/{id}";
    }


}
