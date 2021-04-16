package emt.finki.ukim.mk.demo.model.exceptions;

public class NoMoreAvailableCopiesException extends RuntimeException{
    public NoMoreAvailableCopiesException(Long id){
        super(String.format("No more available copies of book with id: %d", id));
    }

}
