package com.example.realgodjj.rxjavademo.utils;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lesences  2017/3/13 下午7:41.
 */
public final class RxBusUtil {
    private static volatile RxBusUtil instance;
    private final FlowableProcessor<Object> rxBus;

    private RxBusUtil() {
        rxBus = PublishProcessor.create().toSerialized();
    }

    public static RxBusUtil getInstance() {
        if (instance == null) {
            synchronized (RxBusUtil.class) {
                if (instance == null) {
                    instance = new RxBusUtil();
                }
            }
        }
        return instance;
    }

    public void post(Object obj) {
        rxBus.onNext(obj);
    }

    public <T> Flowable<T> toFlowable(Class<T> tClass) {
        return rxBus.ofType(tClass)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io());
    }

    public boolean hasSubscribers() {
        return rxBus.hasSubscribers();
    }
}
