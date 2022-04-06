package com.womakerscode.microsservicemeetup;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServicoDeAgendamentoDeMeetupApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicoDeAgendamentoDeMeetupApplication.class, args);
	}
	//adicionar essa linha parou de erro
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();}
}
