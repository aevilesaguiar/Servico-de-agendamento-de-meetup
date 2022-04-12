package com.womakerscode.microsservicemeetup.service.impl;


import com.womakerscode.microsservicemeetup.controller.dto.MeetupFilterDto;
import com.womakerscode.microsservicemeetup.model.entity.Meetup;
import com.womakerscode.microsservicemeetup.model.entity.Registration;
import com.womakerscode.microsservicemeetup.repository.MeetupRepository;
import com.womakerscode.microsservicemeetup.service.MeetupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class MeetupServiceImpl implements MeetupService {


    private MeetupRepository repository;

    public MeetupServiceImpl(MeetupRepository repository) {
        this.repository = repository;
    }


    @Override
    public Meetup save(Meetup meetup) {
        return repository.save(meetup);
    }

    @Override
    public Optional<Meetup> getById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Meetup update(Meetup loan) {
        return repository.save(loan);
    }

    @Override
    public Page<Meetup> find(MeetupFilterDto filterDTO, Pageable pageable) {
        return repository.findByRegistrationOnMeetup( filterDTO.getRegistration(), filterDTO.getEvent(), (java.awt.print.Pageable) pageable);
    }

    @Override
    public Page<Meetup> getRegistrationsByMeetup(Registration registration, Pageable pageable) {
        return repository.findByRegistration(registration, (java.awt.print.Pageable) pageable);
    }
}