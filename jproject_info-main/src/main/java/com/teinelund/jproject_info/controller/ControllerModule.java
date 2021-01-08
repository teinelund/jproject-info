package com.teinelund.jproject_info.controller;

import com.teinelund.jproject_info.context.Context;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ControllerModule {

    @Singleton
    @Provides
    public Controller provideController(Context context) {
        return new ControllerImpl(context);
    }
}
