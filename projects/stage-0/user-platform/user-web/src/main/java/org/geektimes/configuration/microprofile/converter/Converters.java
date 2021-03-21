package org.geektimes.configuration.microprofile.converter;

import org.eclipse.microprofile.config.spi.Converter;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

import static java.util.ServiceLoader.load;

/**
 * @author ajin
 * @see Converter
 */

public class Converters implements Iterable<Converter> {

    public static final int DEFAULT_PRIORITY = 100;

    private final Map<Class<?>, PriorityQueue<PrioritizedConverter>> typedConverters = new HashMap<>();

    private ClassLoader classLoader;

    private boolean addedDiscoveredConverters = false;

    public Converters() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public Converters(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void addDiscoveredConverters() {
        if (addedDiscoveredConverters) {
            return;
        }
        addConverters(load(Converter.class, classLoader));
        addedDiscoveredConverters = true;

    }

    public void addConverters(Iterable<Converter> converters) {
        converters.forEach(this::addConverter);
    }

    private void addConverter(Converter converter) {
        addConverter(converter, DEFAULT_PRIORITY);
    }

    private void addConverter(Converter converter, int priority) {
        Class<?> convertedType = resolveConvertedType(converter);
        addConverter(converter, priority, convertedType);
    }

    private Class<?> resolveConvertedType(Converter converter) {
        assertConverter(converter);
        Class<?> convertedType  = null;
        Class<?> converterClass = converter.getClass();
        while (converterClass != null) {
            convertedType = resolveConvertedType(converterClass);
            if (convertedType != null) {
                break;
            }
        }
        return convertedType;
    }

    private Class<?> resolveConvertedType(Class<?> converterClass) {
        Class<?> convertedType = null;

        // converterClass.getGenericInterfaces():获取该类实现的接口Type

        // Type : Type is the common superinterface for all types in the Java
        //  programming language. These include raw types, parameterized types,
        //  array types, type variables and primitive types.
        for (Type superInterface : converterClass.getGenericInterfaces()) {
            convertedType = resolveConvertedType(superInterface);
            if (convertedType != null) {
                break;
            }
        }
        return convertedType;
    }

    private Class<?> resolveConvertedType(Type type) {
        Class<?> convertedType = null;
        // ParameterizedType represents a parameterized type ,such as Collection<String>
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType)type;
            if (pType.getRawType() instanceof Class) {
                Class<?> rawType = (Class)pType.getRawType();
                if (Converter.class.isAssignableFrom(rawType)) {
                    Type[] arguments = pType.getActualTypeArguments();
                    if (arguments.length == 1 && arguments[0] instanceof Class) {
                        convertedType = (Class)arguments[0];
                    }

                }
            }
        }
        return convertedType;
    }

    private void addConverter(Converter converter, int priority, Class<?> convertedType) {
        PriorityQueue<PrioritizedConverter> priorityQueue = typedConverters.computeIfAbsent(convertedType,
            t -> new PriorityQueue<>());

        priorityQueue.offer(new PrioritizedConverter(converter, priority));

    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    private void assertConverter(Converter<?> converter) {
        Class<? extends Converter> converterClass = converter.getClass();
        if (converterClass.isInterface()) {
            throw new IllegalArgumentException("The implementation Class of Converter must not be an interface!");
        }
        if (Modifier.isAbstract(converterClass.getModifiers())) {
            throw new IllegalArgumentException("The implementation Class of Converter must not be abstract!");

        }
    }

    @Override
    public Iterator<Converter> iterator() {
        return null;
    }

}
