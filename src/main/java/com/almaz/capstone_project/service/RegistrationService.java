package com.almaz.capstone_project.service;

import com.almaz.capstone_project.model.Registration;
import com.almaz.capstone_project.model.Tournament;
import com.almaz.capstone_project.model.User;

import java.util.List;

public interface RegistrationService {
    List<Registration> findAllRegistrations();
    Registration createRegistration(Registration registration, Tournament tournament, User user);
    Registration findRegistrationById(long id);
    void deleteRegistration(Tournament tournament, User user);
    boolean isUserRegisteredForTournament(User user, Tournament tournament);
}
