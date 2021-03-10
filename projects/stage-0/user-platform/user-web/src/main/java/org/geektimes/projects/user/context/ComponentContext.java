package org.geektimes.projects.user.context;

import org.geektimes.function.ThrowableAction;
import org.geektimes.function.ThrowableFunction;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.naming.*;
import javax.servlet.ServletContext;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * 组件上下文（Web应用全局使用）
 *
 * @author ajin
 */
public class ComponentContext {

    public static final String CONTEXT_NAME = ComponentContext.class.getName();

    private static final String COMPONENT_ENV_CONTEXT_NAME = "java:comp/env";

    private static final Logger logger = Logger.getLogger(CONTEXT_NAME);

    /**
     * Servlet上下文
     */
    private static ServletContext servletContext;

    // ApplicationContextAware 接口 setApplicationContext

    // Component Env Context
    private Context envContext;

    private ClassLoader classLoader;

    private Map<String, Object> componentsMap = new LinkedHashMap<>();


    public static ComponentContext getInstance() {
        return (ComponentContext) servletContext.getAttribute(CONTEXT_NAME);
    }


    /**
     * 初始化组件上下文
     *
     * @param servletContext Servlet上下文
     */
    public void init(ServletContext servletContext) throws RuntimeException {
        Objects.requireNonNull(servletContext, "Servlet容器启动异常!");

        // 将当前的组件上下文 设置为ServletContext的属性
        servletContext.setAttribute(CONTEXT_NAME, this);
        // 给当前组件上下文 关联 ServletContext实例对象
        ComponentContext.servletContext = servletContext;
        // 获取当前ServletContext (WebApp) 的 ClassLoader
        this.classLoader = servletContext.getClassLoader();

        // 初始化组件上下文
        initEnvContext();
        // 实例化组件
        instantiateComponents();
        // 初始化组件
        initializeComponents();

//        componentsMap.values().forEach(System.out::println);
        servletContext.setAttribute("componentsMap", componentsMap);

    }

    /**
     * 初始化{@link Context}
     */
    private void initEnvContext() throws RuntimeException {
        if (null != envContext) {
            return;
        }
        Context context = null;
        try {
            context = new InitialContext();
            this.envContext = (Context) context.lookup(COMPONENT_ENV_CONTEXT_NAME);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {
            close(context);
        }
    }

    /**
     * 实例化组件
     */
    protected void instantiateComponents() {
        // 遍历获取所有的组件名称
        List<String> componentNames = listAllComponentNames();
        // 通过依赖查找，实例化对象（ Tomcat BeanFactory setter 方法的执行，仅支持简单类型）
        componentNames.forEach(componentName -> componentsMap.put(componentName, lookupComponent(componentName)));
    }

    /**
     * 依赖注入框架 内部使用
     */
    protected <C> C lookupComponent(String componentName) {
        return executeInContext((context) -> (C) (context.lookup(componentName)));
    }

    /**
     * 通过名称进行依赖查找 (外部使用）
     *
     * @param name 名称
     */
    public <C> C getComponent(String name) {
        C component;
        try {
            component = (C) envContext.lookup(name);
        } catch (NamingException e) {
            throw new NoSuchElementException(name);
        }
        return component;
    }


    /**
     * 初始化组件（支持 Java 标准 Commons Annotation 生命周期）
     * <ol>
     *  <li>注入阶段 - {@link Resource}</li>
     *  <li>初始阶段 - {@link PostConstruct}</li>
     *  <li>销毁阶段 - {@link PreDestroy}</li>
     * </ol>
     */
    protected void initializeComponents() {
        for (Object component : componentsMap.values()) {
            Class<?> componentClass = component.getClass();
            // 注入阶段 - {@link Resource}
            injectComponents(component, componentClass);
            // 初始阶段 - {@link PostConstruct}
            processPostConstruct(component, componentClass);
//            // 销毁阶段 - {@link PreDestroy}
//            processPreDestroy();
        }

    }


    private void injectComponents(Object component, Class<?> componentClass) {
        Stream.of(componentClass.getDeclaredFields()).filter(field -> {
            int modifiersCount = field.getModifiers();
            return !Modifier.isStatic(modifiersCount) && field.isAnnotationPresent(Resource.class);
        }).forEach(field -> {
            Resource resource = field.getAnnotation(Resource.class);
            String resourceName = resource.name();
            // 查找 注入对象
            Object injectedObject = lookupComponent(resourceName);
            field.setAccessible(true);
            try {
                field.set(component, injectedObject);
            } catch (IllegalAccessException e) {
                logger.warning(component.getClass().getName() + "依赖注入失败!");
                throw new RuntimeException(e);
            }
        });
    }

    private void processPostConstruct(Object component, Class<?> componentClass) {
        Stream.of(componentClass.getMethods()).filter(method -> !Modifier.isStatic(method.getModifiers())// 非静态方法
                && method.getParameterCount() == 0 // 无参函数
                && method.isAnnotationPresent(PostConstruct.class) // 标注PostConstruct注解
        ).forEach(method -> {
            try {
                method.invoke(component);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private List<String> listAllComponentNames() {
        return listAllComponentNames("/");
    }

    /**
     * 获取某个JNDI名称下的 组件名称 list
     */
    private List<String> listAllComponentNames(String name) {
        return executeInContext(context -> {
            NamingEnumeration<NameClassPair> namingEnumeration = executeInContext(context, ctx -> ctx.list(name), true);

            // 目录 - Context
            // 节点 -
            if (namingEnumeration == null) { // 当前JNDI名称下没有子节点
                return Collections.emptyList();
            }

            List<String> fullNames = new LinkedList<>();
            while (namingEnumeration.hasMoreElements()) {
                NameClassPair element = namingEnumeration.nextElement();
                String className = element.getClassName();
                // 加载类
                Class<?> targetClass = classLoader.loadClass(className);
                if (Context.class.isAssignableFrom(targetClass)) {
                    // 如果当前名称是目录（Context 实现类）的话，递归查找
                    fullNames.addAll(listAllComponentNames(element.getName()));
                } else {
                    // 否则，当前名称绑定目标类型的话，添加该名称到集合中
                    String fullName = name.startsWith("/") ? element.getName() : name + "/" + element.getName();
                    fullNames.add(fullName);
                }
            }

            return fullNames;

        });

    }

    protected <R> R executeInContext(ThrowableFunction<Context, R> function) {
        return executeInContext(this.envContext, function, false);
    }

    protected <R> R executeInContext(ThrowableFunction<Context, R> function, boolean ignoreException) {
        return executeInContext(this.envContext, function, ignoreException);
    }

    /**
     * 在 Context 中执行，通过指定 ThrowableFunction 返回计算结果
     *
     * @param context         环境上下文
     * @param function        可能会抛出异常的<code>Function</code>
     * @param ignoreException 是否忽略异常
     * @return R  -> 执行返回的结果
     * @see ThrowableFunction#apply(Object)
     */
    private <R> R executeInContext(Context context, ThrowableFunction<Context, R> function, boolean ignoreException) {

        R result = null;
        try {
            result = ThrowableFunction.execute(context, function);
        } catch (Exception e) {
            if (ignoreException) {
                logger.warning(e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        }

        return result;
    }


    /**
     * 关闭
     */
    private static void close(Context context) throws RuntimeException {
        if (context != null) {
            ThrowableAction.execute(context::close);
        }
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * 销毁组件上下文
     */
    public void destroy() {
        processPreDestroy();
//        close(this.envContext);
    }

    /**
     * 销毁阶段 - {@link PreDestroy}
     */
    private void processPreDestroy() {

        componentsMap.values().forEach(component -> {
            Class<?> componentClass = component.getClass();
            Stream.of(componentClass.getMethods()).filter(method ->
                    !Modifier.isStatic(method.getModifiers()) && method.getParameterCount() == 0
                            && method.isAnnotationPresent(PreDestroy.class)).forEach(method -> {
                try {
                    method.invoke(component);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });

        componentsMap.clear();
    }


}
