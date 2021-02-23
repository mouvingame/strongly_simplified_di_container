package org.example.exceptions;

/**
 * Instance of this exception will be throwed if container will use constructor annotated by @Inject but
 * the last one will have any parameter which cannot be injected cause missing binding for this parameter
 */
public class BindingNotFoundException extends RuntimeException {
    public BindingNotFoundException(String message) {
        super(message);
    }
}
