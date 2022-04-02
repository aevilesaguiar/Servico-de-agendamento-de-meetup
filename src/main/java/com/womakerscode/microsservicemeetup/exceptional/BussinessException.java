package com.womakerscode.microsservicemeetup.exceptional;

public class BussinessException extends RuntimeException {
    public BussinessException(String s) {
        super(s);
    }
}
