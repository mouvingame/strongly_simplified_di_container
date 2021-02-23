package org.example.container;

/**
 * Encapsulates instance of chosen type T
 * @param <T> type of encapsulated instance
 */
public interface Provider<T> {
    /**
     * Returns encapsulated instance
     * @return encapsulated instance
     */
    T getInstance();
}
