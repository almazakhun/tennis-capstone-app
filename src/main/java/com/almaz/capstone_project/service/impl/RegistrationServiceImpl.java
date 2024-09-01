package com.almaz.capstone_project.service.impl;

import com.almaz.capstone_project.model.Registration;
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
    public Registration createRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    @Override
    public Registration findRegistrationById(long id) {
        return registrationRepository.findById(id).get();
    }

    @Override
    public void deleteRegistration(long id) {
        registrationRepository.deleteById(id);
    }
}
