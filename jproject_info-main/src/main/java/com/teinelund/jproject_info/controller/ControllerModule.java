package com.teinelund.jproject_info.controller;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ControllerModule {

    @Singleton
    @Provides
    public Controller provideController() {
        return new ControllerImpl();
    }
}
