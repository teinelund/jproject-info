package com.teinelund.jproject_info;

import javax.inject.Inject;

import com.teinelund.jproject_info.commands.NonValidJavaProjectPath;
import com.teinelund.jproject_info.commands.ParseCommandLineArgumentsCommand;
import com.teinelund.jproject_info.commands.ValidateCommandLineArgumentsException;
import com.teinelund.jproject_info.context.Context;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import java.io.IOException;
import java.util.List;

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
        try {
            this.command.execute();
        }
        catch (ValidateCommandLineArgumentsException e) {
            printErrorMessage(e.getNonValidJavaProjectPaths());
        }
    }

    void printErrorMessage(List<NonValidJavaProjectPath> nonValidJavaProjectPaths) {
        if (nonValidJavaProjectPaths.size() == 1) {
            System.err.println(ansi().fg(Ansi.Color.RED).a("[ERROR] " + nonValidJavaProjectPaths.get(0).getErrorString()).reset().toString());
        } else {
            for (NonValidJavaProjectPath nonValidJavaProjectPath : nonValidJavaProjectPaths) {
                System.err.println(ansi().fg(Ansi.Color.RED).a("[ERROR] Option " + (nonValidJavaProjectPath.getIndex() + 1) + " : " + nonValidJavaProjectPath.getErrorString()).reset().toString());
            }
        }
    }

}
