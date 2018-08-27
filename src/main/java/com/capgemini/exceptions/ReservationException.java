package com.capgemini.exceptions;

/**
 * Custom reservation exception, if Client has made more than 3 reservations
 */
public class ReservationException extends Exception {
    public ReservationException() {

    }
      public ReservationException(String message)
        {
            super(message);
        }
    }

