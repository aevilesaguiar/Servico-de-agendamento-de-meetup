package com.womakerscode.microsservicemeetup.repository;

import com.womakerscode.microsservicemeetup.model.entity.Meetup;
import com.womakerscode.microsservicemeetup.model.entity.Registration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class MeetupRepositoryTest {

    @Autowired
    MeetupRepository meetupRepository;


    @Autowired
    TestEntityManager entityManager;


    @Autowired
    RegistrationRepository registrationRepository;

    @Test
    @DisplayName("Should return true when exists a meetup")
    public void testReturnMeetupSuccess(){
        String registration = "1";

        Registration registrationAttribute = createNewRegistration(registration);
        registrationRepository.save(registrationAttribute);

        Meetup meetup = createNewMeetup(registrationAttribute);
        meetupRepository.save(meetup);

        Meetup exists = meetupRepository.getById(meetup.getId());
        assertThat(exists).isNotNull();
    }

    @DisplayName("should save a meetup")
    @Test
    public void testSaveMeetup(){
        String registration = "1";

        Registration registrationAttribute = createNewRegistration(registration);
        registrationRepository.save(registrationAttribute);

        Meetup meetup = createNewMeetup(registrationAttribute);
        Meetup savedMeetup = meetupRepository.save(meetup);

        assertThat(savedMeetup.getId()).isNotNull();
    }


    private Registration createNewRegistration(String registration) {
        return Registration.builder()
                .name("Aeviles Aguiar")
                .dateOfRegistration("17/04/22")
                .registration(registration)
                .build();
    }

    private Meetup createNewMeetup(Registration registration){
        return Meetup.builder()
                .id(1)
                .registration(registration)
                .meetupDate("17/04/22")
                .registered(true)
                .event("Java BackEnd").build();
    }


}