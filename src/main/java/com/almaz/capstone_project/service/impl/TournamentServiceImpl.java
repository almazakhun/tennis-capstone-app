package com.almaz.capstone_project.service.impl;

import com.almaz.capstone_project.model.Category;
import com.almaz.capstone_project.model.Registration;
import com.almaz.capstone_project.model.Tournament;
import com.almaz.capstone_project.model.User;
import com.almaz.capstone_project.repository.TournamentRepository;
import com.almaz.capstone_project.repository.UserRepository;
import com.almaz.capstone_project.security.SecurityUtil;
import com.almaz.capstone_project.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService {
    private TournamentRepository tournamentRepository;
    private UserRepository userRepository;

    @Autowired
    public TournamentServiceImpl(TournamentRepository tournamentRepository, UserRepository userRepository) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Tournament> findAllTournaments() {
        return tournamentRepository.findAll();
    }

    @Override
    public Tournament createTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    @Override
    public Tournament findTournamentById(long id) {
        return tournamentRepository.findById(id).get();
    }

    @Override
    public void updateTournament(Tournament tournament) {
        tournamentRepository.save(tournament);
    }

    @Override
    public void delete(long id) {
        tournamentRepository.deleteById(id);
    }

    @Override
    public void addCategory(long id, Category category) {
        Tournament tournament = tournamentRepository.findById(id).get();
        tournament.addCategory(category);
        tournamentRepository.save(tournament);
    }

    @Override
    public void addRegistration(long id, Registration registration) {
        Tournament tournament = tournamentRepository.findById(id).get();
        String username = SecurityUtil.getSessionUser();
        User user = userRepository.findByUsername(username);
        registration.setUser(user);
        tournament.addRegistration(registration);
        tournamentRepository.save(tournament);
    }

    @Override
    public void removeRegistration(long id, Registration registration) {
        Tournament tournament = tournamentRepository.findById(id).get();
        tournament.removeRegistration(registration);
        tournamentRepository.save(tournament);
    }
}
