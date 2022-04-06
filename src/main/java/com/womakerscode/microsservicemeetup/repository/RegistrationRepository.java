package com.womakerscode.microsservicemeetup.repository;

import com.womakerscode.microsservicemeetup.model.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {


    boolean existsByRegistration(String registration);

    Optional<Registration> findByRegistration(String registrationAttribute);//parameters JPA
}
