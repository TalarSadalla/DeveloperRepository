package com.capgemini.exceptions;

/**
 * Custom exception for apartment criteria search
 */
public class CriteriaSearchException extends Exception {
    public CriteriaSearchException() {

    }
      public CriteriaSearchException(String message)
        {
            super(message);
        }
    }

