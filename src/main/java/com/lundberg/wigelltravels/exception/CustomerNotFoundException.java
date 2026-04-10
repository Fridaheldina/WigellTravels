package com.lundberg.wigelltravels.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String messsage){
        super(messsage);
    }
}
