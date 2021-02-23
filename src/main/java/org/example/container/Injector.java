package org.example.container;

/**
 * Manages the creation of interface implementations, injection dependencies through constructor parameters
 */
public interface Injector {
    /**
     *
     * @param type class instance for chosen type
     * @param <T> type of instance that will be created by this injector
     * @return provider that encapsulates created instance of type T, if injector can create instance, otherwise null
     */
    <T> Provider<T> getProvider(Class<T> type);

    /**
     *
     * @param intfType class instance for chosen interface type
     * @param implType class instance for some interface implementation type
     * @param <T> type of interface that implementation instance will be created by this injector
     */
    <T> void bind(Class<T> intfType, Class<? extends T> implType);
}
