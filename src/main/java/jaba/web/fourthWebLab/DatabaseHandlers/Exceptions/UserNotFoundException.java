package jaba.web.fourthWebLab.DatabaseHandlers.Exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id) {
        super("Can't find user with id = " + id);
    }
}
