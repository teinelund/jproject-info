package com.teinelund.jproject_info.strategy;

import com.teinelund.jproject_info.commands.ProjectInformationCommand;
import com.teinelund.jproject_info.commands.ValidateCommandLineArgumentsCommand;
import com.teinelund.jproject_info.context.Context;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class StrategyModule {

    @Singleton
    @Provides
    public PrintHelpStrategy providePrintHelpStrategy(Context context) {
        return new PrintHelpStrategyImpl(context);
    }

    @Singleton
    @Provides
    public PrintVersionStrategy providePrintVersionStrategy(Context context) {
        return new PrintVersionStrategyImpl(context);
    }

    @Singleton
    @Provides
    public PathInformationStrategy providePathInformationStrategy(Context context, ValidateCommandLineArgumentsCommand command) {
        return new PathInformationStrategyImpl(context, command);
    }
}
