package com.project.SamServices.exceptions;

public class AdNotFoundException extends  RuntimeException{
    public AdNotFoundException(String message) {
        super(message);
    }
}
