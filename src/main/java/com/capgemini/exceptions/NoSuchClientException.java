package com.capgemini.exceptions;

public class NoSuchClientException extends RuntimeException {
    public NoSuchClientException() {

    }
      public NoSuchClientException(String message)
        {
            super(message);
        }
    }

