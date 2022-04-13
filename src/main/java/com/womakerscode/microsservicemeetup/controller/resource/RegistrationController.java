package com.womakerscode.microsservicemeetup.controller.resource;

import com.womakerscode.microsservicemeetup.controller.dto.RegistrationDto;
import com.womakerscode.microsservicemeetup.model.entity.Registration;
import com.womakerscode.microsservicemeetup.service.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private RegistrationService registrationService;

    private ModelMapper modelMapper;


    public RegistrationController(RegistrationService registrationService, ModelMapper modelMapper) {
        this.registrationService = registrationService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationDto create(@RequestBody @Valid RegistrationDto dto) {

        Registration entity = modelMapper.map(dto, Registration.class);
        entity = registrationService.save(entity);

        return modelMapper.map(entity, RegistrationDto.class);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public RegistrationDto get(@PathVariable Integer id) {

        return registrationService
                .getRegistrationById(id)
                .map(registration -> modelMapper.map(registration, RegistrationDto.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByRegistrationId(@PathVariable Integer id) {
        Registration registration = registrationService.getRegistrationById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        registrationService.delete(registration);
    }


    @PutMapping("{id}")
    public RegistrationDto update(@PathVariable Integer id, RegistrationDto registrationDTO) {

        return registrationService.getRegistrationById(id).map(registration -> {
            registration.setName(registrationDTO.getName());
            registration.setDateOfRegistration(registrationDTO.getDateOfRegistration());
            registration = registrationService.update(registration);

            return modelMapper.map(registration, RegistrationDto.class);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @GetMapping
    public Page<RegistrationDto> find(RegistrationDto dto, Pageable pageRequest) {
        Registration filter = modelMapper.map(dto, Registration.class);
        Page<Registration> result = registrationService.find(filter, pageRequest);

        List<RegistrationDto> list = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, RegistrationDto.class))
                .collect(Collectors.toList());

        return new PageImpl<RegistrationDto>(list, pageRequest, result.getTotalElements());
    }
}