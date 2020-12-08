package com.example.kotlinsample.sharedpreference

import io.reactivex.Single
import javax.inject.Inject

class GetKey @Inject constructor() : SimpleUseCase<String> {

    @Inject
    lateinit var cacheRepository: CacheRepository

    override fun execute(): Single<String> = Single.fromCallable { cacheRepository.getKey() }
}