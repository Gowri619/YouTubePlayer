package com.example.kotlinsample.sharedpreference

interface CacheRepository {

    fun getKey(): String

    fun setFirstTimeLaunch(isFirstLogin: Boolean)
    fun isFirstTimeLaunch(): Boolean

}