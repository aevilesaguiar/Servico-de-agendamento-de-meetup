package com.womakerscode.microsservicemeetup.service;

import com.womakerscode.microsservicemeetup.exceptional.BussinessException;
import com.womakerscode.microsservicemeetup.model.Registration;
import com.womakerscode.microsservicemeetup.repository.RegistrationRepository;
import com.womakerscode.microsservicemeetup.service.impl.RegistrationServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class RegistrationServiceTest {

    RegistrationService registrationService;
    @MockBean
    RegistrationRepository repository;


    //setup para o teste
    @BeforeEach//antes de cada teste rode a dependencia do service
    public void setup(){
        //dependencia do service e da um new na mesma
        this.registrationService=new RegistrationServiceImpl(repository);

    }
    @Test
    @DisplayName("Should save an Registration")
    public void saveStudent(){
        //cenario- O que precisa para o teste funcionar
        Registration registration= createValidRegistration();

        //execução - simulando o ato de salvar o dado
        Mockito.when(repository.existsByRegistration(Mockito.anyString())).thenReturn(false);
        Mockito.when(repository.save(registration)).thenReturn(createValidRegistration());

        Registration savedRegistration = registrationService.save(registration);
        //assert - garantia do retorno
        assertThat(savedRegistration.getId()).isEqualTo(101);
        assertThat(savedRegistration.getName()).isEqualTo("Aeviles de Aguiar");
        //assertThat(savedRegistration.getDateOfRegistration()).isEqualTo("01/04/2022");
        assertThat(savedRegistration.getRegistration()).isEqualTo("001");
    }
    //lançar erro quando o cadastro for duplicado
    @Test
    @DisplayName("Should throw Business  error When to save a new registration with a registration duplicated")
    public void shouldNotSaveAsRegistrationDuplicated(){
        //cenário
        Registration registration = createValidRegistration();
        Mockito.when(repository.existsByRegistration(Mockito.any())).thenReturn(true);//ele vai pegar o registro duplicado

        Throwable exception = Assertions.catchThrowable(()-> registrationService.save(registration));
        assertThat(exception)
                .isInstanceOf(BussinessException.class)
                .hasMessage("Registration already created");


        Mockito.verify(repository, Mockito.never()).save(registration);//mockito vai verificar que dentro desse repository, que ele  nunca vai salvar um registro que foi criado

    }

    @Test
    @DisplayName("Should get an Registration by Id")
    public void getByRegistrationIdTest(){
        //Cenario
        Integer id = 11;
        Registration registration= createValidRegistration();
        registration.setId(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(registration));

        //Execução
        Optional<Registration> foundRegistration = registrationService.getRegistrationById(id);

        //asserts
        assertThat(foundRegistration.isPresent()).isTrue();
        assertThat(foundRegistration.get().getId()).isEqualTo(id);
        assertThat(foundRegistration.get().getName()).isEqualTo(registration.getName());
        assertThat(foundRegistration.get().getDateOfRegistration()).isEqualTo(registration.getDateOfRegistration());
        assertThat(foundRegistration.get().getRegistration()).isEqualTo(registration.getRegistration());

    }

    @Test
    @DisplayName("Should return empty when get an registration by id when doen't exists")//retorna um registration vazio se o id não for encontrado
    public void registrationNotFoundByIdTest() {
        //cenario
            Integer id = 11;

            Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

            Optional<Registration> registration  = registrationService.getRegistrationById(id);

            assertThat(registration.isPresent()).isFalse();//se o id estiver presente eu quero que ele seja falso

    }
    @Test
    @DisplayName("Should delete an student ")//retorna um registration vazio se o id não for encontrado
    public void deleteRegistrationTest(){
        Registration registration= Registration.builder().id(11).build();
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> registrationService.delete(registration));
        Mockito.verify(repository, Mockito.times(1)).delete(registration);


    }

    @Test
    @DisplayName("Should update an registration")//retorna um registration vazio se o id não for encontrado
    public void updateRegistration(){

        // cenario
        Integer id = 11;
        Registration updatingRegistration = Registration.builder().id(11).build();
        // execucao
        Registration updatedRegistration = createValidRegistration();
        updatedRegistration.setId(id);

        Mockito.when(repository.save(updatingRegistration)).thenReturn(updatedRegistration);
        Registration registration = registrationService.update(updatingRegistration);

        // assert
        assertThat(registration.getId()).isEqualTo(updatedRegistration.getId());
        assertThat(registration.getName()).isEqualTo(updatedRegistration.getName());
        assertThat(registration.getDateOfRegistration()).isEqualTo(updatedRegistration.getDateOfRegistration());
        assertThat(registration.getRegistration()).isEqualTo(updatedRegistration.getRegistration());

    }
    @Test
    @DisplayName("Should filter registrations must by properties")//fazer uma busca através das propriedades
    public void findRegistrationTest() {

        // cenario
        Registration registration = createValidRegistration();
        //requisição paginada devido PageRequest(SpringData)
        PageRequest pageRequest = PageRequest.of(0,10);

        List<Registration> listRegistrations = Arrays.asList(registration);
        Page<Registration> page = new PageImpl<Registration>(Arrays.asList(registration),
                PageRequest.of(0,10), 1);

        // execucao
        Mockito.when(repository.findAll(Mockito.any(Example.class),Mockito.any(PageRequest.class)))
                .thenReturn(page);

        Page<Registration> result = registrationService.find(registration, pageRequest);

        // assercao
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(listRegistrations);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
    }

    @Test
    @DisplayName("Should get an Registration model by registration attribute")
    public void getRegistrationByRegistrationAtrb() {

        String registrationAttribute = "1234";

        Mockito.when(repository.findByRegistration(registrationAttribute))
                .thenReturn(Optional.of(Registration.builder().id(11).registration(registrationAttribute).build()));

        Optional<Registration> registration  = registrationService.getRegistrationByRegistrationAttribute(registrationAttribute);

        assertThat(registration.isPresent()).isTrue();
        assertThat(registration.get().getId()).isEqualTo(11);
        assertThat(registration.get().getRegistration()).isEqualTo(registrationAttribute);

        Mockito.verify(repository, Mockito.times(1)).findByRegistration(registrationAttribute);

    }
    private Registration createValidRegistration() {
        return Registration.builder()
                .id(101)
                .name("Aeviles de Aguiar")
                //.dateOfRegistration("01/04/2022")
                .registration("001")
                .build();
    }


}
