package com.capgemini.exceptions;

/**
 * Custom Building exception if the Building does not exist
 */
public class NoSuchBuildingException extends Exception {
    public NoSuchBuildingException() {

    }
      public NoSuchBuildingException(String message)
        {
            super(message);
        }
    }

