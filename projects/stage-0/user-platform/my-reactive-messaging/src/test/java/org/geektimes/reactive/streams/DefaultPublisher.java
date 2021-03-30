package org.geektimes.reactive.streams;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ajin
 */

public class DefaultPublisher<T> implements Publisher<T> {

    private List<SubscriberWrapper<T>> subscribers = new LinkedList<>();

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        DefaultSubscription subscription = new DefaultSubscription(subscriber);
        // 当订阅者订阅时
        subscriber.onSubscribe(subscription);
        subscribers.add(new SubscriberWrapper(subscriber, subscription));
    }

    public void publish(T data) {
        // 广播
        subscribers.forEach(subscriber -> {

            DefaultSubscription subscription = subscriber.getSubscription();

            // 否则 继续发送
            if (!subscription.isCanceled()) {
                subscriber.onNext(data);
            }

            // 判断当前 subscriber 是否 cancel 数据发送
            if (subscription.isCanceled()) {
                System.err.println("本次数据发布已忽略，数据为：" + data);
                // return;
            }
        });
    }

    public void error(Throwable error) {
        // 广播
        subscribers.forEach(subscriber -> subscriber.onError(error));
    }

    public void complete() {
        // 广播
        subscribers.forEach(SubscriberWrapper::onComplete);
    }

    public static void main(String[] args) {
        DefaultPublisher<Integer> publisher = new DefaultPublisher<>();
        publisher.subscribe(new DefaultSubscriber<>());

        for (int i = 0; i < 5; i++) {
            publisher.publish(i);
        }
    }
}
