package jaba.web.fourthWebLab.DatabaseHandlers.Exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String login) {
        super("Can't find user with id = " + login);
    }
}
