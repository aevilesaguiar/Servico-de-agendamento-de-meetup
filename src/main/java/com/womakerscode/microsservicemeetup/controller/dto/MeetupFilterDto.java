package com.womakerscode.microsservicemeetup.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeetupFilterDto {

    private String registration;

    private  String event;
}
