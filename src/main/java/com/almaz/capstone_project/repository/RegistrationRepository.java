package com.almaz.capstone_project.repository;

import com.almaz.capstone_project.model.Registration;
import com.almaz.capstone_project.model.Tournament;
import com.almaz.capstone_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Registration findByTournamentAndUser(Tournament tournament, User user);
}
