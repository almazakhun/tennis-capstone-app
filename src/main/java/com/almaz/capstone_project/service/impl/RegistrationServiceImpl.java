package com.almaz.capstone_project.service.impl;

import com.almaz.capstone_project.model.Registration;
import com.almaz.capstone_project.model.Tournament;
import com.almaz.capstone_project.model.User;
import com.almaz.capstone_project.repository.RegistrationRepository;
import com.almaz.capstone_project.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private RegistrationRepository registrationRepository;

    @Autowired
    public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public List<Registration> findAllRegistrations() {
        return registrationRepository.findAll();
    }

    @Override
    public Registration createRegistration(Registration registration, Tournament tournament, User user) {
        registration.setUser(user);
        registration.setTournament(tournament);
        return registrationRepository.save(registration);
    }

    @Override
    public Registration findRegistrationById(long id) {
        return registrationRepository.findById(id).get();
    }

    @Override
    public void deleteRegistration(Tournament tournament, User user) {
        Registration registration = registrationRepository.findByTournamentAndUser(tournament, user);
        registrationRepository.delete(registration);
    }

    @Override
    public boolean isUserRegisteredForTournament(User user, Tournament tournament) {
        for (Registration registration : user.getRegistrations()) {
            // Check if this registration is for the given tournament
            if (registration.getTournament().equals(tournament)) {
                return true; // The user is registered for this tournament
            }
        }
        return false;
    }
}
