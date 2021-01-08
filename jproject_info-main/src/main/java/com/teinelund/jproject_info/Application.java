package com.teinelund.jproject_info;

import javax.inject.Inject;

import com.teinelund.jproject_info.commands.ParseCommandLineArgumentsCommand;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.controller.Controller;
import com.teinelund.jproject_info.command_line_parameters_parser.Parameters;
import com.teinelund.jproject_info.project_information.ProjectInformation;
import com.teinelund.jproject_info.project_information.ProjectInformationFactory;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;

import static org.fusesource.jansi.Ansi.ansi;


/**
 * Main class
 */
public class Application {
    public static void main(String[] args) throws IOException {
        ApplicationComponent ac = DaggerApplicationComponent.create();
        Application application = ac.buildApplication();
        application.execute(args);
    }

    private Context context;
    private ParseCommandLineArgumentsCommand command;

    @Inject
    public Application(Context context, ParseCommandLineArgumentsCommand command) {
        this.context = context;
        this.command = command;
    }

    void execute(String[] args) {
        AnsiConsole.systemInstall();
        this.context.setCommandLineArguments(args);
        this.command.execute();
    }

}
