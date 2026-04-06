package com.Projects.marketplace.exception;

public class EmailDomainNotFoundException extends RuntimeException{
    public EmailDomainNotFoundException(String message){
        super(message);
    }
}
