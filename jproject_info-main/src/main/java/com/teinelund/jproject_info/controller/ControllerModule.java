package com.teinelund.jproject_info.controller;

import com.teinelund.jproject_info.common.Printer;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.strategy.PathInformationStrategy;
import com.teinelund.jproject_info.strategy.PrintHelpStrategy;
import com.teinelund.jproject_info.strategy.PrintVersionStrategy;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ControllerModule {

    @Singleton
    @Provides
    public Controller provideController(
            Context context,
            Printer printer,
            PrintHelpStrategy printHelpStrategy,
            PrintVersionStrategy printVersionStrategy,
            PathInformationStrategy pathInformationStrategy) {
        return new ControllerImpl(context, printer, printHelpStrategy, printVersionStrategy, pathInformationStrategy);
    }
}
