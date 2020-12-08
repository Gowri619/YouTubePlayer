package com.example.kotlinsample.di.component;

import com.example.kotlinsample.di.module.ServiceModule;
import com.example.kotlinsample.service.UploadService;
import dagger.Subcomponent;

@Subcomponent(modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(UploadService uploadService);

    @Subcomponent.Builder
    interface Builder {
        Builder serviceModule(ServiceModule serviceModule);

        ServiceComponent build();
    }
}
