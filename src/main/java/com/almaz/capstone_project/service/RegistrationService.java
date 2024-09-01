package com.almaz.capstone_project.service;

import com.almaz.capstone_project.model.Registration;

import java.util.List;

public interface RegistrationService {
    List<Registration> findAllRegistrations();
    Registration createRegistration(Registration registration);
    Registration findRegistrationById(long id);
    void deleteRegistration(long id);
}
