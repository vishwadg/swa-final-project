package com.example.commonsmodule.exceptions;

import org.springframework.cache.annotation.Cacheable;

public class NotFoundException extends RuntimeException{
    public NotFoundException(){
        super();
    }

    public NotFoundException(String message){
        super(message);
    }

    public NotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public NotFoundException(Throwable cause){
        super(cause);
    }
}
