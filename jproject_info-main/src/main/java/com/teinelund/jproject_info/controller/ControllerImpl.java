package com.teinelund.jproject_info.controller;

import com.teinelund.jproject_info.commands.NonValidJavaProjectPath;
import com.teinelund.jproject_info.commands.ValidateCommandLineArgumentsException;
import com.teinelund.jproject_info.common.VerboseOutput;
import com.teinelund.jproject_info.context.Context;
import com.teinelund.jproject_info.strategy.PathInformationStrategy;
import com.teinelund.jproject_info.strategy.PrintHelpStrategy;
import com.teinelund.jproject_info.strategy.PrintVersionStrategy;
import com.teinelund.jproject_info.strategy.Strategy;
import org.fusesource.jansi.Ansi;

import javax.inject.Inject;

import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class ControllerImpl extends VerboseOutput implements Controller {

    private Context context;
    private Strategy printHelpStrategy;
    private Strategy printVersionStrategy;
    private Strategy pathInformationStrategy;

    @Inject
    public ControllerImpl(
            Context context,
            PrintHelpStrategy printHelpStrategy,
            PrintVersionStrategy printVersionStrategy,
            PathInformationStrategy pathInformationStrategy) {
        this.context = context;
        this.printHelpStrategy = printHelpStrategy;
        this.printVersionStrategy = printVersionStrategy;
        this.pathInformationStrategy = pathInformationStrategy;
    }

    @Override
    public void execute() {
        Strategy strategy = null;
        if (this.context.getParameters().isHelpOption()) {
            strategy = this.printHelpStrategy;
        }
        else if (this.context.getParameters().isVersionOption()) {
            strategy = this.printVersionStrategy;
        }
        else if (this.context.getParameters().isPathInfo()) {
            verboseOutput(this.context.getParameters(), "Path info strategy selected.");
            strategy = this.pathInformationStrategy;
        }

        if (strategy != null) {
            try {
                strategy.execute();
            }
            catch (ValidateCommandLineArgumentsException e) {
                printErrorMessage(e.getNonValidJavaProjectPaths());
            }
            catch(Exception e) {
                System.err.println(ansi().fg(Ansi.Color.RED).a("[ERROR] " + e.getMessage()).reset().toString());
            }
        }
        else {
            System.err.println(ansi().fg(Ansi.Color.RED).a("[ERROR] No option selected. Type --help to display all options.").reset().toString());
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
