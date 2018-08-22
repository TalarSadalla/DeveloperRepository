package com.capgemini.exceptions;

public class NoSuchBuildingException extends RuntimeException {
    public NoSuchBuildingException() {

    }
      public NoSuchBuildingException(String message)
        {
            super(message);
        }
    }

