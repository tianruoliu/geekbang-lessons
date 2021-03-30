package org.geektimes.reactive.streams;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * 业务数据订阅者
 *
 * @author ajin
 */

public class BusinessSubscriber<T> implements Subscriber<T> {

    private Subscription subscription;
    private int          count = 0;

    private final long maxRequest;

    public BusinessSubscriber(long maxRequest) {
        this.maxRequest = maxRequest;
    }

    @Override
    public void onSubscribe(Subscription s) {
        this.subscription = s;
        this.subscription.request(maxRequest);

    }

    @Override
    public void onNext(T t) {
        // 当到达数据阈值时，取消 Publisher 给当前 Subscriber 发送数据
        if (count++ > 2) {
            subscription.cancel();
            return;
        }
        System.out.println("收到数据: " + t);

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }
}
