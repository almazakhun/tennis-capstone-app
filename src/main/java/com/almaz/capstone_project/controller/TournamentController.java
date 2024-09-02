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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        // Format dates as strings
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedStartDate = tournament.getStartDate().format(formatter);
        String formattedEndDate = tournament.getEndDate().format(formatter);

        model.addAttribute("tournament", tournament);
        model.addAttribute("formattedStartDate", formattedStartDate);
        model.addAttribute("formattedEndDate", formattedEndDate);

        User currentUser = userService.findByUsername(SecurityUtil.getSessionUser());
        boolean isRegistered = registrationService.isUserRegisteredForTournament(currentUser, tournament);
        model.addAttribute("isRegistered", isRegistered);

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
    public String saveTournament(@ModelAttribute Tournament tournament, @RequestParam(required = false) List<Long> categoryIds) {
        // If categoryIds is null, initialize it as an empty list
        List<Category> categories = (categoryIds != null)
                ? categoryService.findCategoriesByIds(categoryIds)
                : new ArrayList<>();

        tournament.setCategories(categories);
        tournamentService.createTournament(tournament);
        return "redirect:/tournaments";
    }

    @GetMapping("/tournaments/{id}/edit")
    public String editTournamentForm(@PathVariable long id, Model model) {
        Tournament tournament = tournamentService.findTournamentById(id);
        model.addAttribute("tournament", tournament);
        return "tournaments-edit";
    }

    @PostMapping("/tournaments/{id}/edit")
    public String updateTournament(@PathVariable long id, @ModelAttribute Tournament tournament) {
        tournamentService.updateTournament(tournament);
        return "redirect:/tournaments";
    }


}
