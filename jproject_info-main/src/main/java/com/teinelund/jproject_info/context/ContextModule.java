package com.teinelund.jproject_info.context;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ContextModule {

    @Singleton
    @Provides
    public Context provideContext() {
        return new ContextImpl();
    }
}
