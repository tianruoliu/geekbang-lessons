package org.geektimes.rest.client;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Map;

/**
 * 不可变的{@link WebTarget}
 *
 * @author ajin
 * @see WebTarget
 * @see UriBuilder
 */

public class ImmutableWebTarget implements WebTarget {

    private final UriBuilder uriBuilder;

    public ImmutableWebTarget(UriBuilder uriBuilder) {
        this.uriBuilder = uriBuilder.clone();
    }

    @Override
    public URI getUri() {
        return uriBuilder.build();
    }

    protected ImmutableWebTarget newWebTarget() {
        return new ImmutableWebTarget(this.uriBuilder);
    }

    @Override
    public UriBuilder getUriBuilder() {
        return uriBuilder;
    }

    @Override
    public WebTarget path(String path) {
        ImmutableWebTarget target = newWebTarget();
        target.uriBuilder.path(path);
        return target;
    }

    @Override
    public WebTarget resolveTemplate(String name, Object value) {
        return resolveTemplate(name, value, false);
    }

    @Override
    public WebTarget resolveTemplate(String name, Object value, boolean encodeSlashInPath) {
        ImmutableWebTarget target = newWebTarget();
        target.uriBuilder.resolveTemplate(name, value, encodeSlashInPath);
        return target;
    }

    @Override
    public WebTarget resolveTemplateFromEncoded(String name, Object value) {
        ImmutableWebTarget target = newWebTarget();
        target.uriBuilder.resolveTemplateFromEncoded(name, value);
        return target;
    }

    @Override
    public WebTarget resolveTemplates(Map<String, Object> templateValues) {
        ImmutableWebTarget target = newWebTarget();
        target.uriBuilder.resolveTemplates(templateValues);
        return target;
    }

    @Override
    public WebTarget resolveTemplates(Map<String, Object> templateValues, boolean encodeSlashInPath) {
        ImmutableWebTarget target = newWebTarget();
        target.uriBuilder.resolveTemplates(templateValues, encodeSlashInPath);
        return target;
    }

    @Override
    public WebTarget resolveTemplatesFromEncoded(Map<String, Object> templateValues) {
        ImmutableWebTarget target = newWebTarget();
        target.uriBuilder.resolveTemplatesFromEncoded(templateValues);
        return target;
    }

    @Override
    public WebTarget matrixParam(String name, Object... values) {
        ImmutableWebTarget target = newWebTarget();
        target.uriBuilder.matrixParam(name, values);
        return target;
    }

    @Override
    public WebTarget queryParam(String name, Object... values) {
        ImmutableWebTarget target = newWebTarget();
        target.uriBuilder.queryParam(name, values);
        return target;
    }

    @Override
    public Invocation.Builder request() {
        return null;
    }

    @Override
    public Invocation.Builder request(String... acceptedResponseTypes) {
        return null;
    }

    @Override
    public Invocation.Builder request(MediaType... acceptedResponseTypes) {
        return null;
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }

    @Override
    public WebTarget property(String name, Object value) {
        return null;
    }

    @Override
    public WebTarget register(Class<?> componentClass) {
        return null;
    }

    @Override
    public WebTarget register(Class<?> componentClass, int priority) {
        return null;
    }

    @Override
    public WebTarget register(Class<?> componentClass, Class<?>... contracts) {
        return null;
    }

    @Override
    public WebTarget register(Class<?> componentClass, Map<Class<?>, Integer> contracts) {
        return null;
    }

    @Override
    public WebTarget register(Object component) {
        return null;
    }

    @Override
    public WebTarget register(Object component, int priority) {
        return null;
    }

    @Override
    public WebTarget register(Object component, Class<?>... contracts) {
        return null;
    }

    @Override
    public WebTarget register(Object component, Map<Class<?>, Integer> contracts) {
        return null;
    }
}
