package com.teinelund.jproject_info;

import javax.inject.Inject;

import com.teinelund.jproject_info.commands.ParseCommandLineArgumentsCommand;
import com.teinelund.jproject_info.common.Printer;
import com.teinelund.jproject_info.context.Context;
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
    private Printer printer;
    private ParseCommandLineArgumentsCommand command;

    @Inject
    public Application(Context context, Printer printer, ParseCommandLineArgumentsCommand command) {
        this.context = context;
        this.printer = printer;
        this.command = command;
    }

    void execute(String[] args) {
        AnsiConsole.systemInstall();
        this.context.setCommandLineArguments(args);
        try {
            this.command.execute();
        }
        catch(Exception e) {
            this.printer.error(e.getMessage());
        }
    }



}
