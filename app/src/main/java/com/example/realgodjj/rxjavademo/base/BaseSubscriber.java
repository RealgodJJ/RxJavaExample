package com.example.realgodjj.rxjavademo.base;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lesences  2018/6/25 10:05.
 */
public abstract class BaseSubscriber<T> implements Subscriber<T> {


    protected List<Subscription> subList = new ArrayList<>();

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }
}
