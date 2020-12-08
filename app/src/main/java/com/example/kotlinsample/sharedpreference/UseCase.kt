package com.example.kotlinsample.sharedpreference

import io.reactivex.Single

interface SimpleUseCase<M> {
    fun execute(): Single<M>
}