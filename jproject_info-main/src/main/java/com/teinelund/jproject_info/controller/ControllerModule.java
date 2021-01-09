package com.teinelund.jproject_info.controller;

import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.strategy.PrintHelpStrategy;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ControllerModule {

    @Singleton
    @Provides
    public Controller provideController(Context context, PrintHelpStrategy printHelpStrategy) {
        return new ControllerImpl(context, printHelpStrategy);
    }
}
