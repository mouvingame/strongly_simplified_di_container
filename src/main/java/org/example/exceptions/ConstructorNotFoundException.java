package org.example.exceptions;

/**
 * Instance of this exception will be throwed if classes won't have constructors annotated by @Inject and
 * default constructor
 */
public class ConstructorNotFoundException extends RuntimeException {
    public ConstructorNotFoundException(String message) {
        super(message);
    }
}
