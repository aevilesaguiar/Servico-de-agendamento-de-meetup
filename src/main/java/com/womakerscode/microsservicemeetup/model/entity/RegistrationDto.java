package com.womakerscode.microsservicemeetup.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder //suprime a geração de método construtor
public class RegistrationDto {

    private Integer id;//nesse atributo não é necessário incluir @NotEmpty por que ele é gerado automaticamente

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private LocalDate dateOfRegistration;

    @NotEmpty
    private String registration;



}
