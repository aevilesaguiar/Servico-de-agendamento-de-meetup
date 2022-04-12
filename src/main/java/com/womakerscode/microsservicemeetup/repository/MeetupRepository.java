package com.womakerscode.microsservicemeetup.repository;

import com.womakerscode.microsservicemeetup.model.entity.Meetup;
import com.womakerscode.microsservicemeetup.model.entity.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;

public interface MeetupRepository extends JpaRepository<Meetup, Integer> {
    @Query( value = " select l from Meetup as l join l.registration as b where b.registration = :registration or l.event =:event ")
    Page<Meetup> findByRegistrationOnMeetup(
            @Param("registration") String registration,
            @Param("event") String event,
            Pageable pageable
    );

    Page<Meetup> findByRegistration(Registration registration, Pageable pageable );
}
