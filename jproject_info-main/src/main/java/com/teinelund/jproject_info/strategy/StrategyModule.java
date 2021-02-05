package com.teinelund.jproject_info.strategy;

import com.teinelund.jproject_info.commands.ValidateCommandLineArgumentsCommand;
import com.teinelund.jproject_info.common.Printer;
import com.teinelund.jproject_info.context.Context;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class StrategyModule {

    @Singleton
    @Provides
    public PrintHelpStrategy providePrintHelpStrategy(Context context, Printer printer) {
        return new PrintHelpStrategyImpl(context, printer);
    }

    @Singleton
    @Provides
    public PrintVersionStrategy providePrintVersionStrategy(Context context, Printer printer) {
        return new PrintVersionStrategyImpl(context, printer);
    }

    @Singleton
    @Provides
    public PathInformationStrategy providePathInformationStrategy(Context context, Printer printer, ValidateCommandLineArgumentsCommand command) {
        return new PathInformationStrategyImpl(context, printer, command);
    }
}
