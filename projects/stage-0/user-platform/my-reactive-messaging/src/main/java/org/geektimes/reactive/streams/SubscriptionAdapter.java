package org.geektimes.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * (Internal) Subscription Adapter with one {@link Subscriber}
 *
 * @author ajin
 */
class SubscriptionAdapter implements Subscription {

    private final DecoratingSubscriber<?> subscriber;

    public SubscriptionAdapter(DecoratingSubscriber<?> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void request(long n) {
        if (n < 1) {
            throw new IllegalArgumentException("The number of elements to requests must be more than zero!");
        }
        subscriber.setMaxRequest(n);
    }

    @Override
    public void cancel() {
        this.subscriber.cancel();
    }
    /**
     * 获取装饰器模式的{@link Subscriber}
     * */
    public Subscriber getSubscriber() {
        return subscriber;
    }
    /**
     * 获取底层的{@link Subscriber}
     * */
    public Subscriber getSourceSubscriber() {
        return subscriber.getSource();
    }
}
