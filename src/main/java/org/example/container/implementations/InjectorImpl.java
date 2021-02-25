package org.example.container.implementations;

import org.example.annotations.Inject;
import org.example.container.Injector;
import org.example.container.Provider;
import org.example.exceptions.BindingNotFoundException;
import org.example.exceptions.ConstructorNotFoundException;
import org.example.exceptions.TooManyConstructorsException;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InjectorImpl implements Injector {
    private Map<Class<?>, Class<?>> bindingMap = new HashMap<>();
    private Map<Class<?>, Constructor<?>> injectionMap = new HashMap<>();

    @Override
    public <T> Provider<T> getProvider(Class<T> type) {
        return null;
    }

    @Override
    public <T> void bind(Class<T> intfType, Class<? extends T> implType) {
        bindingMap.put(Objects.requireNonNull(intfType), Objects.requireNonNull(implType));
    }

    @Override
    public void checkBindings() {
        for (Map.Entry<Class<?>, Class<?>> binding : bindingMap.entrySet()) {
            Class<?> intfType = binding.getKey();
            Class<?> implType = binding.getValue();
            Constructor<?>[] constructors = implType.getConstructors();
            List<Constructor<?>> injectConstructors = Stream.of(constructors)
                    .filter(constructor -> constructor.isAnnotationPresent(Inject.class)).collect(Collectors.toList());

            if (injectConstructors.size() > 1) throw new TooManyConstructorsException(
                    implType.getName() + " has several constructors annotated by @Inject");

            if (injectConstructors.size() == 0) {
                try {
                    Constructor<?> defaultConstructor = implType.getConstructor();
                    injectionMap.put(intfType, defaultConstructor);
                } catch (NoSuchMethodException e) {
                    throw new ConstructorNotFoundException(
                            implType.getName() + " doesn't have constructors annotated by @Inject and default constructor");
                }
            } else {
                Constructor<?> injectConstructor = injectConstructors.get(0);

                for (Class<?> parameterType : injectConstructor.getParameterTypes()) {
                    if (!bindingMap.containsKey(parameterType)) throw new BindingNotFoundException(
                            injectConstructor + " has parameter " + parameterType.getName() +
                                    " which cannot be injected because missing binding for this parameter");
                }

                injectionMap.put(intfType, injectConstructor);
            }
        }
    }
}
