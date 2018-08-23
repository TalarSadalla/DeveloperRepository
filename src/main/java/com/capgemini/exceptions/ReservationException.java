package com.capgemini.exceptions;

public class ReservationException extends RuntimeException {
    public ReservationException() {

    }
      public ReservationException(String message)
        {
            super(message);
        }
    }

