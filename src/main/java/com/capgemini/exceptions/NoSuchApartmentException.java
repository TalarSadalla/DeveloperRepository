package com.capgemini.exceptions;

/**
 * Custom Apartment exception if Apartment does not exist
 */
public class NoSuchApartmentException extends Exception {
    public NoSuchApartmentException() {

    }
      public NoSuchApartmentException(String message)
        {
            super(message);
        }
    }

