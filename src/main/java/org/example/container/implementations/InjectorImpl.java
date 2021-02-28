package org.example.container.implementations;

import org.example.annotations.Inject;
import org.example.container.Injector;
import org.example.container.Provider;
import org.example.exceptions.BindingNotFoundException;
import org.example.exceptions.ConstructorNotFoundException;
import org.example.exceptions.TooManyConstructorsException;

import java.lang.reflect.Constructor;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InjectorImpl implements Injector {
    private Map<Class<?>, SimpleEntry<Class<?>, Constructor<?>>> prototypeBindings = new HashMap<>();
    private Map<Class<?>, SimpleEntry<Class<?>, Constructor<?>>> singletonBindings = new HashMap<>();
    private Map<Class<?>, Object> singletonInstanceBindings = new HashMap<>();

    private Object createThrough(Constructor<?> constructor) throws Exception {
        int parameterCount = constructor.getParameterCount();
        if (parameterCount == 0) return constructor.newInstance();

        Object[] injected = new Object[parameterCount];
        Class<?>[] parameterTypes = constructor.getParameterTypes();

        for (int i = 0; i < parameterCount; i++) {
            injected[i] = getInstance(parameterTypes[i]);
        }

        return constructor.newInstance(injected);
    }

    @SuppressWarnings("unchecked")
    private <T> T getInstance(Class<T> type) throws Exception {
        if (singletonBindings.containsKey(type)) {
            if (!singletonInstanceBindings.containsKey(type)) {
                Constructor<?> implConstructor = singletonBindings.get(type).getValue();
                Object implInstance = createThrough(implConstructor);
                singletonInstanceBindings.put(type, implInstance);
            }
            return (T) singletonInstanceBindings.get(type);
        } else {
            Constructor<?> implConstructor = prototypeBindings.get(type).getValue();
            return (T) createThrough(implConstructor);
        }
    }

    @Override
    public <T> Provider<T> getProvider(Class<T> type) throws Exception {
        Objects.requireNonNull(type);
        if (!prototypeBindings.containsKey(type) && !singletonBindings.containsKey(type)) return null;
        T instance = getInstance(type);
        return () -> instance;
    }

    @Override
    public <T> void bind(Class<T> intfType, Class<? extends T> implType) {
        Objects.requireNonNull(intfType);
        Objects.requireNonNull(implType);
        if (singletonBindings.containsKey(intfType))
            throw new IllegalArgumentException("Interface " + intfType.getName() + " and his implementation " +
                    implType.getName() + " are already a singleton binding");
        prototypeBindings.put(intfType, new SimpleEntry<>(implType, null));
    }

    @Override
    public <T> void bindSingleton(Class<T> intfType, Class<? extends T> implType) {
        Objects.requireNonNull(intfType);
        Objects.requireNonNull(implType);
        if (prototypeBindings.containsKey(intfType))
            throw new IllegalArgumentException("Interface " + intfType.getName() + " and his implementation " +
                    implType.getName() + " are already a prototype binding");
        singletonBindings.put(intfType, new SimpleEntry<>(implType, null));
    }

    @Override
    public void checkBindings() {
        List<SimpleEntry<Class<?>, Constructor<?>>> implConstrBindings = new ArrayList<>();
        implConstrBindings.addAll(prototypeBindings.values());
        implConstrBindings.addAll(singletonBindings.values());

        for (SimpleEntry<Class<?>, Constructor<?>> implConstrBinding : implConstrBindings) {
            Class<?> implType = implConstrBinding.getKey();
            Constructor<?>[] constructors = implType.getConstructors();
            List<Constructor<?>> injectConstructors = Stream.of(constructors)
                    .filter(constructor -> constructor.isAnnotationPresent(Inject.class)).collect(Collectors.toList());

            if (injectConstructors.size() > 1) throw new TooManyConstructorsException(
                    implType.getName() + " has several constructors annotated by @Inject");

            if (injectConstructors.size() == 0) {
                try {
                    Constructor<?> defaultConstructor = implType.getConstructor();
                    implConstrBinding.setValue(defaultConstructor);
                } catch (NoSuchMethodException e) {
                    throw new ConstructorNotFoundException(
                            implType.getName() + " doesn't have constructors annotated by @Inject and default constructor");
                }
            } else {
                Constructor<?> injectConstructor = injectConstructors.get(0);

                for (Class<?> parameterType : injectConstructor.getParameterTypes()) {
                    if (!prototypeBindings.containsKey(parameterType) && !singletonBindings.containsKey(parameterType))
                        throw new BindingNotFoundException(injectConstructor + " has parameter " + parameterType.getName() +
                                    " which cannot be injected because missing binding for this parameter");
                }

                implConstrBinding.setValue(injectConstructor);
            }
        }
    }
}
