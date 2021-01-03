package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.context.Context;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class CommandModule {

    @Singleton
    @Provides
    public ParseCommandLineArgumentsCommand provideParseCommandLineArguments(Context context, ValidateCommandLineArgumentsCommand command) {
        return new ParseCommandLineArgumentsCommand(context, command);
    }

    @Singleton
    @Provides
    public ValidateCommandLineArgumentsCommand provideValidateCommandLineArgumentsCommnd(Context context) {
        return new ValidateCommandLineArgumentsCommand(context);
    }
}
