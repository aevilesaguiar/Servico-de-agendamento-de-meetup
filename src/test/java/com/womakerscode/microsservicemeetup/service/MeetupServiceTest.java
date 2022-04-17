package com.womakerscode.microsservicemeetup.service;

import com.womakerscode.microsservicemeetup.model.entity.Meetup;
import com.womakerscode.microsservicemeetup.model.entity.Registration;
import com.womakerscode.microsservicemeetup.repository.MeetupRepository;
import com.womakerscode.microsservicemeetup.repository.RegistrationRepository;
import com.womakerscode.microsservicemeetup.service.impl.MeetupServiceImpl;
import com.womakerscode.microsservicemeetup.service.impl.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class MeetupServiceTest {
    RegistrationService registrationService;

    MeetupService meetupService;

    @MockBean
    RegistrationRepository registrationRepository;

    @MockBean
    MeetupRepository meetupRepository;

    @BeforeEach
    public void setUp(){
        this.registrationService = new RegistrationServiceImpl(registrationRepository);
        this.meetupService = new MeetupServiceImpl(meetupRepository);
    }

    @DisplayName("should not save a new meetup if there is another one")
    @Test
    public void testErrorOnCreateMeetupWithInvalidRegistration(){
        Meetup meetup = createValidMeetup();
        int id = 1;
        Mockito.when(registrationRepository.findById(id)).thenReturn(Optional.empty());
        Optional<Registration> registration = registrationService.getRegistrationById(id);

        assertThat(registration.isPresent()).isFalse();
        Mockito.verify(meetupRepository,Mockito.never()).save(meetup);

    }

    @Test
    @DisplayName("Should create a meetup")
    public void testSaveMeetup(){
        Meetup meetup = createValidMeetup();
        Registration registration = createValidRegistration();
        Mockito.when(registrationRepository.existsByRegistration(Mockito.any())).thenReturn(true);
        Mockito.when(meetupRepository.save(meetup)).thenReturn(createValidMeetup());

        Meetup savedMeetup = meetupService.save(meetup);

        assertThat(savedMeetup.getId()).isEqualTo(1);
        assertThat(savedMeetup.getRegistration()).isEqualTo(registration);
        assertThat(savedMeetup.getEvent()).isEqualTo("Backend java");

    }



    private Registration createValidRegistration() {
        return Registration.builder()
                .id(1)
                .name("Aeviles Aguiar")
                .dateOfRegistration("17/04/2022")
                .registration("001")
                .build();
    }

    private Meetup createValidMeetup(){
        return Meetup.builder()
                .id(1)
                .registration(createValidRegistration())
                .event("Backend java").build();
    }


}