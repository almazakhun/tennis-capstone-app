package com.almaz.capstone_project.controller;

import com.almaz.capstone_project.model.Registration;
import com.almaz.capstone_project.service.RegistrationService;
import com.almaz.capstone_project.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    private RegistrationService registrationService;
    private TournamentService tournamentService;

    @Autowired
    public RegistrationController(RegistrationService registrationService, TournamentService tournamentService) {
        this.registrationService = registrationService;
        this.tournamentService = tournamentService;
    }

    @PostMapping("/registrations/{tournament_id}/new")
    public String createRegistrationForm(@PathVariable long tournament_id) {
        Registration registration = new Registration();
        tournamentService.addRegistration(tournament_id, registration);
        return "redirect:/tournaments";
    }

    @PostMapping("/registrations/{tournament_id}/delete")
    public String deleteRegistration(@PathVariable long tournament_id) {
        return "redirect:/tournaments";
    }
}
