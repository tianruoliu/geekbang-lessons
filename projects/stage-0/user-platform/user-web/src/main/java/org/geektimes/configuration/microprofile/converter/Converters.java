package org.geektimes.configuration.microprofile.converter;

import org.eclipse.microprofile.config.spi.Converter;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

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

            Type superType = converterClass.getGenericSuperclass();
            if (superType instanceof ParameterizedType) {
                convertedType = resolveConvertedType(superType);
            }

            if (convertedType != null) {
                break;
            }
            // recursively
            converterClass = converterClass.getSuperclass();
        }
        return convertedType;
    }

    private Class<?> resolveConvertedType(Class<?> converterClass) {
        Class<?> convertedType = null;

        // converterClass.getGenericInterfaces():获取该类实现的接口数组

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

    public void addConverter(Converter converter, int priority, Class<?> convertedType) {
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

    public void addConverters(Converter... converters) {
        addConverters(Arrays.asList(converters));
    }

    public List<Converter> getConverters(Class<?> convertedType) {

        List<Converter> converterList = new LinkedList<>();

        PriorityQueue<PrioritizedConverter> priorityQueue = typedConverters.get(convertedType);
        if (null == priorityQueue || priorityQueue.isEmpty()) {
            return Collections.emptyList();
        }
        for (PrioritizedConverter prioritizedConverter : priorityQueue) {
            converterList.add(prioritizedConverter.getConverter());
        }

        return converterList;
    }

    @Override
    public Iterator<Converter> iterator() {
        List<Converter> allConverters = new LinkedList<>();
        for (PriorityQueue<PrioritizedConverter> priorityQueue : typedConverters.values()) {
            for (PrioritizedConverter prioritizedConverter : priorityQueue) {
                allConverters.add(prioritizedConverter.getConverter());
            }
        }
        return allConverters.iterator();
    }

}
