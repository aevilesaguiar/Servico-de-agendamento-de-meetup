package com.womakerscode.microsservicemeetup.service;

import com.womakerscode.microsservicemeetup.controller.dto.MeetupFilterDto;
import com.womakerscode.microsservicemeetup.model.entity.Meetup;
import com.womakerscode.microsservicemeetup.model.entity.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MeetupService {

    Meetup save(Meetup meetup);

    Optional<Meetup> getById(Integer id);

    Meetup update(Meetup loan);

    Page<Meetup> find(MeetupFilterDto filterDTO, Pageable pageable);

    Page<Meetup> getRegistrationsByMeetup(Registration registration, Pageable pageable);

}