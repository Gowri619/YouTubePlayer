package com.example.kotlinsample.di.module;

import dagger.Module;

@Module(includes = {ApiModule.class, RepositoryModule.class})
public class DataModule {
}
