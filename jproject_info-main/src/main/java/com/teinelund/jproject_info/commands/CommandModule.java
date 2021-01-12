package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.controller.Controller;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class CommandModule {

    @Singleton
    @Provides
    public ParseCommandLineArgumentsCommand provideParseCommandLineArguments(Context context, ValidateCommandLineArgumentsCommand command) {
        return new ParseCommandLineArgumentsCommandImpl(context, command);
    }

    @Singleton
    @Provides
    public ValidateCommandLineArgumentsCommand provideValidateCommandLineArgumentsCommnd(Context context, Controller controller) {
        return new ValidateCommandLineArgumentsCommandImpl(context, controller);
    }

    @Singleton
    @Provides
    public ProjectInformationCommand provideProjectInformationCommand(Context context) {
        return new ProjectInformationCommandImpl(context);
    }

}
