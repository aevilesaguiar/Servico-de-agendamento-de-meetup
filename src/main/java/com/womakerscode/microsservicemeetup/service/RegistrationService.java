package com.womakerscode.microsservicemeetup.service;

import com.womakerscode.microsservicemeetup.model.entity.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RegistrationService {

    Registration save(Registration any);

    Optional<Registration> getRegistrationById(Integer id);

    void delete(Registration registration);

    Registration update(Registration registration);

    Page<Registration> find(Registration filter, Pageable pageRequest);

    Optional<Registration> getRegistrationByRegistrationAttribute(String registrationAttribute);
}
