package com.capgemini.exceptions;

public class NoSuchApartmentException extends RuntimeException {
    public NoSuchApartmentException() {

    }
      public NoSuchApartmentException(String message)
        {
            super(message);
        }
    }

