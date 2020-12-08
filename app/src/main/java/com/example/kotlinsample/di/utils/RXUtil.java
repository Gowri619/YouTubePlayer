package com.example.kotlinsample.di.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;

/**
 * Created by Bajic Dusko (www.bajicdusko.com) on 22/03/17.
 */

public class RXUtil {
    public static void dispose(CompositeDisposable disposables) {
        if (disposables != null && !disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    public static CompositeDisposable initDisposables(CompositeDisposable disposables) {
        if (disposables == null || disposables.isDisposed()) {
            return new CompositeDisposable();
        }

        return disposables;
    }

    public static class RetryWithDelay implements Function<Flowable<? extends Throwable>, Flowable<?>> {
        private final int maxRetries;
        private final int retryDelayMillis;
        private int retryCount;

        public RetryWithDelay(final int maxRetries, final int retryDelayMillis) {
            this.maxRetries = maxRetries;
            this.retryDelayMillis = retryDelayMillis;
            this.retryCount = 0;
        }

        @Override
        public Flowable<?> apply(final Flowable<? extends Throwable> attempts) {
            return attempts
                    .flatMap(new Function<Throwable, Flowable<?>>() {
                        @Override
                        public Flowable<?> apply(final Throwable throwable) {
                            if (++retryCount < maxRetries) {
                                // When this Observable calls onNext, the original
                                // Observable will be retried (i.e. re-subscribed).
                                return Flowable.timer(retryDelayMillis,
                                        TimeUnit.MILLISECONDS);
                            }

                            // Max retries hit. Just pass the error along.
                            return Flowable.error(throwable);
                        }
                    });
        }
    }
}
