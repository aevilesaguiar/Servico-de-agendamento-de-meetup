package com.womakerscode.microsservicemeetup.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetupDto {

    private Integer id;

    private String registrationAttribute;

    private String event;

    private RegistrationDto registration;

}
