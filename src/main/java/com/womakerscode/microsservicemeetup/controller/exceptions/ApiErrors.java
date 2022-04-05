package com.womakerscode.microsservicemeetup.controller.exceptions;

import com.womakerscode.microsservicemeetup.exceptional.BussinessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiErrors {
    private final List<String> errors;

    public ApiErrors(BindingResult bindingResult) {
        this.errors = new ArrayList<>();
        bindingResult.getAllErrors().forEach(error -> this.errors.add(error.getDefaultMessage()) );
    }

    public ApiErrors(BussinessException e) {
        this.errors = Arrays.asList(e.getMessage());
    }


    public ApiErrors(ResponseStatusException e) {
        this.errors = Arrays.asList(e.getReason());
    }


    public List<String> getErrors() {
        return errors;
    }
}
