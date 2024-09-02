package com.almaz.capstone_project.controller;

import com.almaz.capstone_project.model.Registration;
import com.almaz.capstone_project.model.Tournament;
import com.almaz.capstone_project.model.User;
import com.almaz.capstone_project.security.SecurityUtil;
import com.almaz.capstone_project.service.RegistrationService;
import com.almaz.capstone_project.service.TournamentService;
import com.almaz.capstone_project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private RegistrationService registrationService;
    private TournamentService tournamentService;
    private UserService userService;

    public RegistrationController(RegistrationService registrationService, TournamentService tournamentService, UserService userService) {
        this.registrationService = registrationService;
        this.tournamentService = tournamentService;
        this.userService = userService;
    }

    @GetMapping("/registrations/{tournament_id}/new")
    public String createRegistrationForm(@PathVariable long tournament_id) {
        Tournament tournament = tournamentService.findTournamentById(tournament_id);
        User user = userService.findByUsername(SecurityUtil.getSessionUser());
        Registration registration = new Registration();
        registrationService.createRegistration(registration, tournament, user);
        return "redirect:/tournaments/{tournament_id}";
    }

    @GetMapping("/registrations/{tournament_id}/withdraw")
    public String withdrawRegistration(@PathVariable long tournament_id) {
        Tournament tournament = tournamentService.findTournamentById(tournament_id);
        User user = userService.findByUsername(SecurityUtil.getSessionUser());

        registrationService.deleteRegistration(tournament, user);

        return "redirect:/tournaments/{tournament_id}";
    }
}
