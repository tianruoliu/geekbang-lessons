package org.geektimes.rest.client;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Configuration;
import java.security.KeyStore;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * {@link ClientBuilder}默认实现
 *
 * @author ajin
 */

public class DefaultClientBuilder extends ClientBuilder {

    private Configuration configuration;

    @Override
    public ClientBuilder withConfig(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    @Override
    public ClientBuilder sslContext(SSLContext sslContext) {
       throw  newUnsupportedSSLException();
    }

    @Override
    public ClientBuilder keyStore(KeyStore keyStore, char[] password) {
        throw  newUnsupportedSSLException();
    }

    @Override
    public ClientBuilder trustStore(KeyStore trustStore) {
        throw  newUnsupportedSSLException();
}

    @Override
    public ClientBuilder hostnameVerifier(HostnameVerifier verifier) {
        throw  newUnsupportedSSLException();
    }

    @Override
    public ClientBuilder executorService(ExecutorService executorService) {
        return null;
    }

    @Override
    public ClientBuilder scheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        return null;
    }

    @Override
    public ClientBuilder connectTimeout(long timeout, TimeUnit unit) {
        return null;
    }

    @Override
    public ClientBuilder readTimeout(long timeout, TimeUnit unit) {
        return null;
    }

    @Override
    public Client build() {
        return new DefaultClient();
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public ClientBuilder property(String name, Object value) {
        return null;
    }

    @Override
    public ClientBuilder register(Class<?> componentClass) {
        return null;
    }

    @Override
    public ClientBuilder register(Class<?> componentClass, int priority) {
        return null;
    }

    @Override
    public ClientBuilder register(Class<?> componentClass, Class<?>... contracts) {
        return null;
    }

    @Override
    public ClientBuilder register(Class<?> componentClass, Map<Class<?>, Integer> contracts) {
        return null;
    }

    @Override
    public ClientBuilder register(Object component) {
        return null;
    }

    @Override
    public ClientBuilder register(Object component, int priority) {
        return null;
    }

    @Override
    public ClientBuilder register(Object component, Class<?>... contracts) {
        return null;
    }

    @Override
    public ClientBuilder register(Object component, Map<Class<?>, Integer> contracts) {
        return null;
    }

    private UnsupportedOperationException newUnsupportedSSLException() {
        return new UnsupportedOperationException("Current implementation does not support SSL features");
    }
}
