package com.womakerscode.microsservicemeetup.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationDto {

    private Integer id;//nesse atributo não é necessário incluir @NotEmpty por que ele é gerado automaticamente

    @NotEmpty
    private String name;

    @NotEmpty
    private String dateOfRegistration;

    @NotEmpty
    private String registration;


}
