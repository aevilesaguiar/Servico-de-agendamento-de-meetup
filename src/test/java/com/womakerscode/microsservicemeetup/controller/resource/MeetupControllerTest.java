package com.womakerscode.microsservicemeetup.controller.resource;

import com.womakerscode.microsservicemeetup.controller.dto.MeetupDto;
import com.womakerscode.microsservicemeetup.model.entity.Meetup;
import com.womakerscode.microsservicemeetup.model.entity.Registration;
import com.womakerscode.microsservicemeetup.service.MeetupService;
import com.womakerscode.microsservicemeetup.service.RegistrationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {MeetupController.class})
@AutoConfigureMockMvc
class MeetupControllerTest {

    static final String MEETUP_API = "/api/meetups";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @MockBean
    private MeetupService meetupService;


    @Test
    @DisplayName("Should create a meetup")
    public void testCreateMeetup() throws Exception{
        // cenario
        MeetupDto meetupDTO = MeetupDto.builder()
                .registrationAttribute("2")
                .event("Java Backend").build();

        String json = new ObjectMapper().writeValueAsString(meetupDTO);

        Registration registration = Registration.builder().id(15).registration("2").build();
        // execucao
        BDDMockito.given(registrationService.getRegistrationByRegistrationAttribute("2"))
                .willReturn(Optional.of(registration));
        // verificacao, assert....
        Meetup meetup =Meetup.builder().id(2).event("Java Backend")
                .registration(registration)
                .meetupDate("17/05/2022").build();

        BDDMockito.given(meetupService.save(Mockito.any(Meetup.class))).willReturn(meetup);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(MEETUP_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().string("2"));
    }

    @Test
    @DisplayName("Should return error when returning a meetup that doesn't exist")
    public void testInvalidRegistrationCreateMeetup () throws Exception{
        MeetupDto meetupDTO =MeetupDto.builder().registrationAttribute("2").event("Java Backend").build();
        String json =new ObjectMapper().writeValueAsString(meetupDTO);

        BDDMockito.given(registrationService.getRegistrationByRegistrationAttribute("2"))
                .willReturn(Optional.empty());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(MEETUP_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }




}