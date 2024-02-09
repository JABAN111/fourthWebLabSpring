package jaba.web.fourthWebLab.DatabaseHandlers.Exceptions;

public class ResultNotFoundException extends RuntimeException{
    public ResultNotFoundException(Long id) {
        super("Can't find result with id = " + id);
    }
}
