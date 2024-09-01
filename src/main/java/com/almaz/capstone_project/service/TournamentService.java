package com.almaz.capstone_project.service;

import com.almaz.capstone_project.model.Category;
import com.almaz.capstone_project.model.Registration;
import com.almaz.capstone_project.model.Tournament;

import java.util.List;

public interface TournamentService {
    List<Tournament> findAllTournaments();

    Tournament createTournament(Tournament tournament);

    Tournament findTournamentById(long id);

    void updateTournament(Tournament tournament);

    void delete(long id);

    void addCategory(long id, Category category);

    void addRegistration(long id, Registration registration);
    void removeRegistration(long id, Registration registration);
}
