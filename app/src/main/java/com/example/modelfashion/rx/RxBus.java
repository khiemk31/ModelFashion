package com.example.modelfashion.rx;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import kotlin.jvm.JvmStatic;

public class RxBus {
    private static PublishSubject publisher = PublishSubject.create();

    public static void publish(Object event) {
        publisher.onNext(event);
    }

    @JvmStatic
    public static  <T> Observable<T> listen(Class eventType) {
        return publisher.ofType(eventType);
    }
}

//     RxBus.publish(RxEvent.TokenExpire())
//   RxBus.listen(RxEvent.TokenExpire::class.java)
