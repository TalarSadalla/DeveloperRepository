package com.capgemini.exceptions;

/**
 * Custom Client exception if Client does not exist
 */
public class NoSuchClientException extends Exception {
    public NoSuchClientException() {

    }
      public NoSuchClientException(String message)
        {
            super(message);
        }
    }

