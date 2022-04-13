package com.womakerscode.microsservicemeetup.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder //suprime a geração de método construtor
@Entity
@Table
public class Registration {

    @Id
    @Column(name = "registration_id")//geração da chave primáriA
    @GeneratedValue(strategy = GenerationType.IDENTITY)//a qual deve ser declarada quando a geração do valor da primary key é de responsabilidade do banco de dados.
    private Integer id;

    @Column(name = "person_name")
    private String name;

    @Column(name = "date_of_registration")
    private String dateOfRegistration;

    @Column
    private String registration;

    @OneToMany(mappedBy = "registration")
    private List<Meetup> meetups;


}
