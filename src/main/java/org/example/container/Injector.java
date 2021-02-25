package org.example.container;

import org.example.exceptions.BindingNotFoundException;
import org.example.exceptions.ConstructorNotFoundException;
import org.example.exceptions.TooManyConstructorsException;

/**
 * Manages the creation of interface implementations, injection dependencies through constructor parameters
 */
public interface Injector {
    /**
     * Provides provider for a created instance of type T
     * @param type class instance for chosen type
     * @param <T> type of instance that will be created by this injector
     * @return provider that encapsulates created instance of type T, if injector can create instance, else null
     * @throws Exception if injector cannot create instance
     */
    <T> Provider<T> getProvider(Class<T> type) throws Exception;

    /**
     * Binds interface type and implementation type between themselves
     * @param intfType class instance for chosen interface type
     * @param implType class instance for some interface implementation type
     * @param <T> type of interface that implementation instance will be created by this injector
     */
    <T> void bind(Class<T> intfType, Class<? extends T> implType);

    /**
     * Should be called after binding. Injector will check a possibility of creating implementation instances and
     * dependencies
     * @exception TooManyConstructorsException
     * @exception ConstructorNotFoundException
     * @exception BindingNotFoundException
     */
    void checkBindings();
}
