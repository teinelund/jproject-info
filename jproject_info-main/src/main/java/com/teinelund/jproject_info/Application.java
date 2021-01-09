package com.teinelund.jproject_info;

import javax.inject.Inject;
import com.teinelund.jproject_info.commands.ParseCommandLineArgumentsCommand;
import com.teinelund.jproject_info.context.Context;
import org.fusesource.jansi.AnsiConsole;
import java.io.IOException;

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
