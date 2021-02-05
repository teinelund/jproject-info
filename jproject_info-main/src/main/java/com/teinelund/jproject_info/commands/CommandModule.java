package com.teinelund.jproject_info.commands;

import com.teinelund.jproject_info.common.Printer;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.controller.Controller;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class CommandModule {

    @Singleton
    @Provides
    public ParseCommandLineArgumentsCommand provideParseCommandLineArguments(Context context, Printer printer, Controller controller) {
        return new ParseCommandLineArgumentsCommandImpl(context, printer, controller);
    }

    @Singleton
    @Provides
    public ValidateCommandLineArgumentsCommand provideValidateCommandLineArgumentsCommnd(Context context, Printer printer, ProjectInformationCommand command) {
        return new ValidateCommandLineArgumentsCommandImpl(context, printer, command);
    }

    @Singleton
    @Provides
    public ProjectInformationCommand provideProjectInformationCommand(Context context, Printer printer) {
        return new ProjectInformationCommandImpl(context, printer);
    }

}
