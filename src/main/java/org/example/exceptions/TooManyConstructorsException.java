package org.example.exceptions;

/**
 * Instance of this exception will be throwed when several constructors in classes will be annotated by @Inject
 */
public class TooManyConstructorsException extends RuntimeException {
    public TooManyConstructorsException(String message) {
        super(message);
    }
}
