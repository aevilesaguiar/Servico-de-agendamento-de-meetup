package com.womakerscode.microsservicemeetup.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder //suprime a geração de método construtor
@Entity
@Table
public class Registration {

    @Id
    @Column(name = "registration_id")//geração da chave primáriA
    @GeneratedValue(strategy = GenerationType.IDENTITY)//a qual deve ser declarada quando a geração do valor da chave-primária é de responsabilidade do banco de dados.
    private Integer id;

    @Column(name = "person_name")
    private String name;

    @Column(name = "person_email")
    private String email;

    @Column(name = "person_password")
    private String password;

    @Column(name = "date_of_registration")
    private String dateOfRegistration;

    @Column //quando não incluimos o nome, o nome fica o mesmo do id
    private String registration;



}
